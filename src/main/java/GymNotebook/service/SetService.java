package GymNotebook.service;

import GymNotebook.model.ExerciseType;
import GymNotebook.model.ParameterDescriptor;
import GymNotebook.model.Set;
import GymNotebook.presenter.UnitManager;
import GymNotebook.presenter.UnitManager.WeightUnits;

import java.util.ArrayList;
import java.util.List;

public class SetService implements BuildableItemService{
    private final Set set;
    private final List<ParameterDescriptor> parametersList;
    private final ExerciseType type;

    public SetService(ExerciseType type, WeightUnits units){
        parametersList = new ArrayList<>();
        set = new Set();
        set.setType(type);
        this.type = type;
        set.setUnits(units);
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
        return ObjectToString(set);
    }

    public static String ObjectToString(Set set){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(set.getWeight()).append(" ");
        stringBuilder.append(set.getUnits()).append(" ");
        if(set.getType()==ExerciseType.Rep){
            stringBuilder.append(set.getRepCount()).append(" times");
        }else if(set.getType()==ExerciseType.Time){
            stringBuilder.append(set.getTime()).append(" seconds");
        }
        return stringBuilder.toString();
    }
}
