package GymNotebook.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

}
