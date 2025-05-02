package GymNotebook.presenter.commands;

import GymNotebook.presenter.Presenter;

public class SaveCurrentWorkout implements Command{
    private Presenter presenter;

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void Execute(){
        presenter.saveCurrentWorkout();
    }
}
