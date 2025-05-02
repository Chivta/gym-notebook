package GymNotebook.presenter.commands;

import GymNotebook.model.Set;
import GymNotebook.presenter.Presenter;
import lombok.Setter;

public class AddSetToCurrentExercise implements Command{
    private Presenter presenter;

    private Set set;

    public AddSetToCurrentExercise(Set set){
        this.set = set;
    }


    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void Execute(){
        presenter.AddSetToCurrentExercise(set);
    }
}
