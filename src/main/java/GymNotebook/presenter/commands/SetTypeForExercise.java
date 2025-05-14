package GymNotebook.presenter.commands;

import GymNotebook.model.ExerciseType;

public class SetTypeForExercise extends Command {
    private ExerciseType type;

    public SetTypeForExercise(ExerciseType type){
        this.type = type;
    }

    public void Execute(){
        presenter.SetTypeForExercise(type);
    }
}
