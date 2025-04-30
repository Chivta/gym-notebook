package GymNotebook.view.windows;

import GymNotebook.model.RepSet;
import GymNotebook.presenter.Presenter;
import GymNotebook.presenter.UnitManger;

public class NewRepSetWindow extends Window{
    Presenter presenter;
    State state;
    RepSet set;
    UnitManger unitManger;

    public NewRepSetWindow(Presenter presenter, UnitManger unitManger){
        this.presenter = presenter;
        this.unitManger = unitManger;
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
        System.out.printf("Set weight (in %s).%n",unitManger.getUnits());
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
