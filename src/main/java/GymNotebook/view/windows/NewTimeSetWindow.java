package GymNotebook.view.windows;

import GymNotebook.model.RepSet;
import GymNotebook.presenter.Presenter;

public class NewTimeSetWindow extends Window{
    Presenter presenter;
    State state;
    RepSet set;

    public NewTimeSetWindow(Presenter presenter){
        this.presenter = presenter;

        header = "Set adding";

        set = new RepSet();

        state = State.SettingWeight;
    }

    enum State{
        SettingWeight,
        SettingTime,
    }

    @Override
    public void SendBody(){
        switch (state){
            case SettingWeight:
                SendSettingWeight();
                break;
            case SettingTime:
                SendSettingRep();
                break;
        }
    }

    private void SendSettingWeight(){
        System.out.printf("%s%n","Write weight (in kg).");
        state = State.SettingWeight;
    }

    private void SendSettingRep(){
        System.out.printf("%s%n","Write time (in seconds).");
        state = State.SettingTime;
    }

    @Override
    public void HandleInput(String input){
        switch (state){
            case SettingWeight:
                HandleSettingWeight(input);
                break;
            case SettingTime:
                HandleSettingTime(input);
                break;
        }
    }

    private void HandleSettingWeight(String input){
        try{
            int weigth = Integer.parseInt(input);

            set.setWeight(weigth);

            state = State.SettingTime;

        } catch (NumberFormatException e) {
            info = "ERR: Invalid input format";
        }
    }

    private void HandleSettingTime(String input){
        try{
            int rep = Integer.parseInt(input);

            if(rep<1){
                info = "ERR: Time can't be lower then 1";
            }

            set.setRepCount(rep);

            presenter.AddSetToCurrentExercise(set);
            presenter.GoBack();

        } catch (NumberFormatException e) {
            info = "ERR: Invalid input format";
        }
    }

}
