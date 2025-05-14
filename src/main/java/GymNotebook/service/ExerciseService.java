package GymNotebook.service;

import GymNotebook.model.*;

import java.util.List;

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
    public static String ObjectToString(Exercise ex){
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(ex.getTitle() != null ? ex.getTitle() : "Untitled Exercise");
        if (ex.getItems() != null && !ex.getItems().isEmpty()) {
            for (WorkoutItem item : ex.getItems()) {
                if (item != null) {
                    toReturn.append(String.format("%n - %s", SetService.ObjectToString((Set) item)));
                }
            }
        }
        return toReturn.toString();
    }

    public String ObjectToString() {
        return ObjectToString(exercise);
    }

    public Exercise Build(){
        return exercise;
    }

}
