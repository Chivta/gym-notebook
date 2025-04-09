package GymNotebook.presenter;

import GymNotebook.model.Exercise;
import GymNotebook.model.Workout;
import GymNotebook.view.UIManager;
import GymNotebook.view.windows.NewExerciseWindow;
import GymNotebook.view.windows.NewWorkoutCreationWindow;
import GymNotebook.view.windows.WorkoutListViewWindow;

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
        currentWorkout = new Workout();
        ui.ChangeWindow(new NewWorkoutCreationWindow(this, currentWorkout));
    }

    public void OpenNewExercise(){
        ui.ChangeWindow(new NewExerciseWindow(this));
    }

    public void OpenNewSet(){
        ui.ChangeWindow(new NewExerciseWindow(this));
    }

    public void ReturnToWorkoutAndAddExercise(Exercise exercise){
        currentWorkout.getExercises().add(exercise);
        ui.GoBack();
    }

}
