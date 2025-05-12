package GymNotebook.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class SuperSet implements WorkoutItem {
    private String title;
    private ArrayList<WorkoutItem> items;
    public SuperSet(){
        items = new ArrayList<>();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SuperSet superSet) {
            if (title == null || !title.equals(superSet.getTitle())) return false;
            if (items.size() != superSet.getItems().size()) return false;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, items);
    }
}
