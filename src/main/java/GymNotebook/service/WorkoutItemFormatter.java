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
            toReturn = item.getClass().getSimpleName() + ": " + item.getTitle() + "\n";
        }else{
            toReturn = item.getClass().getSimpleName() + ": \n";
        }
        String note = item.getNote();
        if(note != null && !note.isEmpty()){
            toReturn+=String.format(" Note: %s\n",note);
        }

        List<WorkoutItem> items = item.getItems();
        if(items!=null && !items.isEmpty()) {
            List<String> collectedItems = new ArrayList<>();
            for (WorkoutItem nestedItem : items) {
                collectedItems.add(CollectNestedObjects(nestedItem));
            }
            toReturn += String.join("\n", collectedItems);
            toReturn = toReturn.replace("\n", "\n ");
        } else if(item instanceof Set set){
            toReturn += String.format(" %f %s - %s",set.getWeight(),set.getUnits(),
                    (set.getType()== ExerciseType.Rep ?  set.getRepCount()+" times":set.getTime() + " seconds"));
        }

        return toReturn;
    }
}
