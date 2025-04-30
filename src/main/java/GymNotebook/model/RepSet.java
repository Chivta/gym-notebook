package GymNotebook.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Setter
@Getter
@NoArgsConstructor
public class RepSet extends Set {
    private int repCount;

    public RepSet(double weight, int repCount){
        this.weight = weight;
        this.repCount = repCount;
    }


    @Override
    public String toString(){
        return String.format("%s %s : %s times",weight,units,repCount);
    }


    @Override
    public boolean equals(Object obj){
        if(obj instanceof RepSet repSet){

            return weight==repSet.getWeight() && repCount==repSet.getRepCount();
        }return false;
    }
}
