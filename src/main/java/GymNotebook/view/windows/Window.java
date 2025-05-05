package GymNotebook.view.windows;

import java.util.ArrayList;
import java.util.List;

import GymNotebook.presenter.commands.Command;
import GymNotebook.presenter.commands.OpenWorkoutView;


public abstract class Window {
    protected String header = "UNSET TITLE";
    protected String info = "";
    protected ArrayList<String> options;
    protected ArrayList<String> inputOptions;

    protected abstract void SendBody();
    public abstract List<Command> HandleInput(String input);

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
        if(!info.isEmpty()){
            System.out.println(info);
        }
    }

    protected void SendOptions(){

        for(int i = 0; i< options.size(); i++){
            System.out.printf("%d. %s%n",i+1, options.get(i));
        }

        inputOptions.addFirst("[Number] - Choose option.");

    }

    private void SendSeparator(){
        System.out.println("-------------------------");
    }

    protected List<Command> HandleOptionIndex(String input){
        List<Command> commands = new ArrayList<>();

        try {
            int optionNumber = Integer.parseInt(input);

            if (optionNumber>0 && optionNumber <= options.size()) {
                commands.addAll(HandleOptionIndex(optionNumber));
            } else {
                info = "ERR: Invalid option number. Enter a number between 1 and " + options.size();
            }
        } catch (NumberFormatException e) {
            this.info = "ERR: Invalid input. Please enter a number.";
        }

        return commands;
    }

    protected List<Command> HandleOptionIndex(int index){
        return new ArrayList<Command>();
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
        info = "";
    }

    protected void AddBackOptionToFooter(){
        inputOptions.add("[B] - Go back");
    }
}
