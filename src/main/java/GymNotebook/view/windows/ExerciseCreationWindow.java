package GymNotebook.view.windows;

import GymNotebook.service.ExerciseService;
import GymNotebook.model.ExerciseType;
import GymNotebook.presenter.commands.*;

import java.util.ArrayList;
import java.util.List;

public class ExerciseCreationWindow extends Window{
    private final ArrayList<String> exerciseTypes;
    private final ExerciseService exerciseService;
    private WindowState state;
    private final TargetComposite targetComposite;

    public enum TargetComposite{
        SuperSet,
        Workout
    }


    public ExerciseCreationWindow(ExerciseService exerciseService, TargetComposite targetComposite){
        this.exerciseService = exerciseService;
        this.targetComposite = targetComposite;

        header = "New Exercise Adding";

        inputOptions.add("Enter exercise name");

        state = new TitleInput();

        exerciseTypes = new ArrayList<>();

        exerciseTypes.add("Rep Exercise");
        exerciseTypes.add("Time Exercise");

        options.add("Add Set");
        options.add("Save Exercise");
    }

    private class TitleInput implements WindowState{
        public List<ICommand> HandleInput(String input) throws WindowException{
            List<ICommand> ICommands = new ArrayList<>();

            if(!input.isEmpty() ){
                if(input.length()<20){
                    exerciseService.SetTitle(input);
                    state = new TypeSelection();
                }else{
                    throw new WindowException("ERR: Title cannot be longer than 20 symbols");
                }
            }
            else{
                throw new WindowException("ERR: Title cannot be empty");
            }

            return ICommands;
        }
        public void Render(){
            System.out.println("Enter Exercise Title");
        }
    }

    private class TypeSelection implements WindowState{
        public List<ICommand> HandleInput(String input) throws WindowException{
            List<ICommand> ICommands = new ArrayList<>();

            try{
                int selected = Integer.parseInt(input);
                switch(selected){
                    case 1:
                        ICommands.add(new SetTypeForExercise(ExerciseType.Rep));
                        break;
                    case 2:
                        ICommands.add(new SetTypeForExercise(ExerciseType.Time));
                        break;
                    default:
                        throw new WindowException("ERR: Number out of range");
                }
                state = new OptionSelection();
            }catch (NumberFormatException e){
                throw new WindowException("ERR: Invalid input");
            }
            return ICommands;
        }
        public void Render(){
            for(int i = 0; i< exerciseTypes.size(); i++){
                System.out.printf("%d. %s%n",i+1, exerciseTypes.get(i));
            }
            inputOptions.clear();
            inputOptions.addFirst("[Number] - Choose option");
        }
    }


    private class OptionSelection extends OptionHandler implements WindowState{
        protected List<ICommand> HandleOptionIndex(int index){
            List<ICommand> ICommands = new ArrayList<>();

            switch(index) {
                case 1:
                    ICommands.add(new OpenNewSet());
                    break;
                case 2:
                    if(targetComposite == TargetComposite.SuperSet){
                        ICommands.add(new AddExerciseToSuperSet());
                    }else{
                        ICommands.add(new AddExerciseToWorkout());
                    }
                    ICommands.add(new GoBack());
                    break;
            }
            return ICommands;
        }

        public List<ICommand> HandleInput(String input) throws WindowException{
            return TryHandleOptionIndex(input,options);
        }
        public void Render(){
            SendExerciseOverview();
            SendOptions();
        }
    }

    @Override
    public void SendBody(){
        state.Render();
    }

    private void SendExerciseOverview(){
        System.out.println(exerciseService.ObjectToString());
    }

    @Override
    public List<ICommand> HandleInput(String input) throws WindowException{
        List<ICommand> ICommands = new ArrayList<>();

        ICommands.addAll(state.HandleInput(input));

        return ICommands;
    }
}
