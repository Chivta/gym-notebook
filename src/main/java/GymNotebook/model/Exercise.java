package GymNotebook.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RepExercise.class, name = "RepExercise"),
})
public abstract class Exercise{
    protected String title;
    private ArrayList<Set> sets;

    protected Exercise(){
        sets = new ArrayList<>();
    }

    public void AddSet(Set set){

        sets.add(set);
    }


    @Override
    public String toString(){
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(title);
        for(Set set: sets){
            toReturn.append(String.format("%n - %s",set.toString()));
        }

        return toReturn.toString();
    }
}
