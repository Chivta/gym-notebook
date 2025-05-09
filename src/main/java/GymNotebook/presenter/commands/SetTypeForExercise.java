package GymNotebook.presenter.commands;

import GymNotebook.model.ExerciseType;
import GymNotebook.presenter.Presenter;

public class SetTypeForExercise implements Command{
    private Presenter presenter;
    private ExerciseType type;

    public SetTypeForExercise(ExerciseType type){
        this.type = type;
    }

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void Execute(){
        presenter.SetTypeForExercise(type);
    }
}
