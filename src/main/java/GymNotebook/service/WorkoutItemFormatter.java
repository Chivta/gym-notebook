package GymNotebook.service;

import GymNotebook.model.ExerciseType;
import GymNotebook.model.Set;
import GymNotebook.model.WorkoutItem;

import java.util.ArrayList;
import java.util.List;

public class WorkoutItemFormatter {
    public static String WorkoutItemToString(WorkoutItem item){
        return CollectNestedObjects(item);
    }
    private static String CollectNestedObjects(WorkoutItem item){
        String toReturn = "";

        String title = item.getTitle();
        if(title != null && !title.isEmpty()) {
            toReturn = item.getClass().getSimpleName() + ": " + item.getTitle();
        }else{
            toReturn = item.getClass().getSimpleName() + ": ";
        }
        String note = item.getNote();
        if(note != null && !note.isEmpty()){
            toReturn+=String.format("%n Note: %s",note);
        }

        String description = item.getDescription();
        if(description!=null && !description.isEmpty()){
            toReturn+= "\n "+description;
        }

        List<WorkoutItem> items = item.getItems();
        if(items!=null && !items.isEmpty()) {
            List<String> collectedItems = new ArrayList<>();
            for (WorkoutItem nestedItem : items) {
                collectedItems.add(CollectNestedObjects(nestedItem));
            }
            toReturn += "\n";
            toReturn += String.join("\n", collectedItems);
            toReturn = toReturn.replace("\n", "\n ");
        }

        return toReturn;
    }
}
