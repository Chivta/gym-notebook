package GymNotebook.view.windows;

public class WindowException extends Exception{
    String message;
    public WindowException(String errMessage){
        this.message = errMessage;
    }
}
