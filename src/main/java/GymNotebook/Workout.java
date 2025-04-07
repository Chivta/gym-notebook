package GymNotebook;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    private ArrayList<Set> sets;
    private String title;


}
