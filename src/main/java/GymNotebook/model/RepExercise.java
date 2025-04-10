package GymNotebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class RepExercise extends Exercise {
    private ArrayList<Set> sets;

    public RepExercise(){
        sets = new ArrayList<>();
    }

    @Override
    public void AddSet(Set set){
        sets.add(set);
    }
}