package GymNotebook.view.windows;

import java.util.ArrayList;

public class ExerciseViewWindow extends Window{

    ArrayList<String> options;
    int selected;

    public ExerciseViewWindow(){
        this.header = "Exercise View Window";
        this.footer = "Enter option number and/or press enter to select";
    }

    @Override
    public void Render(){

    }
}
