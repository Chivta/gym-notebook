package GymNotebook.service;

import GymNotebook.model.*;

import java.util.List;

import static GymNotebook.service.WorkoutItemFormatter.WorkoutItemToString;

public class ExerciseService implements BuildableItemService, CompositeItemService {
    private Exercise exercise;

    public ExerciseService(){
        exercise = new Exercise();
    }

    public void SetTitle(String title){
        exercise.setTitle(title);
    }

    public void SetType(ExerciseType type){
        exercise.setType(type);
    }
    public ExerciseType GetType(){
        return exercise.getType();
    }

    public void AddItem(WorkoutItem item){
        exercise.getItems().add(item);
    }
    public List<WorkoutItem> GetItems() {return exercise.getItems();}

    public String ObjectToString() {
        return WorkoutItemToString(exercise);
    }

    public Exercise Build(){
        return exercise;
    }

}
