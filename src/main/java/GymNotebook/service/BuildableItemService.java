package GymNotebook.service;

import GymNotebook.model.WorkoutItem;

public interface BuildableItemService {
    void StartNew();
    WorkoutItem Build();
    String ObjectToString();
}
