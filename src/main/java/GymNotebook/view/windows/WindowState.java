package GymNotebook.view.windows;

import GymNotebook.presenter.commands.ICommand;
import java.util.List;

public interface WindowState {
    List<ICommand> HandleInput(String input) throws WindowException;
    void Render();
}
