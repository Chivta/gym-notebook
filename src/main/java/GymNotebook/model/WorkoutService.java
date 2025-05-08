package GymNotebook.model;

import java.util.List;

public class WorkoutService {
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
}
