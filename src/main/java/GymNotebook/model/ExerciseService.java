package GymNotebook.model;

import java.util.List;

public class ExerciseService implements Service {
    private Exercise exercise;
    private List<WorkoutItem> items;

    public ExerciseService(){
        exercise = new Exercise();
        this.items = exercise.getItems();
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

    public void addItem(WorkoutItem item){
        items.add(item);
    }

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
