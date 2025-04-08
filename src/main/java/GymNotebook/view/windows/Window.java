package GymNotebook.view.windows;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class Window {
    protected String header;
    protected String footer;
    public abstract void Render();
    public void HandleEnter(){}
    public void HandleInput(String input){}

    Map<String,String> colors = Map.of(
            "black","\u001B[30m",
            "white", "\u001B[37m",
            "reset","\u001B[0m"
    );

    protected void WriteInColor(String foregroundColor, String backgroundColor, String text){
        System.out.printf(
                colors.get(foregroundColor) +
                colors.get(backgroundColor) +
                "%s" +
                colors.get("reset"),text);
    }
    protected void Test(){
        System.out.printf("\\x1B[48;2;255;255;255m  123");
    }
}
