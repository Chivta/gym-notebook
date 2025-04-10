package GymNotebook.view.windows;

import GymNotebook.model.RepSet;
import GymNotebook.presenter.Presenter;

public class NewRepSetWindow extends Window{
    Presenter presenter;
    State state;
    RepSet set;

    public NewRepSetWindow(Presenter presenter){
        this.presenter = presenter;

        header = "Set adding";

        set = new RepSet();

        state = State.SettingWeight;
    }

    enum State{
        SettingWeight,
        SettingRep,
    }

    @Override
    public void SendBody(){
        switch (state){
            case SettingWeight:
                SendSettingWeight();
                break;
            case SettingRep:
                SendSettingRep();
                break;
        }
    }

    private void SendSettingWeight(){
        System.out.printf("%s%n","Set weight (in kg).");
        state = State.SettingWeight;
    }

    private void SendSettingRep(){
        System.out.printf("%s%n","Set amount of reps.");
        state = State.SettingRep;
    }

    @Override
    public void HandleInput(String input){
        switch (state){
            case SettingWeight:
                HandleSettingWeight(input);
                break;
            case SettingRep:
                HandleSettingRep(input);
                break;
        }
    }

    private void HandleSettingWeight(String input){
        try{
            int weigth = Integer.parseInt(input);

            set.setWeight(weigth);

            state = State.SettingRep;

        } catch (NumberFormatException e) {
            info = "ERR: Invalid input format";
        }
    }

    private void HandleSettingRep(String input){
        try{
            int rep = Integer.parseInt(input);

            if(rep<1){
                info = "ERR: Reps can't be lower then 1";
            }

            set.setRepCount(rep);

            presenter.AddSetToCurrentExercise(set);
            presenter.GoBack();

        } catch (NumberFormatException e) {
            info = "ERR: Invalid input format";
        }
    }

}
