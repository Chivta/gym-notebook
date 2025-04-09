package GymNotebook.view.windows;

import GymNotebook.model.Exercise;
import GymNotebook.model.RepExercise;
import GymNotebook.presenter.Presenter;

import java.util.ArrayList;

public class NewExerciseWindow extends Window{
    private ArrayList<String> exerciseOptions;
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
        footer = "Enter Exercise Name";

        state = State.TypeSelection;

        exerciseOptions = new ArrayList<>();

        exerciseOptions.add("RepExercise");
        exerciseOptions.add("TimeExercise");
    }

    @Override
    public void SendBody(){
        footer = "";
        switch(state){
            case TitleEnter:
                SendTitleEnter();
                break;
            case TypeSelection:
                SendTypeSelection();
                break;
            case RepExercise:
                break;
            case TimeExercise:
                break;

        }
    }

    private void SendTitleEnter(){
        System.out.println("Enter Exercise Title");
    }

    private void SendTypeSelection(){
        for(int i = 0; i<exerciseOptions.size(); i++){
            System.out.printf("%d. %s",i+1,exerciseOptions.get(i));
        }

        footer = "Select option.";
    }

    private void SendRepExercise(){

    }
    private void SendTimeExercise(){

    }

    @Override
    public void HandleInput(String input){
        input = input.toLowerCase();
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
        exercise.setTitle(input);
        state = State.TypeSelection;
    }

    private void HandleTypeSelection(String input){
        info = "";

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

    private void HandleRepExercise(String input){}

}
