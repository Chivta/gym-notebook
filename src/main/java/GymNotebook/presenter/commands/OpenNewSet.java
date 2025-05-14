package GymNotebook.presenter.commands;

import GymNotebook.presenter.Presenter;

public class OpenNewSet implements ICommand {
    private Presenter presenter;

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void Execute(){
        presenter.OpenNewSet();
    }
}
