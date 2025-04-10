package GymNotebook.view.windows;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class Window {
    protected String header = "";
    protected String info = "";
    protected ArrayList<String> options;
    protected ArrayList<String> inputOptions;
    public void HandleInput(String input){}

    protected Window(){
        options = new ArrayList<>();
        inputOptions = new ArrayList<>();
    }

    private void SendHeader(){
        if(!header.isEmpty()){
            System.out.println(header);
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
        System.out.println("---------------");
    }

    protected abstract void SendBody();

    public void Render(){
        inputOptions.clear();
        SendSeparator();
        SendHeader();
        SendBody();
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
