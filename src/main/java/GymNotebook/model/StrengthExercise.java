package GymNotebook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class StrengthExercise extends Exercise {
    private ArrayList<StregthSet> sets;

    public StrengthExercise() {}

    public StrengthExercise(String title) {
        this.title = title;
        this.sets = new ArrayList<>();
    }
}