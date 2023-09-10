package SkiJournal;

import java.time.LocalDate;

public class Randonee extends Activity {
    private String extraInfoOneFormatted;
    private String extraInfoTwoFormatted;
    private String extraInfoOne;
    private String extraInfoTwo;

    public Randonee(String title, LocalDate date, String text, String distance, String activityType,
    String extraInfoOneFormatted, String extraInfoTwoFormatted, String extraInfoOne, String extraInfoTwo) {
        super(title, date, text, distance, activityType);
        validVerticalMeters(extraInfoOne);
        this.extraInfoOne = extraInfoOne;
        validAvalancheDanger(extraInfoTwo);
        this.extraInfoTwo = extraInfoTwo;
        if (extraInfoOneFormatted.equals("0") && extraInfoTwoFormatted.equals("0") &&  !extraInfoOne.equals("0") && !extraInfoTwo.equals("0")){
            this.extraInfoOneFormatted = "Høydemeter: " + extraInfoOne;
            this.extraInfoTwoFormatted = "Skredfare: " + extraInfoTwo;
        } else{
            this.extraInfoOneFormatted = extraInfoOneFormatted;
            this.extraInfoTwoFormatted = extraInfoTwoFormatted;
        }
        


        // validVerticalMeters(verticalMeters);
        // this.verticalMeters = "Høydemeter: " + String.valueOf(verticalMeters);
        // validAvalancheDanger(Integer.valueOf(avalancheDanger));
        // this.avalancheDanger = "Skredfare: " + String.valueOf(avalancheDanger);
    }

    public Randonee(String title, LocalDate date, String text, String distance, String activityType,
        String extraInfoOne, String extraInfoTwo) {
        this(title, date, text, distance, activityType, "0", "0", extraInfoOne, extraInfoTwo);
        }

    private void validVerticalMeters(String verticalMeters) throws IllegalArgumentException, NumberFormatException{
        if (Integer.valueOf(verticalMeters) < 0) throw new IllegalArgumentException("Høydemeter kan ikke være negativt tall.");
    }

    private void validAvalancheDanger(String avalancheDanger) throws IllegalArgumentException{
        if (Integer.valueOf(avalancheDanger) < 1 || Integer.valueOf(avalancheDanger) > 5){
            throw new IllegalArgumentException("Skredfaren må være et tall mellom 1 og 5.");
        }
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
    public String getExtraInfoOneFormatted() {
        return extraInfoOneFormatted;
    }

    @Override
    public String getExtraInfoTwoFormatted() {
        return extraInfoTwoFormatted;
    }

    @Override
    public String toString() {
        
        return super.toString() + "extraInfoOneFormatted=" + extraInfoOneFormatted + ", extraInfoTwoFormatted=" + extraInfoTwoFormatted +
        ", extraInfoOne=" + extraInfoOne + ", extraInfoTwo=" + extraInfoTwo;
    }

    

    
    
}
