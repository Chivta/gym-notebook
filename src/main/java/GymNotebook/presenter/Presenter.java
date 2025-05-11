package GymNotebook.presenter;

import GymNotebook.model.*;
import GymNotebook.view.UIManager;
import GymNotebook.view.windows.*;
import GymNotebook.presenter.WorkoutFileHandler.Extension;

public class Presenter {
    private final UIManager ui;
    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;
    private final SetService setService;
    private final UnitManger unitManger;
    private final WorkoutFileHandler workoutFileHandler;
    private Extension SavingExtension;

    public Presenter(UIManager uiManager) {
        ui = uiManager;
        workoutService = new WorkoutService();
        exerciseService = new ExerciseService();
        setService = new SetService();
        workoutFileHandler = new WorkoutFileHandler();
        unitManger = new UnitManger();
        unitManger.Subscribe(workoutService);
        SavingExtension = workoutFileHandler.GetCurrentExtension();

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
        unitManger.setUnits(UnitManger.WeightUnits.kg);
        workoutService.StartNewWorkout(unitManger.getUnits());

        ui.ChangeWindow(new WorkoutCreationWindow(workoutService, workoutFileHandler));
    }

    public void OpenNewExercise(){
        exerciseService.StartNewExercise();
        ui.ChangeWindow(new ExerciseCreationWindow(exerciseService));
    }

    public void SaveExercise(Exercise exercise){
        //
    }

    public void ChangeUnitsForCurrentWorkout(){
        unitManger.ChangeUnits();
    }

    public void OpenNewSet(){
        setService.StartNewSet(exerciseService.GetType(),unitManger.getUnits());

        ui.ChangeWindow(new SetCreationWindow(setService));
    }

    public void SetTypeForExercise(ExerciseType type){
        exerciseService.SetType(type);
    }

    public void SetParameter(String key, Object value){
        setService.SetParameter(key,value);
    }

    public void AddSetToCurrentExercise(){
        exerciseService.AddSet(setService.BuildSet());
    }

    public void AddExerciseToCurrentWorkout(Exercise exercise){
        workoutService.AddExercise(exercise);
    }

    public void SwitchSavingFormat(){
        workoutFileHandler.SwitchExtension();
        SavingExtension = workoutFileHandler.GetCurrentExtension();
    }

    public void saveCurrentWorkout() {
        Workout currentWorkout = workoutService.BuildWorkout();
        if (currentWorkout == null) {
            System.err.println("Err: No current workout to save.");
            return;
        }

        // Отримуємо бажане розширення (можливо, з налаштувань UI або іншого місця)
        // Для прикладу припустимо, що за замовчуванням використовується JSON
        Extension saveExtension = Extension.JSON;
        // Якщо користувач обрав інший формат, встановлюємо його
        // workoutFileHandler.setExtension(Extension.XML);

        String savedFilename = workoutFileHandler.saveWorkout(currentWorkout);

        if (savedFilename != null) {
            System.out.println("Workout saved successfully as: " + savedFilename);
            ui.ChangeWindow(new MainMenuWindow());
            ui.ClearHistory();
        } else {
            System.err.println("Failed to save the workout.");
            // Можна додати додаткову обробку помилок для UI
        }
    }

    public void OpenWorkoutView(String filename){
        Workout workout = workoutFileHandler.loadWorkout(filename);
        ui.ChangeWindow(new WorkoutViewWindow(workout));
    }
}
