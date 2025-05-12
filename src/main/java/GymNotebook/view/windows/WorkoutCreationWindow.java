package GymNotebook.view.windows;

import GymNotebook.model.WorkoutService;
import GymNotebook.presenter.WorkoutFileHandler;
import GymNotebook.presenter.commands.*;

import java.util.ArrayList;
import java.util.List;


public class WorkoutCreationWindow extends Window{
    private final WorkoutService workoutService;

    private WindowState state;

    private class TitleInput implements WindowState{
        public List<Command> HandleInput(String title) throws WindowException {
            List<Command> commands = new ArrayList<>();
            if(!title.isEmpty()){
                if (title.length()<20){
                    workoutService.SetTitle(title);
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
            System.out.println(workoutService.ObjectToString());
        }
    }

    private WorkoutFileHandler workoutFileHandler;

    public WorkoutCreationWindow(WorkoutService workoutService, WorkoutFileHandler workoutFileHandler) {
        header = "New Workout Creation";

        state = new TitleInput();
        this.workoutFileHandler = workoutFileHandler;
        this.workoutService = workoutService;
    }
    private void BuildOptions(){
        options.clear();
        options.add("Add Exercise");
        options.add("Add SuperSet Exercise");
        options.add("Save Workout");
        options.add(String.format("Change units (current: %s)",workoutService.getUnits()));
        options.add(String.format("Switch saving format (current: %s)",workoutFileHandler.GetCurrentExtension()));
    }

    @Override
    public void SendBody(){
        BuildOptions();
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
                commands.add(new OpenNewSuperSet());
                break;
            case 3:
                commands.add(new SaveCurrentWorkout());
                break;
            case 4:
                commands.add(new ChangeUnitsForCurrentWorkout());
                break;
            case 5:
                commands.add(new SwitchSavingFormat());
                break;
        }
        return commands;
    }

}
