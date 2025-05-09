package GymNotebook.model;

import java.util.ArrayList;
import java.util.List;

public class SetService {
    private Set set;
    private List<ParameterDescriptor> parametersList;
    private ExerciseType type;

    public SetService(){
        parametersList = new ArrayList<>();
    }

    public void StartNewSet(ExerciseType type){
        set = new Set();
        set.setType(type);
        this.type = type;
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

}
