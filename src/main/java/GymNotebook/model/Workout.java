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

    public Workout(String title) {
        this.title = title;
        exercises = new ArrayList<>();
    }
}
