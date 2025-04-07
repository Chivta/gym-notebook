package GymNotebook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    private String title;
    private List<Set> sets;

    public Exercise(String title) {
        this.title = title;
        sets = new ArrayList<>();
    }

}
