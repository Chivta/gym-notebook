package GymNotebook.service;

import GymNotebook.model.WorkoutItem;

import java.util.List;

public interface CompositeItemService {
    List<WorkoutItem> GetItems();
    void AddItem(WorkoutItem item);
}
