package GymNotebook.presenter;

import GymNotebook.model.Exercise;
import GymNotebook.model.Workout;
import GymNotebook.model.Set;
import GymNotebook.view.UIManager;
import GymNotebook.view.windows.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class Presenter {
    private final UIManager ui;

    public Presenter(UIManager uiManager) {
        ui = uiManager;
    }


    public void OpenWorkoutListView(){
        ui.ChangeWindow(new WorkoutListViewWindow(this));
    }

    public void GoBack(){
        ui.GoBack();
    }

    public void Quit(){
        System.exit(200);
    }

    public List<String> GetWorkoutFilenamesSorted() {
        return FileManager.getAllWorkoutFilenamesSortedByDateDesc();
    }

    UnitManger unitManger;
    Workout currentWorkout;
    public void OpenNewWorkoutCreation(){
        currentWorkout = new Workout();
        unitManger = new UnitManger();

        unitManger.setUnits(UnitManger.WeightUnits.kg);

        ui.ChangeWindow(new WorkoutCreationWindow(currentWorkout));
    }

    Exercise currentExercise;
    public void OpenNewExercise(){
        currentExercise = new Exercise();
        ui.ChangeWindow(new ExerciseCreationWindow(currentExercise));
    }

    public void SaveExercise(Exercise exercise){
        currentExercise = exercise;
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

        currentExercise.AddSet(set);
    }

    public void AddExerciseToCurrentWorkout(Exercise exercise){
        currentWorkout.AddExercise(exercise);
    }

    public void saveCurrentWorkout() {
        if (currentWorkout == null) {
            System.err.println("Err: No current workout to save.");
            return;
        }

        String title = currentWorkout.getTitle();
        String sanitizedTitle = (title == null || title.trim().isEmpty())
                ? "UntitledWorkout"
                : title.trim().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9\\-_]", "");
        if (sanitizedTitle.length() > 50) {
            sanitizedTitle = sanitizedTitle.substring(0, 50);
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
        ui.ChangeWindow(new WorkoutViewWindow(this,workout));
    }

    public void PrintWorkout(Workout workout){
        WorkoutPrinter.PrintWorkout(workout);
    }
}
