package GymNotebook.view.windows;

import GymNotebook.presenter.commands.Command;
import java.util.List;

public interface WindowState {
    List<Command> HandleInput(String input);
    void Render();
}
