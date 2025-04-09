// File: FileManager.java
package GymNotebook.presenter; // Або ваш пакет

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import GymNotebook.model.Workout; // Імпортуємо Workout
import GymNotebook.model.Exercise; // Потрібен для Workout

import java.io.File;
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

public class FileManager {

    // Змінено на Workouts
    private static final String WORKOUTS_DIR_NAME = "Workouts";
    private static final ObjectMapper objectMapper = createObjectMapper();
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Переконайтесь, що ObjectMapper знає про поліморфізм Exercise (анотації в Exercise)
        // Якщо використовуєте Java 8 Date/Time:
        // mapper.registerModule(new JavaTimeModule());
        // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    // Допоміжний клас для сортування (змінено ім'я)
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

    /**
     * Повертає список імен файлів тренувань, відсортованих за датою (з імені файлу) DESC.
     * Приймає базовий каталог для можливості тестування.
     * @param baseDir Базовий каталог, де знаходиться підкаталог WORKOUTS_DIR_NAME.
     * @return Відсортований список імен файлів або порожній список.
     */
    public static List<String> getAllWorkoutFilenamesSortedByDateDesc(Path baseDir) {
        Path workoutsDirPath = baseDir.resolve(WORKOUTS_DIR_NAME);

        if (!Files.exists(workoutsDirPath) || !Files.isDirectory(workoutsDirPath)) {
            // System.err.println("Warning: Directory '" + workoutsDirPath + "' not found.");
            return new ArrayList<>();
        }

        try (Stream<Path> paths = Files.list(workoutsDirPath)) {
            List<WorkoutFileInfo> fileInfos = paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.toLowerCase().endsWith(".json"))
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

    /**
     * Перевантажений метод для зручності, використовує поточний робочий каталог.
     */
    public static List<String> getAllWorkoutFilenamesSortedByDateDesc() {
        return getAllWorkoutFilenamesSortedByDateDesc(Paths.get("")); // Використовуємо поточний каталог
    }

    /**
     * Завантажує тренування з JSON-файлу за його повною назвою.
     * Приймає базовий каталог для тестування.
     * @param baseDir Базовий каталог, де знаходиться підкаталог WORKOUTS_DIR_NAME.
     * @param fullFileName Повна назва файлу (наприклад, "LegDay_2025-04-08.json").
     * @return Об'єкт Workout або null у разі помилки.
     */
    public static Workout loadWorkoutByFileName(Path baseDir, String fullFileName) {
        if (fullFileName == null || fullFileName.trim().isEmpty()) {
            return null;
        }
        Path workoutsDirPath = baseDir.resolve(WORKOUTS_DIR_NAME);
        Path filePath = workoutsDirPath.resolve(fullFileName);

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            return null;
        }

        try {
            File workoutFile = filePath.toFile();
            // Переконайтесь, що клас Workout та вкладені Exercise налаштовані для Jackson
            return objectMapper.readValue(workoutFile, Workout.class);
        } catch (IOException e) {
            System.err.println("Error loading/parsing file '" + filePath + "': " + e.getMessage());
            return null;
        }
    }

    /**
     * Перевантажений метод для зручності, використовує поточний робочий каталог.
     */
    public static Workout loadWorkoutByFileName(String fullFileName) {
        return loadWorkoutByFileName(Paths.get(""), fullFileName);
    }


    /**
     * Зберігає об'єкт Workout у JSON-файл.
     * Приймає базовий каталог для тестування.
     * @param baseDir Базовий каталог, де буде створено/використано підкаталог WORKOUTS_DIR_NAME.
     * @param workout Об'єкт тренування для збереження.
     * @param fullFileName Повна назва файлу для збереження.
     * @return true, якщо збереження пройшло успішно, false в іншому випадку.
     */
    public static void saveWorkout(Path baseDir, Workout workout, String fullFileName) throws IOException {
        if (workout == null || fullFileName == null || fullFileName.trim().isEmpty()) {
            return;
        }
        Path workoutsDirPath = baseDir.resolve(WORKOUTS_DIR_NAME);
        Path filePath = workoutsDirPath.resolve(fullFileName);

        try {
            Files.createDirectories(workoutsDirPath); // Створюємо каталог, якщо його немає
            objectMapper.writeValue(filePath.toFile(), workout);
        } catch (IOException e) {
            System.err.println("Error saving file '" + filePath + "': " + e.getMessage());
            throw e;
        }
    }

    /**
     * Перевантажений метод для зручності, використовує поточний робочий каталог.
     */
    public static void saveWorkout(Workout workout, String fullFileName) throws IOException {
        try{
            saveWorkout(Paths.get(""), workout, fullFileName);
        }catch(Exception e){
            throw e;
        }
    }
}