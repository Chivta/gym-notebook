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
public abstract class Set implements UnitChangeListener {
    protected double weight;
    protected WeightUnits units;

    public void Notify(WeightUnits newUnits){
        switch (units){
            case kg:
                weight = UnitManger.KGtoLBS(weight);
                units = WeightUnits.lbs;
                break;
            case lbs:
                weight = UnitManger.LBStoKG(weight);
                units = WeightUnits.kg;
                break;
        }
    }
}

