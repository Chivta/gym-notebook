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
public class Workout implements WorkoutItem{
    private String title;
    private ArrayList<WorkoutItem> items;
    protected WeightUnits units;

    public Workout(){
        items = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Workout workout){
            if (!title.equals(workout.getTitle())) return false;
            if (items.size()!=workout.getItems().size()) return false;

            for(int i = 0; i < items.size(); i++){
                if(!items.get(i).equals(workout.getItems().get(i))){
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
        return Objects.hash(title, items);
    }
}
