package GymNotebook.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Setter
@Getter
@NoArgsConstructor
public class TimeSet extends Set{
    private int time;

    public TimeSet(double weight, int time){
       this.weight = weight;
       this.time = time;
    }

    @Override
    public String toString(){
        return String.format("%s %s : %s sec",weight,units,time);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof TimeSet timeSet){

            return weight==timeSet.getWeight() && time==timeSet.getTime();
        }return false;
    }
}
