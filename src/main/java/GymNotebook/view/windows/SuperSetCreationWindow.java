package GymNotebook.view.windows;

import GymNotebook.service.SuperSetService;
import GymNotebook.presenter.commands.*;

import java.util.ArrayList;
import java.util.List;

public class SuperSetCreationWindow extends Window{
    private final SuperSetService sup;
    private WindowState state;

    public SuperSetCreationWindow(SuperSetService superSetService){
        header = "SuperSet Creation";
        sup = superSetService;
        state = new OptionSelection();
        options.add("Add Exercise");
        options.add("Save SuperSet");
    }


    private class OptionSelection extends OptionHandler implements WindowState {
        protected List<Command> HandleOptionIndex(int index) {
            List<Command> commands = new ArrayList<>();

            switch (index) {
                case 1:
                    commands.add(new OpenNewExercise(ExerciseCreationWindow.TargetComposite.SuperSet));
                    break;
                case 2:
                    commands.add(new AddSuperSetToWorkout());
                    commands.add(new GoBack());
                    break;
            }
            return commands;
        }
        public List<Command> HandleInput(String input) throws WindowException{
            return TryHandleOptionIndex(input,options);
        }
        public void Render(){
            SendSuperSetOverview();
            SendOptions();
        }
    }

    private void SendSuperSetOverview(){
        System.out.println(sup.ObjectToString());
    }

    public void SendBody() {
        state.Render();
    }
    public List<Command> HandleInput(String input) throws WindowException{
        List<Command> commands = new ArrayList<>();

        commands.addAll(state.HandleInput(input));

        return commands;
    }
}
