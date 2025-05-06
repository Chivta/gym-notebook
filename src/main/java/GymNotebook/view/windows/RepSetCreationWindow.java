package GymNotebook.view.windows;

import GymNotebook.model.RepSet;
import GymNotebook.presenter.commands.AddSetToCurrentExercise;
import GymNotebook.presenter.commands.Command;
import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.commands.GoBack;

import java.util.ArrayList;
import java.util.List;

import static GymNotebook.presenter.UnitManger.RoundToDecimalPlaces;

public class RepSetCreationWindow extends Window{
    WindowState state;
    RepSet set;
    UnitManger unitManger;

    public RepSetCreationWindow(UnitManger unitManger){
        this.unitManger = unitManger;
        header = "Set adding";

        set = new RepSet();

        state = new SettingWeight();
    }

    private class SettingWeight implements WindowState{
        public void Render(){
            System.out.printf("Set weight (in %s).%n",unitManger.getUnits());
        }
        public List<Command> HandleInput(String input) throws WindowException{
            List<Command> commands = new ArrayList<>();

            try{
                double weight = Double.parseDouble(input);

                if(weight < 0){
                    throw new WindowException("ERR: Weight can't be lower then 0");
                }

                weight = RoundToDecimalPlaces(weight,2);

                set.setWeight(weight);

                state = new SettingRep();

            } catch (NumberFormatException e) {
                throw new WindowException("ERR: Invalid input format");
            }

            return commands;
        }
    }
    private class SettingRep implements WindowState{
        public void Render(){
            System.out.printf("%s%n","Set amount of reps.");
        }
        public List<Command> HandleInput(String input) throws WindowException{
            List<Command> commands = new ArrayList<>();

            try{
                int rep = Integer.parseInt(input);

                if(rep<1){
                    throw new WindowException("ERR: Reps can't be lower then 1");
                }
                set.setRepCount(rep);

                commands.add(new AddSetToCurrentExercise(set));
                commands.add(new GoBack());

            } catch (NumberFormatException e) {
                throw new WindowException("ERR: Invalid input format");
            }

            return commands;
        }
    }

    @Override
    public void SendBody(){
        state.Render();
    }

    @Override
    public List<Command> HandleInput(String input) throws WindowException{
        List<Command> commands = new ArrayList<>();

        commands.addAll(state.HandleInput(input));

        return commands;
    }
}
