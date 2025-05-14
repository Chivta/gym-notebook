package GymNotebook.presenter.commands;

import GymNotebook.presenter.Presenter;
import GymNotebook.view.windows.ExerciseCreationWindow;

public class OpenNewExercise implements ICommand {
    private Presenter presenter;
    private ExerciseCreationWindow.TargetComposite targetComposite;

    public OpenNewExercise(ExerciseCreationWindow.TargetComposite targetComposite){
        this.targetComposite = targetComposite;
    }

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void Execute(){
        presenter.OpenExerciseCreation(targetComposite);
    }
}
