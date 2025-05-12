package GymNotebook.presenter.commands;

import GymNotebook.presenter.Presenter;

public class OpenNewSuperSet implements Command{
    private Presenter presenter;

    @Override
    public void Execute() {
        presenter.OpenNewSuperSet();
    }
    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }
}
