package GymNotebook.service;

import GymNotebook.model.*;
import GymNotebook.presenter.UnitManager.WeightUnits;
import lombok.Getter;

import java.util.List;

import static GymNotebook.service.WorkoutItemFormatter.WorkoutItemToString;

public class WorkoutService implements UnitChangeListener, BuildableItemService, CompositeItemService{
    private Workout workout;
    @Getter
    private WeightUnits units;

    public void StartNew(){
        workout = new Workout();
    }

    public void SetUnits(WeightUnits units){
        this.units = units;
        workout.setUnits(units);
    }

    public void SetTitle(String title){
        workout.setTitle(title);
    }

    public void AddItem(WorkoutItem exercise){ workout.getItems().add(exercise); }
    public List<WorkoutItem> GetItems(){ return workout.getItems(); }


    public String ObjectToString(){
        return WorkoutItemToString(workout);
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
