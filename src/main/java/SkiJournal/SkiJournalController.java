package SkiJournal;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDate;
import java.util.Optional;

import SkiJournal.FileHandler.ISkiJournalFileHandler;
import SkiJournal.FileHandler.SkiJournalFileHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SkiJournalController {

    private Profile profile; 
    private ISkiJournalFileHandler fileHandler = new SkiJournalFileHandler();

    @FXML
    private VBox activityInputView;

    @FXML
    private AnchorPane distanceGoalView, tableActivitiesView, logInView;

    @FXML
    private TableView<Activity> tableActivities;

    @FXML
    private TableColumn<Activity, String> colTitle, colText, colActivity, colDistance,colExtraOne, colExtraTwo;

    @FXML
    private TableColumn<Activity, DatePicker> colDate;
    
    @FXML 
    private Label achivedDistance, distanceGoal, profileInfo, skiDays, errorMessage;

    @FXML
    private TextField title, distance, distanceGoalInput, extraOne, extraTwo;

    @FXML
    private TextArea text;

    @FXML
    private DatePicker date;

    @FXML
    private ChoiceBox<String> activityChoice, activityViewChoiceBox, chooseUser, sortingActivities;

    @FXML
    private Button SaveActivity, CreateNewActivity, cancelActivity, saveDistanceGoal, cancelSaveDistanceGoal, changeUser, deleteActivity;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private void initialize(){  
        tableActivitiesView.setVisible(false);
        profileInfo.setVisible(false);
        chooseUser.getItems().setAll(fileHandler.getUserList());
        chooseUser.setValue("Velg Bruker");
        deleteActivity.disableProperty().bind(tableActivities.getSelectionModel().selectedItemProperty().isNull());
        initializeTableView();
        initializeCreateActivityView();
        initializeActivityChoiceView();
        initializeSortingChoiceView();
        }   
    
    @FXML
    private void handleChoseUser(){
            try {
                if (!chooseUser.getValue().toString().equals("Velg Bruker")){
                    String userName = chooseUser.getValue().toString();
                    profile = new Profile(userName);
                    fileHandler.readProfile(userName, profile); 
                    updateInformationTables();
                    profileInfo.setVisible(true);
                    tableActivitiesView.setVisible(true);
                    profileInfo.setText(("Profil: " + profile.getUserName()));
                    tableActivities.setItems(FXCollections.observableArrayList(profile.getActivitiesList()));
                    logInView.setVisible(false);
                } 
            }
            catch (FileNotFoundException e){
            showErrorDialog("Noe gikk galt. Vennligst velg en annen bruker.");
            } catch (NullPointerException e){
                //ønsker ikke at noe skal skje da ingenting er valgt
            }
        } 
    

    @FXML
    private void handleCreateNewUser(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Opprett ny bruker");
        dialog.setHeaderText("Velg deg et brukernavn.");
        String userName = dialog.showAndWait().get();
        try {        
            profile = new Profile(userName);
            fileHandler.createNewFile(userName, profile);
            profileInfo.setText(("Pålogget: " + profile.getUserName()));
            tableActivities.setItems(FXCollections.observableArrayList(profile.getActivitiesList())); 
            initialize();
            chooseUser.setValue(userName);
        } catch (FileNotFoundException e) {
            showErrorDialog("En uventet feil oppsto." + e.getMessage()); //bør endre? Er en uventet feil at files lages.
        } catch (FileAlreadyExistsException e){
            showErrorDialog("Det finnes allerede en bruker med navnet " + userName + 
            ".\nPrøv med et nytt brukernavn. ");
        }
         
    }

    private void initializeTableView(){
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colActivity.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colText.setCellValueFactory(new PropertyValueFactory<>("text"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        colExtraOne.setCellValueFactory(new PropertyValueFactory<>("extraInfoOneFormatted"));
        colExtraTwo.setCellValueFactory(new PropertyValueFactory<>("extraInfoTwoFormatted"));
    }

    private void initializeCreateActivityView(){
        activityChoice.getItems().setAll("*Velg Aktivtetstype", "Langrenn","Slalom", "Telemark", "Topptur");
        activityChoice.setValue("*Velg Aktivtetstype");
        date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
        date.setValue(LocalDate.now());   
    }

    private void initializeActivityChoiceView(){
        activityViewChoiceBox.getItems().setAll("Vis alle Aktivtetstyper", "Vis kun Langrenn","Vis kun Slalom", "Vis kun Telemark", "Vis kun Topptur");
        activityViewChoiceBox.setValue("Vis alle Aktivtetstyper");
    }

    private void initializeSortingChoiceView(){
        sortingActivities.getItems().setAll("Sorter etter dato", "Sorter etter aktivitetstype og distanse");
        sortingActivities.setValue("Sorter etter dato");
    }
    private void updateInformationTables(){
        distanceGoal.setText(String.valueOf(profile.getDistanceGoal()));
        achivedDistance.setText(String.valueOf(profile.getTotalDistance()));
        progressbar.setProgress(profile.getDistanceGoalAchivement());
        skiDays.setText(String.valueOf(profile.getSkiDays()));
    }

    @FXML
    private void handleSortActivitiesChoice(){
        try{
            if(sortingActivities.getValue().toString().equals("Sorter etter dato")){
                tableActivities.setItems(FXCollections.observableArrayList(profile.getActivitiesSortedByDate()));
            } else if (sortingActivities.getValue().toString().equals("Sorter etter aktivitetstype og distanse")){
                tableActivities.setItems(FXCollections.observableArrayList(profile.getActivitiesSortedByType()));
            }
        } catch (NullPointerException e){

        }
    }

    @FXML
    private void handleActivityViewChoice(){
        try{
        if (activityViewChoiceBox.getValue().toString().equals("Vis alle Aktivtetstyper")){
            tableActivities.setItems(FXCollections.observableArrayList(profile.getActivitiesList()));        } 
        else{
            tableActivities.setItems(FXCollections.observableArrayList(profile.getActivitiesByType(activityViewChoiceBox.getValue().toString().substring(8))));     
        } 
        tableActivities.getSortOrder().add(colDate); 
        } catch (NullPointerException e){
            //Ønsker ingen endring i visning av aktivitet
        }
    }

    @FXML
    private void handleCreateNewActivity(){
        clearActivityFields();
        tableActivitiesView.setVisible(false);
        distanceGoalInput.setText("0");
        activityInputView.setVisible(true);
        }

    @FXML
    private void handleSaveActivity(){
        try {
            if (title.getText().isEmpty() || extraOne.getText().isEmpty() || extraTwo.getText().isEmpty()) throw new IllegalArgumentException("Fyll inn alle felter med stjerne");
            if (activityChoice.getValue().toString().equals("*Velg Aktivtetstype")) throw new IllegalArgumentException("Velg aktivitetstype");
            profile.addActivity(title.getText(), date.getValue(), text.getText(), distance.getText(), activityChoice.getValue().toString(), 
                extraOne.getText(), extraTwo.getText());
            tableActivities.setItems(FXCollections.observableArrayList(profile.getActivitiesSortedByDate()));
            updateInformationTables();
            activityInputView.setVisible(false);
            tableActivitiesView.setVisible(true);
            if (profile.getDistanceGoalAchivement() >= 1){
                showInformationDialog("Gratulerer! \nDu har nådd distansemålet ditt! Sett deg et høyere distansemål!");
            }
            writeChangesToFile();
        } 
        catch (NumberFormatException e) {
            showErrorDialog("Skriv inn gyldig tall.");
        } catch (IllegalArgumentException e){
            showErrorDialog(e.getLocalizedMessage());
        } 
    }

    @FXML
    private void handleActivityTypeChoice(){
        if (!(activityChoice.getValue() == "0")){
            if (activityChoice.getValue().equals("*Velg Aktivtetstype")){
                extraOne.setPromptText("Ekstra informasjon tilknyttet valg av aktivitetstype");
                extraTwo.setPromptText("Ekstra informasjon tilknyttet valg av aktivitetstype");
                extraOne.setDisable(true);
                extraTwo.setDisable(true);
            } else{
                extraOne.setDisable(false);
                extraTwo.setDisable(false);
            } if (activityChoice.getValue().equals("Langrenn")){
                extraOne.setPromptText("*Skismøring");
                extraTwo.setPromptText("*Varighet på turen (oppgi i timer, for kalkulering av gjennomsnittlig hastighet.)");
            } else if (activityChoice.getValue().equals("Slalom") || activityChoice.getValue().equals("Telemark")){
                extraOne.setPromptText("*Totale høydemeter");
                extraTwo.setPromptText("*Gjennomsnittlig høydemeter per bakke (For kalkulering av antall laps)");
            } else if (activityChoice.getValue().equals("Topptur")){
                extraOne.setPromptText("*Totale høydemeter");
                extraTwo.setPromptText("*Skredfaregrad (1-5)");
            }
        }
    }

    @FXML
    void handleCancelActivity(){
        if(confirmationDialog("Er du sikker på at du ønsker å avbryte? "+ "\n" + "Aktiviteten vil ikke bli lagret.")){
        activityInputView.setVisible(false);
        tableActivitiesView.setVisible(true);
        }
    }

    @FXML
    private void handleSetDistanceGoal(){
        activityInputView.setVisible(false);
        tableActivitiesView.setVisible(false);
        distanceGoalView.setVisible(true);
    }

    @FXML
    private void handleSaveDistanceGoal(){
        try{
            double newDistanceGoal = Double.valueOf(distanceGoalInput.getText());
            profile.setDistanceGoal(newDistanceGoal);
            distanceGoal.setText(String.valueOf(profile.getDistanceGoal()));
            progressbar.setProgress(profile.getDistanceGoalAchivement());
            distanceGoalInput.clear();
            distanceGoalView.setVisible(false);
            tableActivitiesView.setVisible(true);
            writeChangesToFile();
        }catch (NumberFormatException e){
            showErrorDialog("Vennligst skriv inn et tall.");
        } catch (IllegalArgumentException e){
            showErrorDialog(e.getMessage());
        }
    }

    @FXML
    private void handleCancelDistanceGoal(){
        distanceGoalInput.clear();
        distanceGoalView.setVisible(false);
        tableActivitiesView.setVisible(true);
    }

    @FXML
    private void handleDeleteActivity(){
        if(confirmationDialog("Er du sikker på at du ønsker å slette den valgte aktiviteten? \n")){
        Activity selectedItem = tableActivities.getSelectionModel().getSelectedItem();
        tableActivities.getItems().remove(selectedItem);
        profile.removeActivity(selectedItem);
        progressbar.setProgress(profile.getDistanceGoalAchivement());
        achivedDistance.setText(String.valueOf(profile.getTotalDistance()));
        skiDays.setText(String.valueOf(profile.getSkiDays()));
        writeChangesToFile();
        } 
    }

    private boolean confirmationDialog(String message){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Informasjon");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Button okButton = (Button)alert.getDialogPane().lookupButton( ButtonType.OK );
        okButton.setText("Slett aktivitet");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent();        
    }


    @FXML
    private void handleChangeUser(){
        logInView.setVisible(true);
        writeChangesToFile();
        initialize();
    }

    private void writeChangesToFile(){
        try {
            fileHandler.writeProfile(profile.getUserName(), profile); //filen lagres som brukernavn
        } catch (FileNotFoundException e) {
            showErrorDialog("Noe gikk galt?"); //endre
        } catch (Exception e){
            showErrorDialog(e.getMessage()); //endre
        } 
    }

    private void showInformationDialog(String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informasjon");
        alert.setHeaderText("0");
        alert.setContentText(message);
        alert.showAndWait();

    }

    private void showErrorDialog(String errorMessage){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Noe gikk galt");
        alert.setHeaderText("Feilmelding");
        alert.setContentText(errorMessage);
        alert.showAndWait();       
    }

    private void clearActivityFields(){
        title.clear();
        text.clear();
        date.setValue(LocalDate.now());
        distance.clear();
        activityChoice.setValue("*Velg Aktivtetstype");
        extraOne.clear();
        extraTwo.clear();
    }
    
}
