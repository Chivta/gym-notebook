package GymNotebook.presenter.commands;

import GymNotebook.presenter.Presenter;

public interface Command {
    void SetPresenter(Presenter presenter);
    void Execute();
}
