package GymNotebook.view.windows;

import java.util.ArrayList;

public class MainMenuWindow extends Window {

    ArrayList<String> options;
    int selected;

    public MainMenuWindow(){
        header = "Main Menu";
        footer = "Enter option number";
        options = new ArrayList<>();
        options.add("New Workout");
        options.add("View Workouts");
    }

    @Override
    public void Render() {
        System.out.printf("%s%n", header);
        for(int i = 0; i<options.size(); i++){
            if(selected == i){
                System.out.print(">");
            }
            else{
                System.out.print("-");
            }
            System.out.printf(" %d. %s%n",i,options.get(i));

        }

        System.out.printf("%s%n", footer);
    }

    public void HandleInput(String input){
        try{
            int selected = Integer.parseInt(input);

            if(selected>=options.size()){
                footer = "Number is too big";
                return;
            }

            this.selected = selected;
            footer="Enter option number";

        }
        catch(NumberFormatException e){
        }

    }

    public void HandleEnter(){
        switch (selected){
            case 0:
                break;
            case 1:
                break;
        }
    }





}
