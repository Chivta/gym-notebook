package GymNotebook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class StrengthExercise extends Exercise {
    private ArrayList<StregthSet> sets;


    public StrengthExercise(String title) {
        super(title);
        this.sets = new ArrayList<>();
    }
}