package GymNotebook.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepSet extends Set {
    private int repCount;
    private int weight;

    @Override
    public String toString(){
        return String.format("%s kg : %s",weight,repCount);
    }


    @Override
    public boolean equals(Object obj){
        if(obj instanceof RepSet repSet){

            return weight==repSet.getWeight() && repCount==repSet.getRepCount();
        }return false;
    }
}
