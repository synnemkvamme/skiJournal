package SkiJournal.FileHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import SkiJournal.Activity;
import SkiJournal.CrossCountrySkiing;
import SkiJournal.Profile;
import SkiJournal.Randonee;
import SkiJournal.SlalomTelemark;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SkiJournalFileHandlerTest{

    private static final String testSkiJournalFileContent = """
            Test
            100.0  
            Langrennstur#2022#APRIL#8#Det var en fin tur!#12#Langrenn#Skismøring: VR45#Gjennomsnittsfart (km/t): 12.00#VR45#1
            Vassfjellet#2022#APRIL#5#Skuffer aldri, bra tur!#54#Slalom#Høydemeter: 3453#Antall laps: 11#3453#300
            Skåråsalen#2022#APRIL#2#Bra snø og kule folk!#12#Topptur#Høydemeter: 1200#Skredfare: 2#1200#2
            """;
            
    private static final String testEmptySkiJournalFileContent = """
            Test2
            0.0
            """;
    
    private ISkiJournalFileHandler skiJournalFileHandler = new SkiJournalFileHandler();
    private Profile profile;
    private String userName = "Test";

    private Profile getProfileWithoutContent(){
        return new Profile(userName);
    }

    private Profile getProfileWithContent(){
        profile = getProfileWithoutContent();
        profile.addActivity(new Randonee("Skåråsalen", LocalDate.of(2022, Month.APRIL, 2), "Bra snø og kule folk!", "12", "Topptur", "1200", "2"));
        profile.addActivity(new CrossCountrySkiing("Langrennstur", LocalDate.of(2022, Month.APRIL, 8), "Det var en fin tur!", "12", "Langrenn", "VR45", "1"));
        profile.addActivity(new SlalomTelemark("Vassfjellet", LocalDate.of(2022, Month.APRIL, 5), "Skuffer aldri, bra tur!", "54", "Slalom", "3453", "300"));
       // profile.addActivity("Kyrkjetaket i strålende sol", LocalDate.of(2022, Month.APRIL, 3), "Helt perfekte forhold!","13", "Topptur", "1150", "2" );
        //profile.addActivity("Langrennstur", LocalDate.of(2022, Month.APRIL, 7), "Fin tur!", "10.0", "Langrenn", "VR45", "1.0");
        //profile.addActivity("Vassfjellet", LocalDate.of(2022, Month.APRIL, 6), "Lang og fin dag i vassfjellet.", "30", "Slalom", "3000", "300");
        profile.setDistanceGoal(100);
        return profile;
    }

    @BeforeAll
    public void setUp() throws IOException{
       // Files.write(Path.of(SkiJournalFileHandler.class.getResource("resources/").getFile() + "Tuva" + ".txt"), testSkiJournalFileContent.getBytes());
        Files.write(Path.of(skiJournalFileHandler.getFilePath("Test")), testSkiJournalFileContent.getBytes());
    }

    @Test
    @DisplayName("Tester lesing av profil med innhold.")
    public void testReadProfile() throws IOException{
        Profile expectedProfile = getProfileWithContent();
        Profile actualProfile = skiJournalFileHandler.readProfile("Test", getProfileWithoutContent());
        
        Iterator<Activity> expectedIterator = expectedProfile.getActivitiesList().iterator();
        Iterator<Activity> actualIterator = actualProfile.getActivitiesList().iterator();

        while (expectedIterator.hasNext()){
            try {
                Activity expectedActivity = expectedIterator.next();
                Activity actualActivity = actualIterator.next();
    
                assertEquals(expectedActivity, actualActivity);
            } catch (IndexOutOfBoundsException e) {
                fail("Forventet filformat er ulikt filformatet ved bruk av readProfile.");
            }
        }
    }

    @AfterAll
    public void tearDown(){
        String filePaht = skiJournalFileHandler.getFilePath(userName);
        (Path.of(filePaht)).toFile().delete();
    }

}
