package GymNotebook.model;

import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.UnitManger.WeightUnits;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutService implements UnitChangeListener, Service{
    private Workout workout;
    private String title;
    private List<WorkoutItem> items;
    @Getter
    private WeightUnits units;

    public WorkoutService(WeightUnits units){
        workout = new Workout();
        items = workout.getItems();
        title = workout.getTitle();
        workout.units = units;
        this.units = units;
    }

    public void SetTitle(String title){
        this.title = title;
        workout.setTitle(title);
    }

    public void addItem(WorkoutItem exercise){
        items.add(exercise);
    }
    public static String ObjectToString(Workout workout){
        StringBuilder toReturn = new StringBuilder();

        toReturn.append(workout.getTitle());

        for(WorkoutItem item : workout.getItems()){
            toReturn.append(String.format("%n - %s", (ExerciseService.ObjectToString((Exercise) item)).replace("-","--")));
        }

        return toReturn.toString();
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

        for (WorkoutItem item : items) {
            if (item instanceof Exercise exercise) {
                for (WorkoutItem set : exercise.getItems()) {
                    double weight = ((Set) set).getWeight();
                    ((Set) set).setWeight(units == WeightUnits.kg ? weight * 0.453592 : weight * 2.20462);

                }
            } else if (item instanceof SuperSet superset) {
                for (WorkoutItem exercise : superset.getItems()) {
                    for (WorkoutItem set : exercise.getItems()) {
                        double weight = ((Set) set).getWeight();
                        ((Set) set).setWeight(units == WeightUnits.kg ? weight * 0.453592 : weight * 2.20462);
                    }
                }
            }
        }


    }
}
