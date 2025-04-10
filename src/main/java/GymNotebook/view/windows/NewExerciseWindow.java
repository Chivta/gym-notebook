package GymNotebook.view.windows;

import GymNotebook.model.Exercise;
import GymNotebook.model.RepExercise;
import GymNotebook.presenter.Presenter;

import java.util.ArrayList;

public class NewExerciseWindow extends Window{
    private final ArrayList<String> exerciseTypes;
    private final Presenter presenter;
    private final Exercise exercise;
    private State state;

    enum State{
        TitleEnter,
        TypeSelection,
        RepExercise,
        TimeExercise
    }

    public NewExerciseWindow(Presenter presenter){
        this.presenter = presenter;
        exercise = new RepExercise();

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
    public void HandleInput(String input){
        switch(state){
            case TitleEnter:
                HandleTitleEnter(input);
                break;
            case TypeSelection:
                HandleTypeSelection(input);
                break;
            case RepExercise:
                HandleRepExercise(input);
                break;

        }
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

    private void HandleRepExercise(String input){
        try{
            int selected = Integer.parseInt(input);
            switch(selected){
                case 1:
                    presenter.SaveCurrentExercise(exercise);
                    presenter.OpenNewRepSet();
                    break;
                case 2:
                    presenter.AddExerciseToCurrentWorkout(exercise);
                    presenter.GoBack();
                    break;
                default:
                    info = "ERR: Number out of range.";
            }
        }catch (NumberFormatException e){
            info = "ERR: Invalid input.";
        }
    }

    private void HandleTimeExercise(String input){
        try{
            int selected = Integer.parseInt(input);
            switch(selected){
                case 1:
                    presenter.SaveCurrentExercise(exercise);
                    presenter.OpenNewTimeSet();
                    break;
                case 2:
                    presenter.AddExerciseToCurrentWorkout(exercise);
                    presenter.GoBack();
                    break;
                default:
                    info = "ERR: Number out of range.";
            }
        }catch (NumberFormatException e){
            info = "ERR: Invalid input.";
        }
    }



}
