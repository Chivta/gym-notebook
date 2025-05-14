package GymNotebook.presenter.commands;

import GymNotebook.view.windows.ExerciseCreationWindow;

public class OpenNewExercise extends Command {
    private ExerciseCreationWindow.TargetComposite targetComposite;

    public OpenNewExercise(ExerciseCreationWindow.TargetComposite targetComposite){
        this.targetComposite = targetComposite;
    }

    public void Execute(){
        presenter.OpenExerciseCreation(targetComposite);
    }
}
