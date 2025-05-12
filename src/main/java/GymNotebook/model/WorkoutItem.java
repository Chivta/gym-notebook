package GymNotebook.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface WorkoutItem {
    default void SwitchUnits(){}
//    default String getTitle(){
//        throw new UnsupportedOperationException("Not supported for this object.");
//    }
    default List<WorkoutItem> getItems(){
        throw new UnsupportedOperationException("Not supported for this object.");
    }
}