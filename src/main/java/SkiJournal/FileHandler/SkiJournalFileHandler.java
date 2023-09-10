package SkiJournal.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import SkiJournal.Activity;
import SkiJournal.Profile;

public class SkiJournalFileHandler implements ISkiJournalFileHandler{
    private ArrayList<String> userNames = new ArrayList<>();
    
    @Override
    public Profile readProfile(String filename, Profile profile) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(getFilePath(filename)))){
            String name = scanner.nextLine();
            String distanceGoal = scanner.nextLine();
            if (!name.equals(null)) profile.setUserName(name);
            profile.setDistanceGoal(Double.valueOf(distanceGoal));
            while (scanner.hasNext()){
                String [] activityList = scanner.nextLine().split("#");
                profile.addActivity(activityList[0].replaceAll(";   ", "\n").replaceAll("   _", "#"), 
                    LocalDate.of(Integer.valueOf(activityList[1]), Month.valueOf(activityList[2]), Integer.valueOf(activityList[3])),
                    activityList[4].replaceAll(";   ", "\n").replaceAll("   _", "#"), activityList[5], activityList[6],
                    activityList[7].replaceAll("   _", "#"), activityList[8].replaceAll("   _", "#"),  
                    activityList[9].replaceAll("   _", "#"), activityList[10].replaceAll("   _", "#"));
            }
        } return profile;
    }

    @Override
    public Profile writeProfile(String filename, Profile profile) throws FileNotFoundException {
            try (PrintWriter writer = new PrintWriter(new File(getFilePath(filename)))){
                writer.println(profile.getUserName());
                writer.println(String.valueOf(profile.getDistanceGoal()));
                for (Activity activity : profile.getActivitiesList()) {
                    writer.println(activity.getTitle().replaceAll("\n", ";   ").replaceAll("#", "   _") + "#" 
                    + String.valueOf(activity.getDate().getYear()) + "#" + String.valueOf(activity.getDate().getMonth()) + "#" + String.valueOf(activity.getDate().getDayOfMonth()) +  "#" 
                    + activity.getText().replaceAll("\n", ";   ").replace("#", "   _") + "#" + String.valueOf(activity.getDistance()) + "#"
                    + activity.getActivityType() + "#"
                    + activity.getExtraInfoOneFormatted().substring(activity.getExtraInfoOne().indexOf(":")+1).replaceAll("#", "   _") + "#"
                    + activity.getExtraInfoTwoFormatted().substring(activity.getExtraInfoTwo().indexOf(":")+1).replaceAll("#", "   _") + "#"
                    + activity.getExtraInfoOne().replaceAll("#", "   _") + "#"
                    + activity.getExtraInfoTwo().replaceAll("#", "   _"));
                }
            } return profile;
    
        }





    @Override
    public void createNewFile(String filename, Profile profile) throws FileNotFoundException, FileAlreadyExistsException{      
        if (!getUserList().contains(filename)){
            try( PrintWriter writer = new PrintWriter(new File(getFilePath(filename)))){
                writer.println(profile.getUserName());
                writer.println(profile.getDistanceGoal());
                userNames.add(profile.getUserName());
            }
        } else throw new FileAlreadyExistsException(filename);
        
    }      
    @Override
    public String getFilePath(String filename) {
        return "src/main/resources/" + filename + ".txt";
        }


    public Collection<String> getUserList(){
        File profiles = new File("src/main/resources/");
        String[] profileList = profiles.list();
        Collection<String> userList = new ArrayList<>();
        for (String profile : profileList) {
            if (!profile.equals(".DS_Store")){ //.DS_Store er en fil i mapper på macer som inneholder informasjon som ikke er relevant for hver enkelt profil
                userList.add(profile.replace(".txt", ""));
            }
        } return userList;        
    }

    // @Override
    // public Path getFilePath(String filename) {
    //     return Path.of(SkiJournalFileHandler.class.getResource("resources/").getFile() + filename + ".txt");
    // }

}
  


    