package GymNotebook.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSet extends Set{
    private int time;
    private int weight;

    @Override
    public String toString(){
        return String.format("%s kg : %s sec",weight,time);
    }
}
