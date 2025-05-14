package GymNotebook.presenter;

import GymNotebook.model.*;
import GymNotebook.service.*;
import GymNotebook.storage.WorkoutFileHandler;
import GymNotebook.view.UIManager;
import GymNotebook.view.windows.*;

import java.util.Stack;

public class Presenter {
    private final UIManager ui;
    private final UnitManager unitManager;
    private final WorkoutFileHandler workoutFileHandler;
    private Stack<BuildableItemService> itemServiceStack;

    public Presenter(UIManager uiManager) {
        ui = uiManager;
        itemServiceStack = new Stack<>();

        unitManager = new UnitManager();
        workoutFileHandler = new WorkoutFileHandler();

    }

    public void GoBack(){
        itemServiceStack.pop();
        ui.GoBack();
    }

    public void Quit(){
        System.exit(200);
    }

    public void OpenWorkoutListView(){
        WorkoutListService workoutListService = new WorkoutListService(workoutFileHandler.getAllWorkoutFilenamesSortedByDateDesc());
        ui.ChangeWindow(new WorkoutListViewWindow(workoutListService));
    }
    public void OpenNewWorkoutCreation(){
        unitManager.setUnits(UnitManager.WeightUnits.kg);
        WorkoutService workoutService = new WorkoutService(unitManager.getUnits());
        unitManager.Subscribe(workoutService);

        itemServiceStack.add(workoutService);
        ui.ChangeWindow(new WorkoutCreationWindow(workoutService, workoutFileHandler));
    }

    public void OpenNewExercise(){
        ExerciseService exerciseService = new ExerciseService();
        itemServiceStack.add(exerciseService);
        ui.ChangeWindow(new ExerciseCreationWindow(exerciseService));
    }

    public void OpenNewSuperSet(){
        SuperSetService sup = new SuperSetService();
        itemServiceStack.add(sup);
        ui.ChangeWindow(new SuperSetCreationWindow(sup));
    }

    public void ChangeUnitsForCurrentWorkout(){
        unitManager.ChangeUnits();
    }

    public void OpenNewSet(){
        BuildableItemService service = itemServiceStack.peek();
        if(service instanceof ExerciseService exerciseService){
            SetService setService = new SetService(exerciseService.GetType(), unitManager.getUnits());
            itemServiceStack.add(setService);

            ui.ChangeWindow(new SetCreationWindow(setService));
        }else if (service instanceof SetService){
            itemServiceStack.pop();
            OpenNewSet();
        }
    }


    public void SetTypeForExercise(ExerciseType type){
        ((ExerciseService) itemServiceStack.peek()).SetType(type);
    }

    public void SetParameter(String key, Object value){
        SetService setService = (SetService) itemServiceStack.peek();
        setService.SetParameter(key,value);
    }

    public void AddItemToCurrentComposite(){
         BuildableItemService currentService = itemServiceStack.pop();
         CompositeItemService nextService = (CompositeItemService) itemServiceStack.peek();
         nextService.AddItem(currentService.Build());
    }

    public void SwitchSavingFormat(){
        workoutFileHandler.SwitchExtension();
    }

    public void saveCurrentWorkout() {
        if(!(itemServiceStack.peek() instanceof WorkoutService)){
            return;
        }
        WorkoutService service = (WorkoutService) itemServiceStack.pop();
        Workout currentWorkout = (Workout) service.Build();
        if (currentWorkout == null) {
            System.err.println("Err: No current workout to save.");
            return;
        }

        String savedFilename = workoutFileHandler.saveWorkout(currentWorkout);

        if (savedFilename != null) {
            System.out.println("Workout saved successfully as: " + savedFilename);
            ui.ChangeWindow(new MainMenuWindow());
            ui.ClearHistory();
        } else {
            System.err.println("Failed to save the workout.");
        }
    }

    public void OpenWorkoutView(String filename){
        Workout workout = workoutFileHandler.loadWorkout(filename);
        ui.ChangeWindow(new WorkoutViewWindow(workout));
    }
}
