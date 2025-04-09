package GymNotebook.view.windows;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class Window {
    protected String header = "";
    protected String footer = "";
    protected String info = "";
    public void HandleInput(String input){}

    private void SendHeader(){
        System.out.println(header);
    }
    private void SendFooter(){
        System.out.println(footer);
    }
    private void SendInfo(){
        System.out.println(info);
    }

    protected abstract void SendBody();

    public void Render(){
        SendHeader();
        SendBody();
        SendFooter();
        SendInfo();
    }

    protected void AddBackOptionToFooter(){
        footer += ", [B] - Back";
    }
}
