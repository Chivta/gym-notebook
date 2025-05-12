package GymNotebook.service;

import GymNotebook.model.*;
import GymNotebook.presenter.UnitManager.WeightUnits;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutService implements UnitChangeListener, Service{
    private Workout workout;
    @Getter
    private WeightUnits units;

    public WorkoutService(WeightUnits units){
        workout = new Workout();
        workout.setUnits(units);
        this.units = units;
    }

    public void SetTitle(String title){
        workout.setTitle(title);
    }

    public void addItem(WorkoutItem exercise){
        workout.getItems().add(exercise);
    }

    private static String CollectNestedObjects(WorkoutItem item){
        String toReturn = "";


        String title = item.getTitle();
        if(title != null && !title.isEmpty()) {
            toReturn = item.getTitle() + "\n";
        }
        String note = item.getNote();
        if(note != null && !note.isEmpty()){
            toReturn+=String.format("Note: %s\n",note);
        }

        List<WorkoutItem> items = item.getItems();
        if(items!=null && !items.isEmpty()) {
            List<String> collectedItems = new ArrayList<>();
            for (WorkoutItem nestedItem : items) {
                collectedItems.add(CollectNestedObjects(nestedItem));
            }
            toReturn += String.join("\n", collectedItems);
            toReturn = toReturn.replace("\n", "\n ");
        } else if(item instanceof Set set){
            toReturn += SetService.ObjectToString(set);
        }

        return toReturn;
    }

    public static String ObjectToString(Workout workout){
        return CollectNestedObjects(workout);
    }
    public String ObjectToString(){
        return ObjectToString(workout);
    }

    public WorkoutItem Build(){
        return workout;
    }

    public void Notify() {
        switch (workout.getUnits()) {
            case kg -> {
                workout.setUnits(WeightUnits.lbs);
                units = WeightUnits.lbs;
            }
            case lbs -> {
                workout.setUnits(WeightUnits.kg);
                units = WeightUnits.kg;
            }
        }
        SwitchUnits();

    }
    private void SwitchUnits(){
       for(WorkoutItem nestedItem : workout.getItems()){
           if(nestedItem instanceof Exercise){
               for(WorkoutItem set : nestedItem.getItems()){
                   set.SwitchUnits();
               }
           }else if(nestedItem instanceof SuperSet){
               for(WorkoutItem ex : nestedItem.getItems()){
                   for(WorkoutItem set : ex.getItems()){
                       set.SwitchUnits();
                   }
               }
           }
       }
    }
}
