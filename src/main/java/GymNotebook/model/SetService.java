package GymNotebook.model;

import java.util.ArrayList;
import java.util.List;

public class SetService {
    private Set set;
    private List<String> parametersList;

    public SetService(){
        set = new Set();
        parametersList = new ArrayList<>(){{add("Weight");add("Rep");}};
    }


}
