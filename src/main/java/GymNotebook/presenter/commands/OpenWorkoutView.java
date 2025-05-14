package GymNotebook.presenter.commands;

public class OpenWorkoutView extends Command {
    private final String fileName;

    public OpenWorkoutView(String filename){
        this.fileName = filename;
    }

    public void Execute(){
        presenter.OpenWorkoutView(fileName);
    }
}
