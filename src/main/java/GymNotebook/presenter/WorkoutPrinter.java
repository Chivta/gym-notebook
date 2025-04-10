package GymNotebook.presenter;

import GymNotebook.model.*;

public class WorkoutPrinter {
    public static void PrintWorkout(Workout workout){
        System.out.printf("==== %s ====%n",workout.getTitle());
        for(Exercise exercise : workout.getExercises()){
            System.out.printf("  -- %s --%n",exercise.getTitle());
            for(Set set : exercise.getSets()){
                if (set instanceof RepSet){
                    System.out.printf("    - %d kg : %d%n",((RepSet)set).getWeight(),((RepSet)set).getRepCount());
                }else{
                    System.out.printf("    - %d kg : %d sec%n",((TimeSet)set).getWeight(),((TimeSet)set).getTime());
                }

            }
        }
    }
}
