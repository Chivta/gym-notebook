package GymNotebook.presenter;

import GymNotebook.model.*;
import GymNotebook.view.UIManager;
import GymNotebook.view.windows.*;
import GymNotebook.presenter.WorkoutFileHandler.Extension;

import java.util.Stack;

public class Presenter {
    private final UIManager ui;
    private final UnitManger unitManger;
    private final WorkoutFileHandler workoutFileHandler;
    private Stack<Service> serviceStack;

    public Presenter(UIManager uiManager) {
        ui = uiManager;
        serviceStack = new Stack<>();

        unitManger = new UnitManger();
        WorkoutService workoutService = new WorkoutService(unitManger.getUnits());
        unitManger.Subscribe(workoutService);
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
        unitManger.setUnits(UnitManger.WeightUnits.kg);
        WorkoutService workoutService = new WorkoutService(unitManger.getUnits());
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
        ui.ChangeWindow(new SuperSetCreationWindow());
    }

    public void ChangeUnitsForCurrentWorkout(){
        unitManger.ChangeUnits();
    }

    public void OpenNewSet(){
        ExerciseService exerciseService = (ExerciseService) serviceStack.peek();
        SetService setService = new SetService(exerciseService.GetType(),unitManger.getUnits());
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
