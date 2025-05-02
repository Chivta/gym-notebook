package GymNotebook.presenter;

public interface Command {
    void SetPresenter(Presenter presenter);
    void Execute();
}
