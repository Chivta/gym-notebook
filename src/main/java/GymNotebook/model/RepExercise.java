package GymNotebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class RepExercise extends Exercise {
    private ArrayList<RepSet> sets;


    public RepExercise(String title) {
        super(title);
        this.sets = new ArrayList<>();
    }
}