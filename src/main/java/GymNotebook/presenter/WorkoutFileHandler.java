package GymNotebook.presenter;

import GymNotebook.model.Workout;
import GymNotebook.presenter.JsonWorkoutStorage;
import GymNotebook.presenter.WorkoutStorageStrategy;
import GymNotebook.presenter.XmlWorkoutStorage;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WorkoutFileHandler {

    @Getter
    public enum Extension {
        JSON(".json"),
        XML(".xml");

        private final String extension;

        Extension(String extension) {
            this.extension = extension;
        }

    }

    private WorkoutStorageStrategy storageStrategy;
    private Extension currentExtension = Extension.JSON; // За замовчуванням JSON
    private static final String WORKOUTS_DIR_NAME = "Workouts";

    public WorkoutFileHandler() {
        setStorageStrategy(currentExtension);
    }

    public void setExtension(Extension extension) {
        if (this.currentExtension != extension) {
            this.currentExtension = extension;
            setStorageStrategy(extension);
        }
    }

    private void    setStorageStrategy(Extension extension) {
        switch (extension) {
            case JSON:
                this.storageStrategy = new JsonWorkoutStorage();
                break;
            case XML:
                this.storageStrategy = new XmlWorkoutStorage();
                break;
            default:
                throw new IllegalArgumentException("Unsupported file extension: " + extension);
        }
    }

    public String saveWorkout(Workout workout) {
        if (workout == null) {
            System.err.println("Err: No workout to save provided.");
            return null;
        }

        String filename = generateUniqueFilename(workout.getTitle(), currentExtension.getExtension());
        try {
            storageStrategy.saveWorkout(workout, filename);
            return filename;
        } catch (IOException e) {
            System.err.println("Failed to save workout file '" + filename + "': " + e.getMessage());
            return null;
        }
    }

    public Workout loadWorkout(String filename) {
        Extension extension = getExtensionFromFilename(filename);
        if (extension == null) {
            System.err.println("Error: Could not determine file extension for loading: " + filename);
            return null;
        }
        setStorageStrategy(extension); // Встановлюємо стратегію на основі розширення файлу
        try {
            return storageStrategy.loadWorkout(filename);
        } catch (IOException e) {
            System.err.println("Failed to load workout file '" + filename + "': " + e.getMessage());
            return null;
        }
    }

    private Extension getExtensionFromFilename(String filename) {
        if (filename.toLowerCase().endsWith(Extension.JSON.getExtension())) {
            return Extension.JSON;
        } else if (filename.toLowerCase().endsWith(Extension.XML.getExtension())) {
            return Extension.XML;
        }
        return null;
    }

    private String generateUniqueFilename(String title, String extension) {
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
        String filename = baseFilename + extension;
        Path workoutDirPath = Paths.get(WORKOUTS_DIR_NAME);
        int counter = 0;
        while (Files.exists(workoutDirPath.resolve(filename))) {
            counter++;
            filename = baseFilename + "(" + counter + ")" + extension;
        }
        return filename;
    }
}