package GymNotebook.model;

import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exercise {
    private String title;
    private ArrayList<Set> sets;
    private ExerciseType type;

    public Exercise() {
        sets = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Exercise exercise) {
            if (title == null || !title.equals(exercise.getTitle())) return false;
            if (sets.size() != exercise.getSets().size()) return false;

            for (int i = 0; i < sets.size(); i++) {
                if (!sets.get(i).equals(exercise.getSets().get(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, sets);
    }
}