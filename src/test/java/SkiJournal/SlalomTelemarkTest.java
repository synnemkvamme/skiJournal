package SkiJournal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlalomTelemarkTest {
    private Activity slalomActivity;

    @BeforeEach
    public void setup(){
        slalomActivity = new SlalomTelemark("Test slalom", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Slalom", "4000", "200");
        }

    @Test
    @DisplayName("Test av konstruktør")
    public void testConstructor(){
        assertEquals("Høydemeter: 4000", slalomActivity.getExtraInfoOne(), "Tester rett formattering på høydemeter/ekstrainfo1");
        assertEquals("Antall laps: 20", slalomActivity.getExtraInfoTwo(), "Tester rett formattering på antall laps/ekstrainfo2");
    }

    @Test
    @DisplayName("Test av gyldighet på totale høydemeter")
    public void testValidVerticalMeters(){
        assertThrows(
            IllegalArgumentException.class,
            () -> {new SlalomTelemark("Test slalom", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Slalom", "-4000", "200");},
            "Unntak skal utløses dersom antall høydemeter som blir gitt inn er et negativt tall");
        assertThrows(
            NumberFormatException.class,
            () -> {new SlalomTelemark("Test slalom", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Slalom", "firetusen", "200");},
            "Unntak skal utløses dersom høydemeter blir gitt inn som et ugyldig tall");
    }

    @Test
    @DisplayName("Test av gyldighet på høydemeter per bakke og kalkulering av antall laps")
    public void testCalculateLaps(){
        assertThrows(
            IllegalArgumentException.class,
            () -> {new SlalomTelemark("Test slalom", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Slalom", "4000", "-200");},
            "Unntak skal utløses dersom antall høydemeter som blir gitt inn er et negativt tall");
        assertThrows(
            NumberFormatException.class,
            () -> {new SlalomTelemark("Test slalom", LocalDate.of(2022, 04, 07), "Testaktivitet", "64.4", "Slalom", "4000", "tohundre");},
            "Unntak skal utløses dersom høydemeter blir gitt inn som et ugyldig tall");
        assertEquals("Antall laps: 20", slalomActivity.getExtraInfoTwo(), "Tester at kalkulering av antall laps er rett.");
    }


}
