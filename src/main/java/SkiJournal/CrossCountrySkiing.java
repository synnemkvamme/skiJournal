package SkiJournal;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CrossCountrySkiing extends Activity {
    private String extraInfoOneFormatted;
    private String extraInfoTwoFormatted;
    private String extraInfoOne;
    private String extraInfoTwo;

    public CrossCountrySkiing(String title, LocalDate date, String text, String distance, String activityType,
            String extraInfoOneFormatted, String extraInfoTwoFormatted, String extraInfoOne, String extraInfoTwo) {
        super(title, date, text, distance, activityType);

        validDuration(extraInfoTwo);
        this.extraInfoOne = extraInfoOne;
        this.extraInfoTwo = extraInfoTwo;

        if (extraInfoOneFormatted.equals("0") && extraInfoTwoFormatted.equals("0")&& !extraInfoOne.equals("0") && !extraInfoTwo.equals("0")){
            this.extraInfoOneFormatted = "Skismøring: " + extraInfoOne;
            this.extraInfoTwoFormatted = "Gjennomsnittsfart (km/t): " + Formatteduration(extraInfoTwo);
        } else {
            this.extraInfoOneFormatted = extraInfoOneFormatted;
            this.extraInfoTwoFormatted = extraInfoTwoFormatted;
        }
    }

    public CrossCountrySkiing(String title, LocalDate date, String text, String distance, String activityType,
            String extraInfoOne, String extraInfoTwo) {
        this(title, date, text, distance, activityType, "0", "0", extraInfoOne, extraInfoTwo);
        }


    private void validDuration(String duration) throws IllegalArgumentException, NumberFormatException{
        if (Double.valueOf(duration) < 0) throw new IllegalArgumentException("Varighet på økt kan ikke være negativt tall.");
    }

    private String Formatteduration(String duration){
        Double Duration = Double.valueOf(this.getDistance())/Double.valueOf(duration);
        return String.format("%.2f", Duration).replace(",", ".");
    }

    @Override
    public String getExtraInfoOneFormatted() {
        return this.extraInfoOneFormatted;
    }

    @Override
    public String getExtraInfoTwoFormatted() {
        return this.extraInfoTwoFormatted;
    }

    @Override
    public String getExtraInfoOne() {
        return this.extraInfoOne;
    }

    @Override
    public String getExtraInfoTwo() {
        return this.extraInfoTwo;
    }

    
    @Override
    public String toString() {
        return super.toString() + "extraInfoOneFormatted=" + extraInfoOneFormatted + ", extraInfoTwoFormatted=" + extraInfoTwoFormatted +
         ", extraInfoOne=" + extraInfoOne + ", extraInfoTwo=" + extraInfoTwo;
    }

    public static void main(String[] args) {
        Profile synne = new Profile("Synne");
        Activity a1 = new CrossCountrySkiing("Litt alng tittel ja hahahah enda lengre", LocalDate.now(), "texttexttexttexttexesdfttexttexttexttexttexttexttexttexttexttext", "2", "Langrenn", "1", "1") ;
        System.out.println(a1.getTitle());
    
    }

    
}
