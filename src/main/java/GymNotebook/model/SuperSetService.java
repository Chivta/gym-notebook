package GymNotebook.model;

import java.util.ArrayList;
import java.util.List;

public class SuperSetService implements Service{
    private List<WorkoutItem> items;
    private final SuperSet superSet;

    public SuperSetService(){
        superSet = new SuperSet();
        items = superSet.getItems();
    }

    public void UpdateTitle(){
        List<String> titles = new ArrayList<>();
        for(WorkoutItem item : items){
            titles.add(item.getTitle());
        }
        superSet.setTitle(String.join("+", titles));
    }

    public void addItem(WorkoutItem item){
        items.add(item);
        UpdateTitle();
    }

    public static String ObjectToString(SuperSet superSet){
        StringBuffer toReturn = new StringBuffer();
        toReturn.append(superSet.getTitle()).append("\n");
        List<String> items = new ArrayList<>();
        for(WorkoutItem item : superSet.getItems()){
            items.add(ExerciseService.ObjectToString((Exercise) item));
        }
        toReturn.append(String.join("\n", items));
        return toReturn.toString();
    }

    public String ObjectToString(){
        return ObjectToString(superSet);
    }

    public SuperSet Build(){
        return superSet;
    }
}
