package GymNotebook.presenter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import GymNotebook.model.Workout;

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

public class XmlFileManager {

    private static final String WORKOUTS_DIR_NAME = "WorkoutsXmlJackson"; // Змінено назву каталогу
    private static final XmlMapper xmlMapper = createXmlMapper();
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private static XmlMapper createXmlMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Налаштування для уникнення обертаючого кореневого елемента за замовчуванням
        // Якщо ви хочете кореневий елемент <Workout>, цю опцію можна видалити
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        return mapper;
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

    public static List<String> getAllWorkoutFilenamesSortedByDateDesc(Path baseDir) {
        Path workoutsDirPath = baseDir.resolve(WORKOUTS_DIR_NAME);

        if (!Files.exists(workoutsDirPath) || !Files.isDirectory(workoutsDirPath)) {
            return new ArrayList<>();
        }

        try (Stream<Path> paths = Files.list(workoutsDirPath)) {
            List<WorkoutFileInfo> fileInfos = paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.toLowerCase().endsWith(".xml")) // Фільтруємо XML файли
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


    public static List<String> getAllWorkoutFilenamesSortedByDateDesc() {
        return getAllWorkoutFilenamesSortedByDateDesc(Paths.get("")); // Використовуємо поточний каталог
    }


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
            return xmlMapper.readValue(workoutFile, Workout.class);
        } catch (IOException e) {
            System.err.println("Error loading/parsing XML file '" + filePath + "': " + e.getMessage());
            return null;
        }
    }


    public static Workout loadWorkoutByFileName(String fullFileName) {
        return loadWorkoutByFileName(Paths.get(""), fullFileName);
    }


    public static void saveWorkout(Path baseDir, Workout workout, String fullFileName) throws IOException {
        if (workout == null || fullFileName == null || fullFileName.trim().isEmpty()) {
            return;
        }
        Path workoutsDirPath = baseDir.resolve(WORKOUTS_DIR_NAME);
        Path filePath = workoutsDirPath.resolve(fullFileName);

        try {
            Files.createDirectories(workoutsDirPath); // Створюємо каталог, якщо його немає
            xmlMapper.writeValue(filePath.toFile(), workout);
        } catch (IOException e) {
            System.err.println("Error saving XML file '" + filePath + "': " + e.getMessage());
            throw e;
        }
    }


    public static void saveWorkout(Workout workout, String fullFileName) throws IOException {
        try{
            saveWorkout(Paths.get(""), workout, fullFileName);
        }catch(Exception e){
            throw e;
        }
    }
}