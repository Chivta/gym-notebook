package GymNotebook.model;

import GymNotebook.presenter.UnitManger.WeightUnits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Set {
    protected double weight;
    protected WeightUnits units;
    private ExerciseType type;
    private int repCount;
    private int time;
}

