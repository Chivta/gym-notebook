package GymNotebook.model;

import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.UnitManger.WeightUnits;
import java.util.List;

public class WorkoutService implements UnitChangeListener{
    private Workout workout;
    private String title;
    private List<Exercise> exercises;


    public void StartNewWorkout(){
        workout = new Workout();
        exercises = workout.getExercises();
        title = workout.getTitle();
    }

    public void SetTitle(String title){
        this.title = title;
        workout.setTitle(title);
    }

    public void AddExercise(Exercise exercise){
        exercises.add(exercise);
    }

    public String ToStringWorkout(){
        StringBuilder toReturn = new StringBuilder();

        toReturn.append(title);

        for(Exercise ex : exercises){
            toReturn.append(String.format("%n - %s", (ex.toString()).replace("-","--")));
        }

        return toReturn.toString();
    }

    public Workout BuildWorkout(){
        return workout;
    }

    public void Notify(){
        for (Exercise ex : exercises){
            for (Set set : ex.getSets()){
                switch (set.units){
                    case kg:
                        set.weight = UnitManger.KGtoLBS(set.weight);
                        set.units = WeightUnits.lbs;
                        break;
                    case lbs:
                        set.weight = UnitManger.LBStoKG(set.weight);
                        set.units = WeightUnits.kg;
                        break;
                }
            }
        }
    }
}
