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
        protected List<ICommand> HandleOptionIndex(int index) {
            List<ICommand> ICommands = new ArrayList<>();

            switch (index) {
                case 1:
                    ICommands.add(new OpenNewExercise(ExerciseCreationWindow.TargetComposite.SuperSet));
                    break;
                case 2:
                    ICommands.add(new AddSuperSetToWorkout());
                    ICommands.add(new GoBack());
                    break;
            }
            return ICommands;
        }
        public List<ICommand> HandleInput(String input) throws WindowException{
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
    public List<ICommand> HandleInput(String input) throws WindowException{
        List<ICommand> ICommands = new ArrayList<>();

        ICommands.addAll(state.HandleInput(input));

        return ICommands;
    }
}
