package GymNotebook.service;

import GymNotebook.model.Exercise;
import GymNotebook.model.SuperSet;
import GymNotebook.model.WorkoutItem;

import java.util.ArrayList;
import java.util.List;

public class SuperSetService implements BuildableItemService, CompositeItemService{
    private final SuperSet superSet;

    public SuperSetService(){
        superSet = new SuperSet();
    }

    public void UpdateTitle(){
        List<String> titles = new ArrayList<>();
        for(WorkoutItem item : superSet.getItems()){
            titles.add(item.getTitle());
        }
        superSet.setTitle(String.join("+", titles));
    }

    public void AddItem(WorkoutItem item){
        superSet.getItems().add(item);
        UpdateTitle();
    }
    public List<WorkoutItem> GetItems(){
        return superSet.getItems();
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
