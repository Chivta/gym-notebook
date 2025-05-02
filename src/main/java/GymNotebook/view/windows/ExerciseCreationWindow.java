package GymNotebook.view.windows;

import GymNotebook.model.Exercise;
import GymNotebook.presenter.commands.*;
import GymNotebook.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class ExerciseCreationWindow extends Window{
    private final ArrayList<String> exerciseTypes;
    private final Exercise exercise;
    private State state;

    enum State{
        TitleEnter,
        TypeSelection,
        RepExercise,
        TimeExercise
    }

    public ExerciseCreationWindow(Exercise exercise){
        this.exercise = exercise;

        header = "New Exercise Adding";

        inputOptions.add("Enter exercise name");

        state = State.TitleEnter;

        exerciseTypes = new ArrayList<>();

        exerciseTypes.add("Rep Exercise");
        exerciseTypes.add("Time Exercise");

        options.add("Add Set");
        options.add("Save Exercise");
    }

    @Override
    public void SendBody(){
        switch(state){
            case TitleEnter:
                SendTitleEnter();
                break;
            case TypeSelection:
                SendTypeSelection();
                break;
            case RepExercise:
            case TimeExercise:
                SendExerciseOverview();
                SendOptions();
                break;

        }
    }

    private void SendTitleEnter(){
        System.out.println("Enter Exercise Title");
    }

    private void SendExerciseOverview(){
        System.out.println(exercise.toString());
    }

    private void SendTypeSelection(){
        for(int i = 0; i< exerciseTypes.size(); i++){
            System.out.printf("%d. %s%n",i+1, exerciseTypes.get(i));
        }
        inputOptions.clear();
        inputOptions.add("[Number] - Choose option");
    }

    @Override
    public List<Command> HandleInput(String input){
        List<Command> commands = new ArrayList<>();

        switch(state){
            case TitleEnter:
                HandleTitleEnter(input);
                break;
            case TypeSelection:
                HandleTypeSelection(input);
                break;
            case RepExercise:
                commands.addAll(HandleRepExercise(input));
                break;
            case TimeExercise:
                commands.addAll(HandleTimeExercise(input));
                break;

        }

        return commands;
    }
    private void HandleTitleEnter(String input){
        if(!input.isEmpty()){
            exercise.setTitle(input);
            state = State.TypeSelection;
        }
        else{
            info = "ERR: Title cannot be empty";
        }
    }

    private void HandleTypeSelection(String input){
        try{
            int selected = Integer.parseInt(input);
            switch(selected){
                case 1:
                    state = State.RepExercise;
                    break;
                case 2:
                    state = State.TimeExercise;
                    break;
                default:
                    info = "ERR: Number out of range.";
            }
        }catch (NumberFormatException e){
            info = "ERR: Invalid input.";
        }
    }

    private List<Command> HandleRepExercise(String input){
        List<Command> commands = new ArrayList<>();

        try{
            int selected = Integer.parseInt(input);
            switch(selected){
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
                default:
                    info = "ERR: Number out of range.";
            }
        }catch (NumberFormatException e){
            info = "ERR: Invalid input.";
        }

        return commands;
    }

    private List<Command> HandleTimeExercise(String input){
        List<Command> commands = new ArrayList<>();

        try{
            int selected = Integer.parseInt(input);
            switch(selected){
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
                default:
                    info = "ERR: Number out of range.";
            }
        }catch (NumberFormatException e){
            info = "ERR: Invalid input.";
        }

        return commands;
    }
}
