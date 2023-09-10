package SkiJournal;

import java.time.LocalDate;

public interface IActivity {
    String getTitle();
    LocalDate getDate();
    String getText();
    String getDistance();
    String getActivityType();
    String getExtraInfoOne();
    String getExtraInfoTwo();
   
}
