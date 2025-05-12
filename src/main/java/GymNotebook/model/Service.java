package GymNotebook.model;

import java.util.List;

public interface Service {
    String ObjectToString();
    default List<WorkoutItem> GetItems(){
        throw new UnsupportedOperationException("Cannot get items from a leaf node.");
    }
    default void addItem(WorkoutItem item){
        throw new UnsupportedOperationException("Cannot add items to a leaf node.");
    }
    WorkoutItem Build();
}
