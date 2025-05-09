package GymNotebook.model;

import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.UnitManger.WeightUnits;

import java.util.List;

public class WorkoutService implements UnitChangeListener{
    private Workout workout;
    private String title;
    private List<Exercise> exercises;

    public void StartNewWorkout(WeightUnits units){
        workout = new Workout();
        exercises = workout.getExercises();
        title = workout.getTitle();
        workout.units = units;
    }

    public void SetTitle(String title){
        this.title = title;
        workout.setTitle(title);
    }

    public void AddExercise(Exercise exercise){
        exercises.add(exercise);
    }
    public static String WorkoutToString(Workout workout){
        StringBuilder toReturn = new StringBuilder();

        toReturn.append(workout.getTitle());

        for(Exercise ex : workout.getExercises()){
            toReturn.append(String.format("%n - %s", (ExerciseService.ExerciseToString(ex)).replace("-","--")));
        }

        return toReturn.toString();
    }
    public String WorkoutToString(){
        return WorkoutToString(workout);
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
