package SkiJournal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ActivityTest {
    private Activity activity;

    @BeforeEach
    public void setUp(){
        activity = new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Topptur", "4000", "2");
    }
    
    @Test
    @DisplayName("Test konstruktør")
    public void testConstructor(){
        assertEquals("Test topptur", activity.getTitle(), "Tester tittel");
        assertEquals(LocalDate.of(2022, 04, 07), activity.getDate(), "Tester dato");
        assertEquals("Testaktivitet", activity.getText(), "Tester tekst");
        assertEquals("64.4", activity.getDistance(), "Tester distanse");
        assertEquals("Topptur", activity.getActivityType(), "Tester aktivitetstype");
        assertEquals("Høydemeter: 4000", activity.getExtraInfoOneFormatted(), "Tester høydemeter/ekstrainfo1");
        assertEquals("Skredfare: 2", activity.getExtraInfoTwoFormatted(), "Tester skredfaregrad/ekstrainfo2");
    }

    @Test
    @DisplayName("Test av gyldig distanse")
    public void testValidDistance(){
        assertThrows(
            IllegalArgumentException.class,
            () -> {new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet", "-1", "Topptur", "4000", "2");},
            "Tester at unntak blir utløst dersom man oppgir negativ distanse.");
        assertThrows(
            NumberFormatException.class,
            () -> {new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet", "sekstifirekommafire", "Topptur", "4000", "2");},
            "Tester at unntak blir utløst dersom man oppgir ugyldig tall");
        assertThrows(
            NumberFormatException.class,
            () -> {new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet", "64,4", "Topptur", "4000", "2");},
            "Tester at unntak blir utløst dersom man bruker komma i steden for punktum.");
    }

    @Test
    @DisplayName("Test av oppdeling av lang tekst.")
    public void testAddNewLines(){
        Activity activityWithLongTitle = new Randonee("Test topptur med 25 tegn.", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Topptur", "4000", "2");
        assertEquals("Test topptur me\r\nd 25 tegn.", activityWithLongTitle.getTitle(),"Tester at linjeskift har blitt lagt inn på rett plass.");

        Activity activityWithLongText = new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet med 65 tegn. Det betyr at vi trenger et linjeskift.", "64.4", "Topptur", "4000", "2");
        
        assertEquals("Testaktivitet med 65 tegn. Det betyr at vi tr\r\nenger et linjeskift.", activityWithLongText.getText());
    }
    }


