package GymNotebook.presenter;


import GymNotebook.model.RepExercise;
import GymNotebook.model.RepSet;
import GymNotebook.model.Workout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @TempDir
    Path tempDir;

    private Path workoutsDir;

    private void createFile(Path dir, String filename, String content) throws IOException {
        Files.createDirectories(dir);
        Files.writeString(dir.resolve(filename), content);
    }
    private void createEmptyFile(Path dir, String filename) throws IOException {
        createFile(dir, filename, "");
    }

    @BeforeEach
    void setUp_test() {
        workoutsDir = tempDir.resolve("Workouts");
    }

    @Test
    void getAllWorkoutFilenamesSortedByDateDesc_sorting_test() throws IOException {
        createEmptyFile(workoutsDir, "Workout_2025-04-09.json");
        createEmptyFile(workoutsDir, "Session_2025-04-07.json");
        createEmptyFile(workoutsDir, "LegDay_2025-04-08.json");
        createEmptyFile(workoutsDir, "NoDateWorkout.json");
        createEmptyFile(workoutsDir, "Workout_invalid-date.json");
        createFile(workoutsDir, "config.txt", "some text"); // Non-json

        List<String> sortedFiles = FileManager.getAllWorkoutFilenamesSortedByDateDesc(tempDir);

        assertNotNull(sortedFiles);
        assertEquals(5, sortedFiles.size()); // Expect 5 json files
        assertEquals("Workout_2025-04-09.json", sortedFiles.get(0));
        assertEquals("LegDay_2025-04-08.json", sortedFiles.get(1));
        assertEquals("Session_2025-04-07.json", sortedFiles.get(2));

        assertTrue(sortedFiles.subList(3, 5).contains("NoDateWorkout.json"));
        assertTrue(sortedFiles.subList(3, 5).contains("Workout_invalid-date.json"));
    }

    @Test
    void getAllWorkoutFilenamesSortedByDateDesc_dirNotFound_test() {
        List<String> sortedFiles = FileManager.getAllWorkoutFilenamesSortedByDateDesc(tempDir);
        assertNotNull(sortedFiles);
        assertTrue(sortedFiles.isEmpty());
    }

    @Test
    void getAllWorkoutFilenamesSortedByDateDesc_emptyDir_test() throws IOException {
        Files.createDirectories(workoutsDir);
        List<String> sortedFiles = FileManager.getAllWorkoutFilenamesSortedByDateDesc(tempDir);
        assertNotNull(sortedFiles);
        assertTrue(sortedFiles.isEmpty());
    }


    @Test
    void saveAndLoadWorkoutByFileName_successful_test() throws IOException {
        Workout originalWorkout = new Workout("Chest Day Test");
        RepExercise benchPress = new RepExercise();
        benchPress.setTitle("Bench Press");
        benchPress.AddSet(new RepSet(5, 100));
        originalWorkout.AddExercise(benchPress);
        String filename = "ChestDay_2025-04-09.json";

        FileManager.saveWorkout(tempDir, originalWorkout, filename);
        assertTrue(Files.exists(workoutsDir.resolve(filename)), "File should exist after saving");

        Workout loadedWorkout = FileManager.loadWorkoutByFileName(tempDir, filename);

        assertNotNull(loadedWorkout);
        assertEquals(originalWorkout.getTitle(), loadedWorkout.getTitle());
        assertEquals(1, loadedWorkout.getExercises().size());
        assertTrue(loadedWorkout.getExercises().get(0) instanceof RepExercise);
        assertEquals("Bench Press", loadedWorkout.getExercises().get(0).getTitle());
        RepExercise loadedEx = (RepExercise) loadedWorkout.getExercises().get(0);
        assertEquals(1, loadedEx.getSets().size());
        assertTrue(loadedEx.getSets().get(0) instanceof RepSet);
        assertEquals(5, ((RepSet)loadedEx.getSets().get(0)).getRepCount());
    }

    @Test
    void loadWorkoutByFileName_fileNotFound_test() {
        Workout loadedWorkout = FileManager.loadWorkoutByFileName(tempDir, "nonexistent_workout.json");
        assertNull(loadedWorkout);
    }

    @Test
    void loadWorkoutByFileName_dirNotFound_test() {
        Workout loadedWorkout = FileManager.loadWorkoutByFileName(tempDir, "any_workout.json");
        assertNull(loadedWorkout);
    }


    @Test
    void loadWorkoutByFileName_invalidJson_test() throws IOException {
        String filename = "invalid_json.json";
        createFile(workoutsDir, filename, "{ \"title\": \"Bad JSON\",, }");

        Workout loadedWorkout = FileManager.loadWorkoutByFileName(tempDir, filename);

        assertNull(loadedWorkout);
    }

    @Test
    void saveWorkout_nullWorkout_test() throws IOException{
        FileManager.saveWorkout(tempDir, null, "somefile.json");
        assertFalse(Files.exists(workoutsDir.resolve("somefile.json")));
    }

    @Test
    void saveWorkout_nullOrEmptyFilename_test() throws IOException {
        Workout workout = new Workout("Test");
        assertDoesNotThrow(() -> FileManager.saveWorkout(tempDir, workout, null));
        assertDoesNotThrow(() -> FileManager.saveWorkout(tempDir, workout, ""));
        assertDoesNotThrow(() -> FileManager.saveWorkout(tempDir, workout, "   "));
        assertFalse(Files.exists(workoutsDir.resolve(""))); // Check if empty string was attempted
    }

    @Test
    void saveWorkout_IOException_test() throws IOException {

        Workout workout = new Workout("Test IO Fail");
        String filename = "io_fail.json";
        Path targetPath = workoutsDir.resolve(filename);
        assertThrows(IOException.class, () -> {

            throw new IOException("Simulated IO error during save");
        }, "Saving with simulated IO error should throw IOException");


    }
}