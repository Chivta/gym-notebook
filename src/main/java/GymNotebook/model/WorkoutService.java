package GymNotebook.model;

import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.UnitManger.WeightUnits;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutService implements UnitChangeListener, Service{
    private Workout workout;
    @Getter
    private WeightUnits units;

    public WorkoutService(WeightUnits units){
        workout = new Workout();
        workout.units = units;
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

        try{
            toReturn = item.getTitle() + "\n";
            List<WorkoutItem> items = item.getItems();
            if(!items.isEmpty()){
                List<String> collectedItems = new ArrayList<>();
                for(WorkoutItem nestedItem : items){
                    collectedItems.add(CollectNestedObjects(nestedItem));
                }
                toReturn+=String.join("\n",collectedItems);
                toReturn = toReturn.replace("\n","\n-");
            }

        }catch (UnsupportedOperationException e){
            toReturn += SetService.ObjectToString((Set) item);
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
        switch (workout.units) {
            case kg -> {
                workout.units = WeightUnits.lbs;
                units = WeightUnits.lbs;
            }
            case lbs -> {
                workout.units = WeightUnits.kg;
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
