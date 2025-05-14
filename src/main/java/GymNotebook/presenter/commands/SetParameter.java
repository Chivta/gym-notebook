package GymNotebook.presenter.commands;

public class SetParameter  extends Command {
    private String key;
    private Object value;

    public SetParameter(String key, Object value){
        this.key = key;
        this.value = value;
    }

    public void Execute(){
        presenter.SetParameter(key,value);
    }
}
