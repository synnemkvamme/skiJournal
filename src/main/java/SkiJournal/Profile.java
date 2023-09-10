package SkiJournal;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Profile{
    private String userName;
    private double distanceGoal;
    private double totalDistance;
    private int skiDays;
    private ArrayList<Activity> activities;
    
    public Profile (String userName){
        this.userName = userName;
        this.activities = new ArrayList<>();
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setDistanceGoal(double distanceGoal) throws IllegalArgumentException{
        validGoal(distanceGoal);
        this.distanceGoal = distanceGoal;
    }

    private void validGoal(double goal) throws IllegalArgumentException{
        if (goal < 0) throw new IllegalArgumentException("Distansemålet må være et positivt nummer.\nDitt nye distansemål ble ikke lagret."); 
        else if (goal < this.getTotalDistance()) throw new IllegalArgumentException("Distansemålet må være høyere enn allerede oppnådd distanse: " + this.getTotalDistance() + ".\nDitt nye distansemål ble ikke lagret.");
    }

    public double getDistanceGoal(){
        return this.distanceGoal;
    }

    public double getTotalDistance(){
        return totalDistance;
    }

    public int getSkiDays(){
        return this.skiDays;
    }

    public List<Activity> getActivitiesList(){
        return new ArrayList<>(activities);
    }

    public List<Activity> getActivitiesByType(String type){
        List<Activity> activitiesByType = activities.stream().filter(activity -> activity.getActivityType().equals(type)).toList();
        return new ArrayList<>(activitiesByType);
    }

    public double getDistanceGoalAchivement(){ 
        if (this.distanceGoal != 0){
            return totalDistance/distanceGoal;
        } else return 0;
    }

    public void addActivity(Activity activity){
        if (activity.getActivityType().equals("Langrenn")){
            this.activities.add(activity);
        } else if (activity.getActivityType().equals("Slalom")|| activity.getActivityType().equals("Telemark")){
            this.activities.add(activity);
        } else if (activity.getActivityType().equals("Topptur")){
            this.activities.add(activity);
        }   
        this.skiDays += 1;
        this.totalDistance += Double.valueOf(activity.getDistance());
        updateActivitiesList();
    }

    public void addActivity(String title, LocalDate date, String text, String distance, String activityType, 
            String extraInfoOne, String extraInfoTwo){
        String extraInfoOneCalculated = "0";
        String extraInfoTwoCalculated = "0";
        if (activityType.equals("Langrenn")){
            this.activities.add(new CrossCountrySkiing(title, date, text, distance, activityType, extraInfoOneCalculated, extraInfoTwoCalculated, extraInfoOne, extraInfoTwo));
        } else if (activityType.equals("Slalom")|| activityType.equals("Telemark")){
            this.activities.add(new SlalomTelemark(title, date, text, distance, activityType, extraInfoOneCalculated, extraInfoTwoCalculated, extraInfoOne, extraInfoTwo));
        } else if (activityType.equals("Topptur")){
            this.activities.add(new Randonee(title, date, text, distance, activityType, extraInfoOneCalculated, extraInfoTwoCalculated, extraInfoOne, extraInfoTwo));
        }
        this.skiDays += 1;
        this.totalDistance += Double.valueOf(distance);
        updateActivitiesList();
    }

    public void addActivity(String title, LocalDate date, String text, String distance, String activityType, 
            String extraInfoOneCalculated, String extraInfoTwoCalculated, String extraInfoOne, String extraInfoTwo){
        if (activityType.equals("Langrenn")){
            this.activities.add(new CrossCountrySkiing(title, date, text, distance, activityType, extraInfoOneCalculated, extraInfoTwoCalculated, extraInfoOne, extraInfoTwo));
        } else if (activityType.equals("Slalom")|| activityType.equals("Telemark")){
            this.activities.add(new SlalomTelemark(title, date, text, distance, activityType, extraInfoOneCalculated, extraInfoTwoCalculated, extraInfoOne, extraInfoTwo));
        } else if (activityType.equals("Topptur")){
            this.activities.add(new Randonee(title, date, text, distance, activityType, extraInfoOneCalculated, extraInfoTwoCalculated, extraInfoOne, extraInfoTwo));
        }
        this.skiDays += 1;
        this.totalDistance += Double.valueOf(distance);
        updateActivitiesList();
    }

    private void updateActivitiesList(){
        this.activities = new ArrayList<Activity>(getActivitiesSortedByDate());
    }

    public void removeActivity(Activity activity){
        if (activities.contains(activity)){
            activities.remove(activity);
            skiDays -= 1;
            totalDistance -= Double.valueOf(activity.getDistance());
        } else throw new IllegalArgumentException("Ikke mulig å fjerne aktivitet som ikke befinner seg i profilens aktivitetsliste");
    }

    public List<Activity> getActivitiesSortedByType(){
        List<Activity> activitiesComparedByType= new ArrayList<>(activities);
        Collections.sort(activitiesComparedByType, new ActivitiesTypeComparator());
        return activitiesComparedByType;
    }

    public List<Activity> getActivitiesSortedByDate(){
        List<Activity> activitiesComparedByDate = new ArrayList<>(activities);
        Collections.sort(activitiesComparedByDate, new ActivitiesDateComparator());
        return activitiesComparedByDate;
    }

    @Override
    public String toString() {
        return "activities=" + activities + ", distanceGoal=" + distanceGoal + ", name="
                + userName;
    }


    public static void main(String[] args) {
        Profile synne = new Profile("Synne");
        synne.addActivity(new Randonee("Skåråsalen", LocalDate.of(2022, Month.APRIL, 8), "Bra snø og kule folk!", "12", "Topptur", "1200", "2"));
        synne.addActivity(new CrossCountrySkiing("Langrennstur", LocalDate.of(2022, Month.APRIL, 1), "Det var en fin tur!", "12", "Langrenn", "VR45", "1"));
        synne.addActivity(new SlalomTelemark("Vassfjellet", LocalDate.of(2022, Month.APRIL, 4), "Skuffer aldri, bra tur!", "54", "Slalom", "3453", "300"));
        
        // synne.addActivity("title", LocalDate.now(), "text","1.0", "Langrenn", "2", "2");
        // synne.addActivity("title", LocalDate.now(), "text", "3.0", "Langrenn", "2", "2");
        // synne.addActivity("title", LocalDate.now(), "text", "2.0", "Langrenn", "2", "2");
        synne.getActivitiesByType("Langrenn");
        System.out.println(synne.getActivitiesList());
      
      

        // synne.setDistanceGoal(100);
        // System.out.println("---------------");

        // System.out.println("::::" + synne.getActivitiesByType("Langrenn"));
        // System.out.println("---------------");
        // System.out.println(synne.getActivitiesList());

        // System.out.println(synne.getDistanceGoalAchivement());
        // System.out.println(synne.activities);
        // //synne.compareDistances();
        // System.out.println(synne.activities);




    }

    


    

   
}
