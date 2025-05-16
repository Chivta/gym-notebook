package GymNotebook.presenter;

import GymNotebook.model.*;
import GymNotebook.service.*;
import GymNotebook.storage.WorkoutFileHandler;
import GymNotebook.view.UIManager;
import GymNotebook.view.windows.*;

public class Presenter {
    private final UIManager ui;
    private final UnitManager unitManager;
    private final WorkoutFileHandler workoutFileHandler;
    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;
    private final SuperSetService superSetService;
    private final SetService setService;


    public Presenter(UIManager uiManager) {
        workoutService = new WorkoutService();
        exerciseService = new ExerciseService();
        superSetService = new SuperSetService();
        setService = new SetService();

        ui = uiManager;

        unitManager = new UnitManager();
        workoutFileHandler = new WorkoutFileHandler();

    }

    public void GoBack(){
        ui.GoBack();
    }

    public void Quit(){
        System.exit(0);
    }

    public void OpenWorkoutListView(){
        WorkoutListService workoutListService = new WorkoutListService(workoutFileHandler.getAllWorkoutFilenamesSortedByDateDesc());
        ui.ChangeWindow(new WorkoutListViewWindow(workoutListService));
    }
    public void OpenWorkoutCreation(){
        unitManager.SetUnitsToDefault();
        unitManager.ClearSubscribers();
        unitManager.Subscribe(workoutService);

        workoutService.StartNew();

        workoutService.SetUnits(unitManager.getUnits());
        ui.ChangeWindow(new WorkoutCreationWindow(workoutService, workoutFileHandler));
    }

    public void OpenExerciseCreation(ExerciseCreationWindow.TargetComposite targetComposite){
        exerciseService.StartNew();
        ui.ChangeWindow(new ExerciseCreationWindow(exerciseService,targetComposite));
    }

    public void OpenNewSuperSet(){
        superSetService.StartNew();
        ui.ChangeWindow(new SuperSetCreationWindow(superSetService));
    }

    public void ChangeUnitsForCurrentWorkout(){
        unitManager.ChangeUnits();
    }

    public void OpenNewSet(){
        setService.StartNew();
        setService.SetUnits(unitManager.getUnits());
        setService.SetType(exerciseService.GetType());

        ui.ChangeWindow(new SetCreationWindow(setService));
    }


    public void SetTypeForExercise(ExerciseType type){
        exerciseService.SetType(type);
    }

    public void SetParameter(String key, Object value){
        setService.SetParameter(key,value);
    }


    public void AddExerciseToSuperSet() {
        Exercise exercise = (Exercise) exerciseService.Build();
        superSetService.AddItem(exercise);
    }

    public void AddSuperSetToWorkout() {
        SuperSet superSet = (SuperSet) superSetService.Build();
        workoutService.AddItem(superSet);
    }

    public void AddExerciseToWorkout() {
        Exercise exercise = (Exercise) exerciseService.Build();
        workoutService.AddItem(exercise);
    }

    public void AddSetToExercise() {
        Set set = (Set) setService.Build();
        exerciseService.AddItem(set);
    }

    public void SwitchSavingFormat(){
        workoutFileHandler.SwitchExtension();
    }

    public void saveCurrentWorkout() {
        Workout currentWorkout = (Workout) workoutService.Build();
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
