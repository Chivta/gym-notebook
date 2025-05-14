package GymNotebook.model;

import GymNotebook.presenter.UnitManager;
import GymNotebook.presenter.UnitManager.WeightUnits;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"title", "items"})
public class Set implements WorkoutItem {
    protected double weight;
    protected WeightUnits units;
    private ExerciseType type;
    private int repCount;
    private int time;
    private String note;
}

