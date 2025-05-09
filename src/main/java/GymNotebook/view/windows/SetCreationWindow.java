package GymNotebook.view.windows;

import GymNotebook.model.ParameterDescriptor;
import GymNotebook.model.SetService;
import GymNotebook.presenter.UnitManger;
import GymNotebook.presenter.commands.AddSetToCurrentExercise;
import GymNotebook.presenter.commands.Command;
import GymNotebook.presenter.commands.GoBack;
import GymNotebook.presenter.commands.SetParameter;

import static GymNotebook.model.ParameterDescriptor.InputType.*;
import java.util.ArrayList;
import java.util.List;

public class SetCreationWindow extends Window{
    private WindowState state;
    private SetService setService;

    public SetCreationWindow(SetService setService){
        this.setService = setService;

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

        public List<Command> HandleInput(String input) throws WindowException{
            List<Command> commands = new ArrayList<>();
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
                
                commands.add(new SetParameter(currentParameter.parameterKey(),value));
                index++;

                if(index == parameters.size()){
                    commands.add(new AddSetToCurrentExercise());
                    commands.add(new GoBack());
                }

            }catch (NumberFormatException e) {
                throw new WindowException("ERR: Invalid input. Please enter a " + currentParameter.expectedInputType());
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
