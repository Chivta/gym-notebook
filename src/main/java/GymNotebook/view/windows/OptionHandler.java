package GymNotebook.view.windows;

import GymNotebook.presenter.commands.ICommand;

import java.util.ArrayList;
import java.util.List;

public abstract class OptionHandler {
    protected List<ICommand> TryHandleOptionIndex(String input, List<String> options) throws WindowException{
        List<ICommand> ICommands = new ArrayList<>();

        try {
            int optionNumber = Integer.parseInt(input);

            if (optionNumber>0 && optionNumber <= options.size()) {
                ICommands.addAll(HandleOptionIndex(optionNumber));
            } else {
                throw new WindowException("ERR: Invalid option number. Enter a number between 1 and " + options.size());
            }
        } catch (NumberFormatException e) {
            throw new WindowException("ERR: Invalid input. Please enter a number");
        }

        return ICommands;
    }

    protected List<ICommand> HandleOptionIndex(int index) throws WindowException {
        throw new WindowException("ERR: HandleOptionIndex is not implemented in this window");
    };
}
