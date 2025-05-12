package GymNotebook.storage;

import GymNotebook.model.Workout;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    private static class WorkoutFileInfo {
        String filename;
        LocalDate date;

        WorkoutFileInfo(String filename, LocalDate date) {
            this.filename = filename;
            this.date = date;
        }
        LocalDate getDate() { return date == null ? LocalDate.MIN : date; }
        String getFilename() { return filename; }
    }

    private WorkoutStorageStrategy storageStrategy;
    private Extension currentExtension = Extension.JSON; // За замовчуванням JSON
    private static final String WORKOUTS_DIR_NAME = "Workouts";
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private void createWorkoutDirectoryIfNotExists() {
        try {
            Files.createDirectories(Paths.get(WORKOUTS_DIR_NAME));
        } catch (IOException e) {
            System.err.println("Failed to create directory '" + WORKOUTS_DIR_NAME + "': " + e.getMessage());
        }
    }


    public Extension GetCurrentExtension(){
        return currentExtension;
    }

    public WorkoutFileHandler() {
        createWorkoutDirectoryIfNotExists();
        setStorageStrategy(currentExtension);
    }

    public void SwitchExtension() {
        switch(currentExtension){
            case XML -> currentExtension = Extension.JSON;
            case JSON -> currentExtension = Extension.XML;
        }
        setStorageStrategy(currentExtension);
    }

    private void setStorageStrategy(Extension extension) {
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

        WorkoutStorageStrategy loadingStrategy; // Локальна стратегія для завантаження
        switch (extension) {
            case JSON:
                loadingStrategy = new JsonWorkoutStorage();
                break;
            case XML:
                loadingStrategy = new XmlWorkoutStorage();
                break;
            default:
                System.err.println("Unsupported file extension for loading: " + extension);
                return null;
        }

        try {
            return loadingStrategy.loadWorkout(filename); // Використовуємо локальну стратегію
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

    public List<String> getAllWorkoutFilenamesSortedByDateDesc() {
        Path workoutsDirPath = Paths.get(WORKOUTS_DIR_NAME);

        if (!Files.exists(workoutsDirPath) || !Files.isDirectory(workoutsDirPath)) {
            return new ArrayList<>();
        }

        try (Stream<Path> paths = Files.list(workoutsDirPath)) {
            List<WorkoutFileInfo> fileInfos = paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(filename -> {
                        Matcher matcher = DATE_PATTERN.matcher(filename);
                        LocalDate date = null;
                        if (matcher.find()) {
                            try {
                                date = LocalDate.parse(matcher.group(1), DATE_FORMATTER);
                            } catch (DateTimeParseException e) { /* Ігноруємо помилку парсингу дати */ }
                        }
                        return new WorkoutFileInfo(filename, date);
                    })
                    .collect(Collectors.toList());

            fileInfos.sort(Comparator.comparing(WorkoutFileInfo::getDate).reversed());

            return fileInfos.stream()
                    .map(WorkoutFileInfo::getFilename)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.err.println("Error reading directory '" + workoutsDirPath + "': " + e.getMessage());
            return new ArrayList<>();
        }
    }
}