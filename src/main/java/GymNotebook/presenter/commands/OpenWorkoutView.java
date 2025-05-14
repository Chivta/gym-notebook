package GymNotebook.presenter.commands;

import GymNotebook.presenter.Presenter;

public class OpenWorkoutView implements ICommand {
    private Presenter presenter;
    private final String fileName;

    public OpenWorkoutView(String filename){
        this.fileName = filename;
    }

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void Execute(){
        presenter.OpenWorkoutView(fileName);
    }
}
