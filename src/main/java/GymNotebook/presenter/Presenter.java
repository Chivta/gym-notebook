package GymNotebook.presenter;

import GymNotebook.model.Exercise;
import GymNotebook.model.Workout;
import GymNotebook.model.Set;
import GymNotebook.view.UIManager;
import GymNotebook.view.windows.NewExerciseWindow;
import GymNotebook.view.windows.NewRepSetWindow;
import GymNotebook.view.windows.NewWorkoutCreationWindow;
import GymNotebook.view.windows.WorkoutListViewWindow;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class Presenter {
    private final UIManager ui;
    public Presenter(UIManager uiManager) {
        ui = uiManager;
    }

    public void OpenWorkoutListView(){
        ui.ChangeWindow(new WorkoutListViewWindow(this));
    }

    public void GoBack(){
        ui.GoBack();
    }

    public List<String> GetWorkoutFilenamesSorted() {
        return FileManager.getAllWorkoutFilenamesSortedByDateDesc();
    }

    public void OpenWorkoutView(String filename){}

    Workout currentWorkout;
    public void OpenNewWorkoutCreation(){
        if(currentWorkout == null){
            currentWorkout = new Workout();
        }
        ui.ChangeWindow(new NewWorkoutCreationWindow(this, currentWorkout));
    }

    public void OpenNewExercise(){
        ui.ChangeWindow(new NewExerciseWindow(this));
    }

    public void OpenNewExercise(Exercise exercise){

    }

    Exercise currentExercise;
    public void SaveCurrentExercise(Exercise exercise){
        currentExercise = exercise;
    }

    public void OpenNewRepSet(){
        ui.ChangeWindow(new NewRepSetWindow(this));
    }

    public void OpenNewTimeSet(){
        System.out.println("NotImplemented");
        //ui.ChangeWindow(new NewRepSetWindow(this));
    }

    public void AddSetToCurrentExercise(Set set){
        currentExercise.AddSet(set);
    }


    public void AddExerciseToCurrentWorkout(Exercise exercise){
        currentWorkout.AddExercise(exercise);
    }






    public void ReturnToWorkoutAndAddExercise(Exercise exercise){
        currentWorkout.getExercises().add(exercise);
        ui.GoBack();
    }

}
