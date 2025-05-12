package GymNotebook.view.windows;

import GymNotebook.presenter.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class SuperSetCreationWindow extends Window{

    public SuperSetCreationWindow(){
        header = "SuperSet Creation";


    }

    @Override
    protected void SendBody() {
        System.out.println("SuperSetCreationWindow");
    }
    protected List<Command> HandleInput(String input){
        List<Command> commands = new ArrayList<>();



        return commands;
    }
}
