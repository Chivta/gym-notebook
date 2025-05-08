package GymNotebook.presenter;

import GymNotebook.model.*;
import GymNotebook.view.UIManager;
import GymNotebook.view.windows.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Presenter {
    private final UIManager ui;
    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;

    public Presenter(UIManager uiManager) {
        ui = uiManager;
        workoutService = new WorkoutService();
        exerciseService = new ExerciseService();
    }

    public void OpenWorkoutListView(){
        WorkoutListService workoutListService = new WorkoutListService();
        ui.ChangeWindow(new WorkoutListViewWindow(workoutListService));
    }

    public void GoBack(){
        ui.GoBack();
    }

    public void Quit(){
        System.exit(200);
    }


    UnitManger unitManger;
    public void OpenNewWorkoutCreation(){
        workoutService.StartNewWorkout();
        unitManger = new UnitManger();

        unitManger.setUnits(UnitManger.WeightUnits.kg);

        ui.ChangeWindow(new WorkoutCreationWindow(workoutService));
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

    public void OpenNewRepSet(){
        ui.ChangeWindow(new RepSetCreationWindow(unitManger));
    }

    public void OpenNewTimeSet(){
        ui.ChangeWindow(new TimeSetCreationWindow(unitManger));
    }

    public void AddSetToCurrentExercise(Set set){
        unitManger.Subscribe(set);
        set.setUnits(unitManger.getUnits());

        exerciseService.AddSet(set);
    }

    public void AddExerciseToCurrentWorkout(Exercise exercise){
        workoutService.AddExercise(exercise);
    }

    public void saveCurrentWorkout() {
        Workout currentWorkout = workoutService.BuildWorkout();
        if (currentWorkout == null) {
            System.err.println("Err: No current workout to save.");
            return;
        }

        String title = currentWorkout.getTitle();
        String sanitizedTitle = (title == null || title.trim().isEmpty())
                ? "UntitledWorkout"
                : title.trim().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9\\-_]", "");
        if (sanitizedTitle.length() > 20) {
            sanitizedTitle = sanitizedTitle.substring(0, 20);
        }
        if (sanitizedTitle.isEmpty()) {
            sanitizedTitle = "Workout";
        }



        LocalDate today = LocalDate.now();
        String dateString = today.format(DateTimeFormatter.ISO_LOCAL_DATE);


        String baseFilename = dateString + "_" + sanitizedTitle;
        String filename = baseFilename + ".json";
        Path workoutDirPath = Paths.get("Workouts");

        int counter = 0;
        while (Files.exists(workoutDirPath.resolve(filename))) {
            counter++;
            filename = baseFilename + "(" + counter + ").json";
        }

        try {
            FileManager.saveWorkout(currentWorkout, filename);
            System.out.println("Workout saved successfully as: " + filename);

            currentWorkout = null;

            ui.ChangeWindow(new MainMenuWindow());
            ui.ClearHistory();

        } catch (IOException e) {
            System.err.println("Failed to save workout file '" + filename + "': " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during saving workout '" + filename + "': " + e.getMessage());
        }

    }

    public void OpenWorkoutView(String filename){
        Workout workout = FileManager.loadWorkoutByFileName(filename);
        ui.ChangeWindow(new WorkoutViewWindow(workout));
    }
}
