package GymNotebook.service;

import GymNotebook.model.ExerciseType;
import GymNotebook.model.ParameterDescriptor;
import GymNotebook.model.Set;
import GymNotebook.presenter.UnitManager;
import GymNotebook.presenter.UnitManager.WeightUnits;

import java.util.ArrayList;
import java.util.List;

import static GymNotebook.service.WorkoutItemFormatter.WorkoutItemToString;

public class SetService implements BuildableItemService{
    private Set set;
    private final List<ParameterDescriptor> parametersList;
    private ExerciseType type;

    public SetService(){
        parametersList = new ArrayList<>();
    }

    public void StartNew(){
        parametersList.clear();
        set = new Set();
    }

    public void SetUnits(WeightUnits units){
        set.setUnits(units);
    }

    public void SetType(ExerciseType type){
        set.setType(type);
        this.type = type;
    }

    public List<ParameterDescriptor> GetParameters(){
        parametersList.clear();
        parametersList.add(
                new ParameterDescriptor(
                        "weight",
                        "Input weight in "+ set.getUnits(),
                        ParameterDescriptor.InputType.DOUBLE,
                        true));
        if(type == ExerciseType.Rep){
            parametersList.add(
                    new ParameterDescriptor(
                            "rep",
                            "Input repetitions",
                            ParameterDescriptor.InputType.INTEGER,
                            false));
        }else if(type == ExerciseType.Time){
            parametersList.add(
                    new ParameterDescriptor(
                            "time",
                            "Input time in seconds",
                            ParameterDescriptor.InputType.INTEGER,
                            false));
        }
        parametersList.add(
                new ParameterDescriptor(
                        "note",
                        "Enter a note for this set. (Optional)",
                        ParameterDescriptor.InputType.STRING,
                        true));
        return parametersList;
    }

    public void SetParameter(String key, Object value) {
        if (set == null) {
            throw new IllegalStateException("Set has not been initialized. Please call StartNewSet() first.");
        }

        switch (key) {
            case "weight":
                if (value instanceof Double) {
                    set.setWeight((UnitManager.RoundToDecimalPlaces((Double) value,2)));
                } else {
                    throw new IllegalArgumentException("Invalid value type for 'weight'. Expected Double.");
                }
                break;
            case "time":
                if (value instanceof Integer) {
                    set.setTime((Integer) value);
                } else {
                    throw new IllegalArgumentException("Invalid value type for 'weight'. Expected Double.");
                }
                break;
            case "rep":
                if (value instanceof Integer) {
                    set.setRepCount((Integer) value);
                } else {
                    throw new IllegalArgumentException("Invalid value type for 'weight'. Expected Double.");
                }
                break;
            case "note":
                set.setNote((String) value);
                break;
            default:
                throw new IllegalArgumentException("Unknown parameter: " + key);
        }
    }

    public Set Build(){
        return set;
    }

    public String ObjectToString(){
        return WorkoutItemToString(set);
    }

    public static void SwitchUnits(Set set){
        switch (set.getUnits()){
            case kg:
                set.setWeight(UnitManager.KGtoLBS(set.getWeight()));
                set.setUnits(WeightUnits.lbs);
                break;
            case lbs:
                set.setWeight(UnitManager.LBStoKG(set.getWeight()));
                set.setUnits(WeightUnits.kg);
                break;
        }
    }
}
