package GymNotebook;

import GymNotebook.view.UIManager;

public class Main {
    public static void main(String[] args) {
        UIManager uiManager = new UIManager();

        try{
            uiManager.Start();

        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
