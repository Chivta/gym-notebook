package GymNotebook.view.windows;

import GymNotebook.model.Workout;
import GymNotebook.presenter.WorkoutPrinter;
import GymNotebook.presenter.commands.ChangeUnitsForCurrentWorkout;
import GymNotebook.presenter.commands.Command;
import GymNotebook.presenter.commands.OpenNewExercise;
import GymNotebook.presenter.commands.SaveCurrentWorkout;

import java.util.ArrayList;
import java.util.List;


public class WorkoutCreationWindow extends Window{
    private final Workout workout;

    private WindowState state;

    private class TitleInput implements WindowState{
        public List<Command> HandleInput(String title) throws WindowException {
            List<Command> commands = new ArrayList<>();
            if(!title.isEmpty()){
                if (title.length()<20){
                    workout.setTitle(title);
                    state = new OptionSelection();
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
            System.out.println("Enter Workout Title");
        }
    }

    private class OptionSelection implements WindowState{
        public void Render(){
            SendWorkoutOverview();
            SendOptions();
        }

        public List<Command> HandleInput(String input) throws WindowException{
            return TryHandleOptionIndex(input, options);
        }
        private void SendWorkoutOverview(){
            WorkoutPrinter.PrintWorkout(workout);
        }
    }

    public WorkoutCreationWindow(Workout workout) {
        header = "New Workout Creation";

        state = new TitleInput();

        this.workout = workout;

        options.add("Add Exercise");
        options.add("Save Workout");
        options.add("Change units");
    }

    @Override
    public void SendBody(){
        state.Render();
    }

    @Override
    public List<Command> HandleInput(String input) throws WindowException {
        List<Command> commands = new ArrayList<>();

        commands.addAll(state.HandleInput(input));

        return commands;
    }

    protected List<Command> HandleOptionIndex(int index){
        List<Command> commands = new ArrayList<>();

        switch (index) {
            case 1:
                commands.add(new OpenNewExercise());
                break;
            case 2:
                commands.add(new SaveCurrentWorkout());
                break;
            case 3:
                commands.add(new ChangeUnitsForCurrentWorkout());
                break;
        }
        return commands;
    }

}
