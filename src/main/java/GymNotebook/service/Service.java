package GymNotebook.service;

import GymNotebook.model.WorkoutItem;

import java.util.List;

public interface Service {
    String ObjectToString();
    default void addItem(WorkoutItem item){
        throw new UnsupportedOperationException("Cannot add items to a leaf node.");
    }
    WorkoutItem Build();
}
