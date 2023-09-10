package SkiJournal;

import java.time.LocalDate;


public abstract class Activity {
    
    protected String title;
    protected String text;
    protected LocalDate date;
    protected String distance;
    protected String activityType;
    
    public Activity(String title, LocalDate date, String text, String distance, String activityType) {
        this.title = addNewLines(title, 15);
        this.date = date;
        this.text = addNewLines(text, 45);
        validDistance(distance);
        this.distance = distance;
        this.activityType = activityType;
    }

    private void validDistance(String distance) throws IllegalArgumentException, NumberFormatException{
        double tmpDistance = Double.valueOf(distance);
        if (tmpDistance <= 0) throw new IllegalArgumentException("Distanse må være mer enn 0.");
    }

    private String addNewLines(String text, int index){
        if (text.length() < index){
            return text;
        } else {
            String finalText = "";
            String[] tmpTextLines = text.split("\n");
            for (String line : tmpTextLines) {
                while(line.length() > index){
                    String tmpSubText = line.substring(0, index);
                    line = line.substring(index);
                    finalText += tmpSubText + "\n"; 
                } finalText += line;
                if (!line.equals(tmpTextLines[tmpTextLines.length-1])){
                    finalText += "\n";
                } 
            }
            return finalText;
        }
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getDistance() {
        return distance;
    }

    public String getActivityType(){
        return this.activityType;
    }

    public abstract String getExtraInfoOneFormatted();
    public abstract String getExtraInfoTwoFormatted();
    public abstract String getExtraInfoOne();
    public abstract String getExtraInfoTwo();

    
    @Override
    public boolean equals(Object otherObject){
        if (otherObject instanceof Activity){
            Activity otherActivity = (Activity) otherObject;
            return this.title.equals(otherActivity.getTitle()) && this.date.equals(otherActivity.getDate()) 
            && this.text.equals(otherActivity.getText()) && this.distance.equals(otherActivity.getDistance())
            && this.activityType.equals(otherActivity.getActivityType())
            && this.getExtraInfoOne().equals(otherActivity.getExtraInfoOne())
            && this.getExtraInfoTwo().equals(otherActivity.getExtraInfoTwo()); 
        } return false;
    }

    @Override
    public String toString() {
        return "title=" + title + ", date=" + date + ", text=" + text + ", distance=" + distance
                + ", activityType=" + activityType + ", ";
    }

    

   
}
