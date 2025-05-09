package GymNotebook.view.windows;

import GymNotebook.model.ParameterDescriptor;
import GymNotebook.model.SetService;
import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.commands.Command;
import static GymNotebook.model.ParameterDescriptor.InputType.*;
import java.util.ArrayList;
import java.util.List;

public class SetCreationWindow extends Window{
    private WindowState state;
    private SetService setService;
    private UnitManger unitManger;

    public SetCreationWindow(SetService setService, UnitManger unitManger){
        this.setService = setService;
        this.unitManger = unitManger;
    }

    private class ParameterSet implements WindowState{
        private List<ParameterDescriptor> parameters;
        private ParameterDescriptor currentParameter;
        private int index;

        public ParameterSet(){
            parameters = new ArrayList<>();
            parameters.addAll(setService.GetParameters());
            index = 0;
        }

        public List<Command> HandleInput(String input) throws WindowException{
            List<Command> commands = new ArrayList<>();

            try{
                switch (currentParameter.expectedInputType()){

                }
            }catch (NumberFormatException e) {
                throw new WindowException("ERR: Invalid input. Please enter a number");
            }

            return commands;
        }

        public void Render(){
            currentParameter = parameters.get(index);

            System.out.println(currentParameter.promptMessage());
        }
    }


    @Override
    public void SendBody(){
        state.Render();
    }

    @Override
    public List<Command> HandleInput(String input) throws WindowException{
        List<Command> commands = new ArrayList<>();

        commands.addAll(state.HandleInput(input));

        return commands;
    }
}
