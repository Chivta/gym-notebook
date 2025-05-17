package GymNotebook.service;

import GymNotebook.model.SuperSet;
import GymNotebook.model.WorkoutItem;

import java.util.ArrayList;
import java.util.List;

import static GymNotebook.service.WorkoutItemFormatter.WorkoutItemToString;

public class SuperSetService implements BuildableItemService, CompositeItemService{
    private SuperSet superSet;

    public void StartNew(){
        superSet = new SuperSet();
    }

    private void UpdateTitle(){
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

    public String ObjectToString(){
        return WorkoutItemToString(superSet);
    }

    public SuperSet Build(){
        return superSet;
    }
}
