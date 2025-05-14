package GymNotebook.presenter.commands;

import GymNotebook.presenter.Presenter;

public interface ICommand {
    void SetPresenter(Presenter presenter);
    void Execute();
}
