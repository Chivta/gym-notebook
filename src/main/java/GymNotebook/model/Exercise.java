package GymNotebook.model;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Exercise {
    protected String title;
    private ArrayList<Set> sets;


    public Exercise() {
        sets = new ArrayList<>();
    }


    public void AddSet(Set set) {
        sets.add(set);
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(title != null ? title : "Untitled Exercise");
        if (sets != null && !sets.isEmpty()) {
            for (Set set : sets) {
                if (set != null) {

                    toReturn.append(String.format("%n - %s", set));
                }
            }
        }
        return toReturn.toString();
    }
}