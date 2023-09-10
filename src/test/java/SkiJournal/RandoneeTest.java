package SkiJournal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RandoneeTest {
    private Activity randoneeActivity;

    @BeforeEach
    public void setup(){
        randoneeActivity = new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Topptur", "4000", "2");
        }

    @Test
    @DisplayName("Test konstruktør")
    public void testConstructor(){
        assertEquals("Test topptur", randoneeActivity.getTitle(), "Tester tittel");
        assertEquals(LocalDate.of(2022, 04, 07), randoneeActivity.getDate(), "Tester dato");
        assertEquals("Testaktivitet", randoneeActivity.getText(), "Tester tekst");
        assertEquals("64.4", randoneeActivity.getDistance(), "Tester distanse");
        assertEquals("Topptur", randoneeActivity.getActivityType(), "Tester aktivitetstype");
        assertEquals("Høydemeter: 4000", randoneeActivity.getExtraInfoOneFormatted(), "Tester høydemeter/ekstrainfo1");
        assertEquals("Skredfare: 2", randoneeActivity.getExtraInfoTwoFormatted(), "Tester skredfaregrad/ekstrainfo2");
    }

    @Test
    @DisplayName("Test av høydemeter.")
    public void testVerticalMeters(){
        assertEquals("Høydemeter: 4000", randoneeActivity.getExtraInfoOneFormatted());
        assertThrows(
            IllegalArgumentException.class,
            () -> {new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Topptur", "-4000", "2");},
            "Tester at unntak blir utløst dersom man gir inn negativt antall høydemeter.");
        assertThrows(
            NumberFormatException.class,
            () -> {new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Topptur", "firetusen", "2");},
            "Tester at unntak blir utløst dersom man gir inn et ugyldig tall.");
    }

    @Test
    @DisplayName("Test av skredfare.")
    public void testAvalancheDanger(){
        assertEquals("Skredfare: 2", randoneeActivity.getExtraInfoTwoFormatted());
        assertThrows(
            IllegalArgumentException.class,
            () -> {new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Topptur", "4000", "0");},
            "Tester at unntak blir utløst dersom man gir inn et tall under 1");
        assertThrows(
        IllegalArgumentException.class,
        () -> {new Randonee("Test topptur", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Topptur", "4000", "6");},
        "Tester at unntak blir utløst dersom man gir inn et tall over 5");
    }

}
