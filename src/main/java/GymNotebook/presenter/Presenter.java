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
    private Stack<Service> serviceStack;

    public Presenter(UIManager uiManager) {
        ui = uiManager;
        serviceStack = new Stack<>();

        unitManager = new UnitManager();
        workoutFileHandler = new WorkoutFileHandler();

    }

    public void OpenWorkoutListView(){
        WorkoutListService workoutListService = new WorkoutListService(workoutFileHandler.getAllWorkoutFilenamesSortedByDateDesc());
        ui.ChangeWindow(new WorkoutListViewWindow(workoutListService));
    }

    public void GoBack(){
        ui.GoBack();
    }

    public void Quit(){
        System.exit(200);
    }

    public void OpenNewWorkoutCreation(){
        unitManager.setUnits(UnitManager.WeightUnits.kg);
        WorkoutService workoutService = new WorkoutService(unitManager.getUnits());
        unitManager.Subscribe(workoutService);

        serviceStack.add(workoutService);
        ui.ChangeWindow(new WorkoutCreationWindow(workoutService, workoutFileHandler));
    }

    public void OpenNewExercise(){
        ExerciseService exerciseService = new ExerciseService();
        serviceStack.add(exerciseService);
        ui.ChangeWindow(new ExerciseCreationWindow(exerciseService));
    }

    public void SaveExercise(Exercise exercise){
        //
    }

    public void OpenNewSuperSet(){
        SuperSetService sup = new SuperSetService();
        serviceStack.add(sup);
        ui.ChangeWindow(new SuperSetCreationWindow(sup));
    }

    public void ChangeUnitsForCurrentWorkout(){
        unitManager.ChangeUnits();
    }

    public void OpenNewSet(){
        ExerciseService exerciseService = (ExerciseService) serviceStack.peek();
        SetService setService = new SetService(exerciseService.GetType(), unitManager.getUnits());
        serviceStack.add(setService);

        ui.ChangeWindow(new SetCreationWindow(setService));
    }

    public void SetTypeForExercise(ExerciseType type){
        ((ExerciseService)serviceStack.peek()).SetType(type);
    }

    public void SetParameter(String key, Object value){
        SetService setService = (SetService) serviceStack.peek();
        setService.SetParameter(key,value);
    }

    public void AddItemToCurrentComposite(){
        Service currentService = serviceStack.pop();
        serviceStack.peek().addItem(currentService.Build());
    }

    public void AddSetToCurrentExercise(){
        AddItemToCurrentComposite();
    }

    public void AddExerciseToCurrentWorkout(){
        AddItemToCurrentComposite();
    }

    public void SwitchSavingFormat(){
        workoutFileHandler.SwitchExtension();
    }

    public void saveCurrentWorkout() {
        if(!(serviceStack.peek() instanceof WorkoutService)){
            return;
        }
        WorkoutService service = (WorkoutService) serviceStack.pop();
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
