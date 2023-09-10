package SkiJournal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrossCountrySkiingTest {
    private Activity crossCountrySkiingActivity;

    @BeforeEach
    public void setup(){
        crossCountrySkiingActivity = new CrossCountrySkiing("Test langrenn", LocalDate.of(2022, 04, 07), "Testaktivitet", "20", "Langrenn", "VR45", "2");
        } 

    @Test
    @DisplayName("Test av konstruktør")
    public void testConstructor(){
        assertEquals("Skismøring: VR45", crossCountrySkiingActivity.getExtraInfoOneFormatted(), "Tester rett formattering på skismøring/ekstrainfo1");
        assertEquals("Gjennomsnittsfart (km/t): 10.00", crossCountrySkiingActivity.getExtraInfoTwoFormatted(), "Tester rett formattering på varighet/ekstrainfo2");
    }

    @Test
    @DisplayName("Test av gydlighet på varighet og kalkulering av hastighet")
    public void testCalculateDuration(){
        assertThrows(
            NumberFormatException.class, 
            () -> {new CrossCountrySkiing("Test langrenn", LocalDate.of(2022, 04, 07), "Testaktivitet", "20", "Langrenn", "0", "0", "VR45", "to");},
            "Unntak skal utløses dersom ugyldig tall blir gitt inn.");
        assertThrows(
            IllegalArgumentException.class,
            () -> {new CrossCountrySkiing("Test langrenn", LocalDate.of(2022, 04, 07), "Testaktivitet", "20", "Langrenn", "0", "0", "VR45", "-2");},
            "Unntak skal utløses dersom negativt tall blir gitt inn.");
        assertEquals("Gjennomsnittsfart (km/t): 10.00", crossCountrySkiingActivity.getExtraInfoTwoFormatted(), "Sjekker at gjennomsnittshastighet har blitt kalkulert rett.");
    }
}
