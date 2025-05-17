package GymNotebook.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface WorkoutItem {
    default String getTitle(){
        return null;
    }
    default List<WorkoutItem> getItems(){
        return null;
    }
    default String getNote(){
        return "";
    }
    default String getDescription(){return "";}
}