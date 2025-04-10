package GymNotebook.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepSet extends Set {
    private int repCount;
    private int weight;


    @Override
    public String toString(){
        return String.format("%s kg : %s",weight,repCount);
    }
}
