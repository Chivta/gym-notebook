package GymNotebook.presenter.commands;

public class SaveCurrentWorkout extends Command {
    public void Execute(){
        presenter.saveCurrentWorkout();
    }
}
