package GymNotebook.model;

import GymNotebook.presenter.UnitManger.WeightUnits;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"title", "exercises", "units"})
public class Workout {
    private String title;
    private ArrayList<Exercise> exercises;
    protected WeightUnits units;

    public Workout(){
        exercises = new ArrayList<>();
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
