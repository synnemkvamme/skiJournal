package SkiJournal;

import java.util.Comparator;

public class ActivitiesTypeComparator implements Comparator<Activity>{

    @Override
    public int compare(Activity activity1, Activity activity2) {
        if (activity1.getActivityType().compareTo(activity2.getActivityType()) == 0){
            double distance1 = 0;
            double distance2 = 0;
            if (activity1.getActivityType() == "Slalom" || activity1.getActivityType() == "Telemark"){
                distance1 = Double.valueOf(activity1.getDistance())/2;
            } else distance1 = Double.valueOf(activity1.getDistance());
            if (activity2.getActivityType() == "Slalom" || activity2.getActivityType() == "Telemark"){
                distance2 = Double.valueOf(activity2.getDistance())/2;
            } else distance2 = Double.valueOf(activity2.getDistance());
            return (int)(distance1 - distance2);
        } else return activity1.getActivityType().compareTo(activity2.getActivityType());
        }
    
}



    

