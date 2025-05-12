package GymNotebook.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exercise implements WorkoutItem {
    private String title;
    private List<WorkoutItem> items;
    private ExerciseType type;

    public Exercise() {
        items = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Exercise exercise) {
            if (title == null || !title.equals(exercise.getTitle())) return false;
            if (items.size() != exercise.getItems().size()) return false;

            for (int i = 0; i < items.size(); i++) {
                if (!items.get(i).equals(exercise.getItems().get(i))) {
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
        return Objects.hash(title, items);
    }
}