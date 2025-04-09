package GymNotebook.presenter;

import GymNotebook.view.UIManager;
import GymNotebook.view.windows.WorkoutListViewWindow;

import java.util.ArrayList;
import java.util.List;

public class Presenter {
    private final UIManager ui;
    public Presenter(UIManager uiManager) {
        ui = uiManager;
    }

    public void OpenWorkoutListView(){
        ui.setCurrentWindow(new WorkoutListViewWindow(this));
    }

    public void GoBack(){}

    public List<String> GetWorkoutFilenamesSorted() {
        return FileManager.getAllWorkoutFilenamesSortedByDateDesc();
    }

    public void OpenWorkoutView(String filename){}
}
