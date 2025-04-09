package GymNotebook.view.windows;

import GymNotebook.presenter.Presenter;

import java.util.ArrayList;

public class NewWorkoutCreationWindow extends Window{
    private ArrayList<String> options;
    private int selected;
    private final Presenter presenter;

    public NewWorkoutCreationWindow(Presenter presenter){
        this.presenter = presenter;

        header = "New Exercise Creation";
        footer = "Enter option number";

    }

    @Override
    public void SendBody(){

    }
}
