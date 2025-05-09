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


    public String ToStringExercise() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(title != null ? title : "Untitled Exercise");
        if (sets != null && !sets.isEmpty()) {
            for (Set set : sets) {
                if (set != null) {

                    toReturn.append(String.format("%n - %s", set));
                }
            }
        }
        return toReturn.toString();
    }

    public Exercise BuildExercise(){
        return exercise;
    }

}
