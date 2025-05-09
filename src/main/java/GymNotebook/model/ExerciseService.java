package GymNotebook.model;

import java.util.List;

public class ExerciseService {
    private Exercise exercise;
    private String title;
    private List<Set> sets;

    public void StartNewExercise(){
        exercise = new Exercise();
        this.title = exercise.getTitle();
        this.sets = exercise.getSets();
    }

    public void SetTitle(String title){
        this.title = title;
        exercise.setTitle(title);
    }

    public void SetType(ExerciseType type){
        exercise.setType(type);
    }
    public ExerciseType GetType(){
        return exercise.getType();
    }

    public void AddSet(Set set){
        sets.add(set);
    }

    public static String ExerciseToString(Exercise ex){
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(ex.getTitle() != null ? ex.getTitle() : "Untitled Exercise");
        if (ex.getSets() != null && !ex.getSets().isEmpty()) {
            for (Set set : ex.getSets()) {
                if (set != null) {
                    toReturn.append(String.format("%n - %s", SetService.SetToString(set)));
                }
            }
        }
        return toReturn.toString();
    }

    public String ExerciseToString() {
        return ExerciseToString(exercise);
    }

    public Exercise BuildExercise(){
        return exercise;
    }

}
