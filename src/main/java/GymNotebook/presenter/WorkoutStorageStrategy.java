package GymNotebook.presenter;

import GymNotebook.model.Workout;
import java.io.IOException;

public interface WorkoutStorageStrategy {

    void saveWorkout(Workout workout, String filename) throws IOException;

    Workout loadWorkout(String filename) throws IOException;
}