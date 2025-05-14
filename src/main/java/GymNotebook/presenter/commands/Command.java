package GymNotebook.presenter.commands;

import GymNotebook.presenter.Presenter;

public abstract class Command implements ICommand {
    protected Presenter presenter;

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }
}
