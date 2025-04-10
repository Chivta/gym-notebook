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

    @Override
    public String toString(){
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(title);
        for(Set set: sets){
            toReturn.append(String.format("%n - %s",set.toString()));
        }

        return toReturn.toString();
    }
}