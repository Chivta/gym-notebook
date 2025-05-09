package GymNotebook.presenter.commands;

import GymNotebook.presenter.Presenter;

public class SetParameter  implements Command{
    private Presenter presenter;
    private String key;
    private Object value;

    public SetParameter(String key, Object value){
        this.key = key;
        this.value = value;
    }

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void Execute(){
        presenter.SetParameter(key,value);
    }
}
