package GymNotebook.view.windows;

import GymNotebook.service.WorkoutService;
import GymNotebook.storage.WorkoutFileHandler;
import GymNotebook.presenter.commands.*;

import java.util.ArrayList;
import java.util.List;


public class WorkoutCreationWindow extends Window{
    private final WorkoutService workoutService;
    private final WorkoutFileHandler workoutFileHandler;


    private WindowState state;

    private class TitleInput implements WindowState{
        public List<ICommand> HandleInput(String title) throws WindowException {
            List<ICommand> ICommands = new ArrayList<>();
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

            return ICommands;
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

        public List<ICommand> HandleInput(String input) throws WindowException{
            return TryHandleOptionIndex(input, options);
        }
        private void SendWorkoutOverview(){
            System.out.println(workoutService.ObjectToString());
        }
    }

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
    public List<ICommand> HandleInput(String input) throws WindowException {
        List<ICommand> ICommands = new ArrayList<>();

        ICommands.addAll(state.HandleInput(input));

        return ICommands;
    }

    protected List<ICommand> HandleOptionIndex(int index){
        List<ICommand> ICommands = new ArrayList<>();

        switch (index) {
            case 1:
                ICommands.add(new OpenNewExercise(ExerciseCreationWindow.TargetComposite.Workout));
                break;
            case 2:
                ICommands.add(new OpenNewSuperSet());
                break;
            case 3:
                ICommands.add(new SaveCurrentWorkout());
                break;
            case 4:
                ICommands.add(new ChangeUnitsForCurrentWorkout());
                break;
            case 5:
                ICommands.add(new SwitchSavingFormat());
                break;
        }
        return ICommands;
    }

}
