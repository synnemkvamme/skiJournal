package SkiJournal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProfileTest {
    private Profile profile;
    //private Activity crossCountryActivity = new CrossCountrySkiing("Test langrenn", LocalDate.now(), "testaktivitet", "10", "Langrenn", "VR45", "2");
    private Activity slalomActivity = new SlalomTelemark("Test slalom", LocalDate.of(2022, Month.JANUARY, 2), "Testaktivitet", "64.4", "Slalom", "4000", "400");
    private Activity randoneeActivity = new Randonee("Test topptur", LocalDate.of(2022, Month.JANUARY, 3), "Testaktivitet", "64.4", "Topptur", "4000", "2");


    @BeforeEach
    public void setup(){
        profile = new Profile("userName");
        profile.addActivity("Test Langrenn", LocalDate.of(2022, Month.JANUARY, 1), "testaktivitet", "10", "Langrenn", "VR45", "2");
    }

    @Test
    @DisplayName("Test av konstruktør")
    public void testConstructor(){
        assertEquals("userName", profile.getUserName(), "Teste brukernavn");
    } 

    @Test
    @DisplayName("Test av å legge til ny aktivitet")
    public void testAddActivity(){
        //profile.addActivity("Test slalom", LocalDate.of(2022, Month.APRIL, 1), "Testaktivitet", "64.4", "Slalom", "4000", "400");
        profile.addActivity(slalomActivity);
        assertEquals("Test slalom", profile.getActivitiesList().get(0).getTitle(), "Aktivitet på indeks 1 tilknyttet profil skal nå ha tittel lik Test slalom");
        assertEquals("64.4", profile.getActivitiesList().get(0).getDistance(), "Aktivitet på indeks 1 tilknyttet profil skal nå ha distanse 64.4");
        assertEquals(2, profile.getSkiDays(), "Antall skidager skal ha oppdatert seg til 2.");
        assertEquals(74.4, profile.getTotalDistance(), "Total distanse skal ha oppdatert seg til 74.4.");
        
        profile.addActivity(randoneeActivity);
        assertEquals("Test topptur", profile.getActivitiesList().get(0).getTitle(), "Tester at alternativ måte å legge til aktiviteter på fungerer.");
    }

    @Test
    @DisplayName("Test av å fjerne aktivitet")
    public void testRemoveActivity(){
        profile.addActivity(slalomActivity);
        assertEquals(2, profile.getActivitiesList().size(), "Det skal nå være 2 aktiviteter tilknyttet profil.");
        profile.removeActivity(slalomActivity);
        assertEquals(1, profile.getActivitiesList().size(), "Etter fjerning av aktivitet skal det nå være 1 aktivitet tilknyttet profil.");
        assertEquals(1, profile.getSkiDays(), "Antall skidager skal ha oppdatert seg til 1.");
        assertEquals(10, profile.getTotalDistance(), "Total distanse skal ha oppdatert seg til 10");
        assertThrows(
            IllegalArgumentException.class,
            () -> {profile.removeActivity(slalomActivity);},
            "Unntak blir utløst dersom man forsøker å fjerne en aktivitet som ikke finnes i aktivitetslisten tilknyttet profil");
    }

    @Test
    @DisplayName("Test å sette ulike distansemål")
    public void testSetDistanceGoal(){
        profile.setDistanceGoal(15);
        assertEquals(15, profile.getDistanceGoal(), "Distansemål skal være satt til 15");
        assertThrows(
                IllegalArgumentException.class,
                () -> {profile.setDistanceGoal(-10);},
                "Unntak skal utløses dersom distansemål blir satt til negativt tall."
        );
        profile.addActivity(slalomActivity);
        assertThrows(
                IllegalArgumentException.class,
                () -> {profile.setDistanceGoal(15);},
                "Unntak skal utløses dersom distansemål blir satt til mindre enn allerede oppnådd distanse."
        );   
    }

   @Test
   @DisplayName("Test av sortering av aktiviteter etter aktivitetstype")
   public void testSortActivitiesByType(){
       profile.addActivity(slalomActivity);
       assertEquals(1, profile.getActivitiesByType("Slalom").size(), "Den sorterte aktivitetslisten som kun inneholder slalom skal kun ha 1 element.");
       assertEquals(0, profile.getActivitiesByType("Topptur").size(), "Dersom det ikke finnes aktiviteter med denne aktivitetstittelen skal en tom liste med lengde 0 retuneres. ");
   }

   @Test
   @DisplayName("Test av å få måloppnåelse av distansemål")
   public void testGetDistanceGoalAchivement(){
       assertEquals(0, profile.getDistanceGoalAchivement(), "Dersom distansemålet ikke er satt skal måloppnåelse av distansemål være satt lik 0. Unntak skal ikke utløses.");
       profile.setDistanceGoal(100);
       assertEquals(0.1, profile.getDistanceGoalAchivement(), "Måloppnåelse av distansemål skal være lik 0.1");
   }



}
