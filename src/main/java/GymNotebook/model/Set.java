package GymNotebook.model;

import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.UnitManger.WeightUnits;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RepSet.class, name = "RepSet"),
        @JsonSubTypes.Type(value = TimeSet.class, name = "TimeSet"),
})
public class Set {
    protected double weight;
    protected WeightUnits units;

    private ExerciseType type;
}

