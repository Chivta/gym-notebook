package GymNotebook.model;

import GymNotebook.presenter.UnitManger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Workout {
    private String title;
    private ArrayList<Exercise> exercises;

    public Workout(){
        exercises = new ArrayList<>();
    }

    public Workout(String title) {
        this.title = title;
        exercises = new ArrayList<>();
    }

    public void AddExercise(Exercise exercise){
        exercises.add(exercise);
    }

    @Override
    public String toString(){
        StringBuffer toReturn = new StringBuffer();

        toReturn.append(title);

        for(Exercise ex : exercises){
            toReturn.append(String.format("%n - %s", (ex.toString()).replace("-","--")));
        }

        return toReturn.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Workout workout){
            if (!title.equals(workout.getTitle())) return false;
            if (exercises.size()!=workout.getExercises().size()) return false;

            for(int i = 0; i < exercises.size(); i++){
                if(!exercises.get(i).equals(workout.getExercises().get(i))){
                    return false;
                }
            }

            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, exercises);
    }
}
