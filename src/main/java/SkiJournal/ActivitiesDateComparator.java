package SkiJournal;

import java.util.Comparator;

public class ActivitiesDateComparator implements Comparator<Activity>{

    @Override
    public int compare(Activity activity1, Activity activity2) {
        if (activity1.getDate().isBefore(activity2.getDate())){
            return 1;
        } else if (activity1.getDate().isAfter(activity2.getDate())){
            return -1;
        } else{
            String title1 = activity1.getTitle();
            String title2 = activity2.getTitle();
            return title1.compareTo(title2);
        }
    }
}
