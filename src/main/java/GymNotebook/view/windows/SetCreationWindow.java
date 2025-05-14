package GymNotebook.view.windows;

import GymNotebook.model.ParameterDescriptor;
import GymNotebook.presenter.commands.*;
import GymNotebook.service.SetService;

import java.util.ArrayList;
import java.util.List;

public class SetCreationWindow extends Window{
    private WindowState state;
    private SetService setService;

    public SetCreationWindow(SetService setService){
        this.setService = setService;
        this.header = "Set Creation";

        state = new ParameterSet();
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

        public List<ICommand> HandleInput(String input) throws WindowException{
            List<ICommand> ICommands = new ArrayList<>();
            Object value;
            try{
                switch (currentParameter.expectedInputType()){
                    case DOUBLE:
                        value = Double.parseDouble(input);
                        break;
                    case INTEGER:
                        value = Integer.parseInt(input);
                        break;
                    case STRING:
                        value = input;
                        break;
                    case BOOLEAN:
                        if(input.equalsIgnoreCase("y")){
                            value = true;
                        }else if(input.equalsIgnoreCase("n")){
                            value = false;
                        }else{
                            throw new WindowException("ERR: Invalid input. Please type y or n");
                        }
                        break;
                    default:
                        throw new WindowException("ERR: Unsupported input type");
                }
                
                ICommands.add(new SetParameter(currentParameter.parameterKey(),value));
                index++;

                if(index == parameters.size()){
                    ICommands.add(new AddSetToExercise());
                    ICommands.add(new GoBack());
                }

            }catch (NumberFormatException e) {
                throw new WindowException("ERR: Invalid input. Please enter a " + currentParameter.expectedInputType());
            }

            return ICommands;
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
    public List<ICommand> HandleInput(String input) throws WindowException{
        List<ICommand> ICommands = new ArrayList<>();

        ICommands.addAll(state.HandleInput(input));

        return ICommands;
    }
}
