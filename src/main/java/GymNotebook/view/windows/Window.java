package GymNotebook.view.windows;

import java.util.ArrayList;
import java.util.List;

import GymNotebook.presenter.commands.Command;

public abstract class Window extends OptionHandler {
    protected String header = "UNSET TITLE";
    private String errorMessage = "";
    protected ArrayList<String> options;
    protected ArrayList<String> inputOptions;

    protected abstract void SendBody();
    protected abstract List<Command> HandleInput(String input) throws WindowException;

    public List<Command> AcceptInput(String input){
        List<Command> commands = new ArrayList<>();

        try{
            commands.addAll(HandleInput(input));
        } catch (WindowException e) {
            errorMessage = e.getMessage();
        }

        return commands;
    }

    protected Window(){
        options = new ArrayList<>();
        inputOptions = new ArrayList<>();
    }

    private void SendHeader(){
        if(!header.isEmpty()){
            System.out.printf("== %s ==%n",header);
        }
    }
    private void SendFooter(){
        System.out.println(String.join(", ",inputOptions));

    }
    private void SendInfo(){
        if(!errorMessage.isEmpty()){
            System.out.println(errorMessage);
        }
    }

    protected void SendOptions(){
        for(int i = 0; i< options.size(); i++){
            System.out.printf("%d. %s%n",i+1, options.get(i));
        }

        inputOptions.addFirst("[Number] - Choose option");

    }

    private void SendSeparator(){
        System.out.println("-------------------------");
    }



    public void Render(){
        inputOptions.clear();
        SendSeparator();
        SendHeader();

        SendBody();

        SendSeparator();
        AddBackOptionToFooter();
        SendFooter();
        SendInfo();
        SendSeparator();
        errorMessage = "";
    }

    protected void AddBackOptionToFooter(){
        inputOptions.add("[B] - Go back");
    }
}
