package GymNotebook.presenter;

import GymNotebook.model.*;

public class WorkoutPrinter {
    public static void PrintWorkout(Workout workout){
        System.out.printf("==== %s ====%n",workout.getTitle());
        for(Exercise exercise : workout.getExercises()){
            System.out.printf("  -- %s --%n",exercise.getTitle());
            for(Set set : exercise.getSets()){
                System.out.printf("    -- %s%n" ,set.toString());
            }
        }
    }
}
