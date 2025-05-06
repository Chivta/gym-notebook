package GymNotebook.view.windows;

import GymNotebook.presenter.commands.Command;

import java.util.ArrayList;
import java.util.List;

public abstract class OptionHandler {
    protected List<Command> TryHandleOptionIndex(String input, List<String> options) throws WindowException{
        List<Command> commands = new ArrayList<>();

        try {
            int optionNumber = Integer.parseInt(input);

            if (optionNumber>0 && optionNumber <= options.size()) {
                commands.addAll(HandleOptionIndex(optionNumber));
            } else {
                throw new WindowException("ERR: Invalid option number. Enter a number between 1 and " + options.size());
            }
        } catch (NumberFormatException e) {
            throw new WindowException("ERR: Invalid input. Please enter a number");
        }

        return commands;
    }

    protected List<Command> HandleOptionIndex(int index) throws WindowException {
        throw new WindowException("ERR: HandleOptionIndex not implemented in this window");
    };
}
