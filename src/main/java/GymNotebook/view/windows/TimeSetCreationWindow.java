package GymNotebook.view.windows;

import GymNotebook.model.RepSet;
import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.commands.AddSetToCurrentExercise;
import GymNotebook.presenter.commands.Command;
import GymNotebook.presenter.commands.GoBack;

import java.util.ArrayList;
import java.util.List;

import static GymNotebook.presenter.UnitManger.RoundToDecimalPlaces;

public class TimeSetCreationWindow extends Window{
    State state;
    RepSet set;
    UnitManger unitManger;

    public TimeSetCreationWindow(UnitManger unitManger){
        this.unitManger = unitManger;

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
        System.out.printf("Set weight (in %s).%n",unitManger.getUnits());
        state = State.SettingWeight;
    }

    private void SendSettingRep(){
        System.out.printf("%s%n","Write time (in seconds).");
        state = State.SettingTime;
    }

    @Override
    public List<Command> HandleInput(String input){
        List<Command> commands = new ArrayList<>();

        switch (state){
            case SettingWeight:
                HandleSettingWeight(input);
                break;
            case SettingTime:
                commands.addAll(HandleSettingTime(input));
                break;
        }

        return commands;
    }

    private void HandleSettingWeight(String input){
        try{
            double weight = Double.parseDouble(input);
            weight = RoundToDecimalPlaces(weight,2);

            set.setWeight(weight);

            state = State.SettingTime;

        } catch (NumberFormatException e) {
            info = "ERR: Invalid input format";
        }
    }

    private List<Command> HandleSettingTime(String input){
        List<Command> commands = new ArrayList<>();

        try{
            int rep = Integer.parseInt(input);

            if(rep<1){
                info = "ERR: Time can't be lower then 1";
            }

            set.setRepCount(rep);

            commands.add(new AddSetToCurrentExercise(set));
            commands.add(new GoBack());

        } catch (NumberFormatException e) {
            info = "ERR: Invalid input format";
        }
        return commands;
    }

}
