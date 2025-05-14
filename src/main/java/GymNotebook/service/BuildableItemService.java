package GymNotebook.service;

import GymNotebook.model.WorkoutItem;

public interface BuildableItemService {
    WorkoutItem Build();
    String ObjectToString();
}
