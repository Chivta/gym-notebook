package GymNotebook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Workout {
    private String title;
    private ArrayList<Exercise> exercises;


    public Workout(){
        exercises = new ArrayList<>();
    }

    public Workout(String title) {
        this.title = title;
        exercises = new ArrayList<>();
    }

    public void AddExercise(Exercise exercise){
        exercises.add(exercise);
    }

    @Override
    public String toString(){
        StringBuffer toReturn = new StringBuffer();

        toReturn.append(title);

        for(Exercise ex : exercises){
            toReturn.append(String.format("%n - %s", (ex.toString()).replace("-","--")));
        }

        return toReturn.toString();
    }
}
