package SkiJournal;

import java.time.LocalDate;

public class SlalomTelemark extends Activity {
    private String extraInfoOneFormatted;
    private String extraInfoTwoFormatted;
    private String extraInfoOne;
    private String extraInfoTwo;

    private String verticalMeters;
    private String verticalMetersPerHillToLaps;


    public SlalomTelemark(String title, LocalDate date, String text, String distance, String activityType,
    String extraInfoOneFormatted, String extraInfoTwoFormatted, String extraInfoOne, String extraInfoTwo) {
        super(title, date, text, distance, activityType);
        validVerticalMeters(extraInfoOne);
        validVerticalMeters(extraInfoTwo);
        this.extraInfoOne = extraInfoOne;
        this.extraInfoTwo = extraInfoTwo;
        if (extraInfoOneFormatted.equals("0") && extraInfoTwoFormatted.equals("0") && !extraInfoOne.equals("0") && !extraInfoTwo.equals("0")){
            this.extraInfoOneFormatted = "Høydemeter: " + extraInfoOne;
            this.extraInfoTwoFormatted = "Antall laps: " + calculateLaps(extraInfoTwo, extraInfoOne); 
        } else{
            this.extraInfoOneFormatted = extraInfoOneFormatted;
            this.extraInfoTwoFormatted = extraInfoTwoFormatted;
        }
        // validVerticalMeters(verticalMeters);
        // validVerticalMeters(verticalMetersPerHillToLaps);
        // this.verticalMeters = "Høydemeter: " + verticalMeters;
        // this.verticalMetersPerHillToLaps = "Antall laps: " + calculateLaps(verticalMetersPerHillToLaps, verticalMeters); 
       }

    public SlalomTelemark(String title, LocalDate date, String text, String distance, String activityType,
        String extraInfoOne, String extraInfoTwo) {
            this(title, date, text, distance, activityType, "0", "0", extraInfoOne, extraInfoTwo);
    }

    private void validVerticalMeters(String verticalMeters) throws IllegalArgumentException, NumberFormatException{
        if (Integer.valueOf(verticalMeters) < 0) throw new IllegalArgumentException("Høydemeter kan ikke være negativt tall.");
    }

    private String calculateLaps(String verticalMetersPerHillToLaps, String verticalMeters) throws NumberFormatException{
        if (Integer.valueOf(verticalMetersPerHillToLaps) == 0) return "0";
        int laps = (Integer.valueOf(verticalMeters))/(Integer.valueOf(verticalMetersPerHillToLaps));
        return String.valueOf(laps);
    }

    @Override
    public String getExtraInfoOneFormatted() {
        return extraInfoOneFormatted;
    }

    @Override
    public String getExtraInfoTwoFormatted() {
        return extraInfoTwoFormatted;
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


    

    

    
}
