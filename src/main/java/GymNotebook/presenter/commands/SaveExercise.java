package GymNotebook.presenter.commands;

import GymNotebook.service.ExerciseService;
import GymNotebook.presenter.Presenter;

public class SaveExercise implements Command{
    private Presenter presenter;
    private ExerciseService exerciseService;

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void SetExerciseService(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    public void Execute(){
        presenter.SaveExercise(exerciseService.Build());
    }
}
