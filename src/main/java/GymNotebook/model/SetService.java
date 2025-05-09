package GymNotebook.model;

import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.UnitManger.WeightUnits;

import java.util.ArrayList;
import java.util.List;

public class SetService {
    private Set set;
    private List<ParameterDescriptor> parametersList;
    private ExerciseType type;

    public SetService(){
        parametersList = new ArrayList<>();
    }

    public void StartNewSet(ExerciseType type, WeightUnits units){
        set = new Set();
        set.setType(type);
        this.type = type;
        set.units = units;
    }

    public List<ParameterDescriptor> GetParameters(){
        parametersList.clear();
        parametersList.add(
                new ParameterDescriptor(
                        "weight",
                        "Input weight in "+set.units,
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
        return parametersList;
    }

    public void SetParameter(String key, Object value) {
        if (set == null) {
            throw new IllegalStateException("Set has not been initialized. Please call StartNewSet() first.");
        }

        switch (key) {
            case "weight":
                if (value instanceof Double) {
                    set.setWeight((UnitManger.RoundToDecimalPlaces((Double) value,2)));
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

            default:
                throw new IllegalArgumentException("Unknown parameter: " + key);
        }
    }

    public Set BuildSet(){
        return set;
    }

    public static String SetToString(Set set){
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
