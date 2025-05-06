package GymNotebook.view.windows;

import GymNotebook.model.Exercise;
import GymNotebook.presenter.commands.*;

import java.util.ArrayList;
import java.util.List;

public class ExerciseCreationWindow extends Window{
    private final ArrayList<String> exerciseTypes;
    private final Exercise exercise;
    private WindowState state;

    private class TitleInput implements WindowState{
        public List<Command> HandleInput(String input) throws WindowException{
            List<Command> commands = new ArrayList<>();

            if(!input.isEmpty() ){
                if(input.length()<20){
                    exercise.setTitle(input);
                    state = new TypeSelection();
                }else{
                    throw new WindowException("ERR: Title cannot be longer than 20 symbols");
                }
            }
            else{
                throw new WindowException("ERR: Title cannot be empty");
            }

            return commands;
        }
        public void Render(){
            System.out.println("Enter Exercise Title");
        }
    }

    private class TypeSelection implements WindowState{
        public List<Command> HandleInput(String input) throws WindowException{
            List<Command> commands = new ArrayList<>();

            try{
                int selected = Integer.parseInt(input);
                switch(selected){
                    case 1:
                        state = new RepExercise();
                        break;
                    case 2:
                        state = new TimeExercise();
                        break;
                    default:
                        throw new WindowException("ERR: Number out of range");
                }
            }catch (NumberFormatException e){
                throw new WindowException("ERR: Invalid input");
            }
            return commands;
        }
        public void Render(){
            for(int i = 0; i< exerciseTypes.size(); i++){
                System.out.printf("%d. %s%n",i+1, exerciseTypes.get(i));
            }
            inputOptions.clear();
            inputOptions.addFirst("[Number] - Choose option");
        }
    }

    private class RepExercise extends OptionHandler implements WindowState{

        protected List<Command> HandleOptionIndex(int index){
            List<Command> commands = new ArrayList<>();

            switch(index) {
                case 1:
                    SaveExercise saveExerciseCommand = new SaveExercise();
                    saveExerciseCommand.SetExercise(exercise);
                    commands.add(saveExerciseCommand);

                    commands.add(new OpenNewRepSet());
                    break;
                case 2:
                    AddExerciseToCurrentWorkout AddExerciseCommand = new AddExerciseToCurrentWorkout();
                    AddExerciseCommand.SetExercise(exercise);
                    commands.add(AddExerciseCommand);

                    commands.add(new GoBack());
                    break;
            }
            return commands;
        }

        public List<Command> HandleInput(String input) throws WindowException{
            return TryHandleOptionIndex(input,options);
        }
        public void Render(){
            SendExerciseOverview();
            SendOptions();
        }
    }

    private class TimeExercise extends OptionHandler implements WindowState{
        protected List<Command> HandleOptionIndex(int index){
            List<Command> commands = new ArrayList<>();

            switch(index) {
                case 1:
                    SaveExercise saveExerciseCommand = new SaveExercise();
                    saveExerciseCommand.SetExercise(exercise);
                    commands.add(saveExerciseCommand);

                    commands.add(new OpenNewTimeSet());
                    break;
                case 2:
                    AddExerciseToCurrentWorkout AddExerciseCommand = new AddExerciseToCurrentWorkout();
                    AddExerciseCommand.SetExercise(exercise);
                    commands.add(AddExerciseCommand);

                    commands.add(new GoBack());
                    break;
            }
            return commands;
        }

        public List<Command> HandleInput(String input) throws WindowException{
            return TryHandleOptionIndex(input,options);
        }
        public void Render(){
            SendExerciseOverview();
            SendOptions();
        }
    }


    public ExerciseCreationWindow(Exercise exercise){
        this.exercise = exercise;

        header = "New Exercise Adding";

        inputOptions.add("Enter exercise name");

        state = new TitleInput();

        exerciseTypes = new ArrayList<>();

        exerciseTypes.add("Rep Exercise");
        exerciseTypes.add("Time Exercise");

        options.add("Add Set");
        options.add("Save Exercise");
    }

    @Override
    public void SendBody(){
        state.Render();
    }

    private void SendExerciseOverview(){
        System.out.println(exercise.toString());
    }

    @Override
    public List<Command> HandleInput(String input){
        List<Command> commands = new ArrayList<>();

        try{
            commands.addAll(state.HandleInput(input));
        }catch (WindowException e){
            info = e.getMessage();
        }

        return commands;
    }
}
