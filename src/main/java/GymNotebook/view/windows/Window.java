package GymNotebook.view.windows;

import lombok.Getter;

@Getter
public abstract class Window {
    protected String header = "";
    protected String footer = "";
    protected String info = "";
    public void HandleInput(String input){}

    private void SendHeader(){
        if(!header.isEmpty()){
            System.out.println(header);
        }
    }
    private void SendFooter(){
        if(!footer.isEmpty()){
            System.out.println(footer);
        }
    }
    private void SendInfo(){
        if(!info.isEmpty()){
            System.out.println(info);
        }
    }
    private void SendSeparator(){
        System.out.println("---------------");
    }

    protected abstract void SendBody();

    public void Render(){
        SendSeparator();
        SendHeader();
        SendBody();
        SendFooter();
        SendInfo();
        SendSeparator();
    }

    protected void AddBackOptionToFooter(){
        footer += ", [B] - Back";
    }
}
