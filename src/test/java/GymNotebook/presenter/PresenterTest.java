// File: src/test/java/GymNotebook/presenter/PresenterTest.java
package GymNotebook.presenter;

import GymNotebook.model.Exercise;
import GymNotebook.model.RepExercise; // Assuming you have this concrete class
import GymNotebook.model.RepSet; // Assuming you have this concrete class
import GymNotebook.model.Set;
import GymNotebook.model.Workout;
import GymNotebook.view.UIManager;
import GymNotebook.view.windows.*; // Import specific window classes

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Initialize mocks
class PresenterTest {

    @Mock
    private UIManager mockUi;

    private Presenter presenter;

    private MockedStatic<FileManager> fileManagerMockedStatic;
    private MockedStatic<Files> filesMockedStatic;
    private MockedStatic<Paths> pathsMockedStatic;


    @BeforeEach
    void setUp() {
        presenter = new Presenter(mockUi);
        fileManagerMockedStatic = mockStatic(FileManager.class);
        filesMockedStatic = mockStatic(Files.class);
        pathsMockedStatic = mockStatic(Paths.class);
        Path mockWorkoutPath = mock(Path.class);
        pathsMockedStatic.when(() -> Paths.get("Workouts")).thenReturn(mockWorkoutPath);
        filesMockedStatic.when(() -> Files.exists(any(Path.class))).thenReturn(false);
    }

    @AfterEach
    void tearDown() {
        fileManagerMockedStatic.close();
        filesMockedStatic.close();
        pathsMockedStatic.close();
    }

    @Test
    void constructor_test() {
        assertNotNull(presenter);
    }


    @Test
    void OpenWorkoutListView_callsUiChangeWindow_test() {
        presenter.OpenWorkoutListView();
        ArgumentCaptor<Window> windowCaptor = ArgumentCaptor.forClass(Window.class);
        verify(mockUi, times(1)).ChangeWindow(windowCaptor.capture());
        assertTrue(windowCaptor.getValue() instanceof WorkoutListViewWindow);
    }

    @Test
    void GoBack_callsUiGoBack_test() {
        presenter.GoBack();
        verify(mockUi, times(1)).GoBack();
    }

    @Test
    void GetWorkoutFilenamesSorted_callsFileManager_test() {
        List<String> expectedList = List.of("file1.json", "file2.json");
        fileManagerMockedStatic.when(FileManager::getAllWorkoutFilenamesSortedByDateDesc)
                .thenReturn(expectedList);

        List<String> actualList = presenter.GetWorkoutFilenamesSorted();

        assertEquals(expectedList, actualList);
        fileManagerMockedStatic.verify(FileManager::getAllWorkoutFilenamesSortedByDateDesc, times(1));
    }

    @Test
    void OpenNewWorkoutCreation_whenNoCurrentWorkout_createsNewAndChangesWindow_test() {
        presenter.currentWorkout = null;

        presenter.OpenNewWorkoutCreation();

        assertNotNull(presenter.currentWorkout, "Presenter should have created a new workout object");

        ArgumentCaptor<Window> windowCaptor = ArgumentCaptor.forClass(Window.class);
        verify(mockUi).ChangeWindow(windowCaptor.capture());
        assertTrue(windowCaptor.getValue() instanceof NewWorkoutCreationWindow,
                "Should change to NewWorkoutCreationWindow");

    }

    @Test
    void OpenNewWorkoutCreation_whenCurrentWorkoutExists_usesExistingAndChangesWindow_test() {
        Workout existingWorkout = new Workout("Existing Workout Title");
        presenter.currentWorkout = existingWorkout;

        presenter.OpenNewWorkoutCreation();

        assertSame(existingWorkout, presenter.currentWorkout, "Presenter should reuse the existing workout");

        ArgumentCaptor<Window> windowCaptor = ArgumentCaptor.forClass(Window.class);
        verify(mockUi).ChangeWindow(windowCaptor.capture());
        assertTrue(windowCaptor.getValue() instanceof NewWorkoutCreationWindow,
                "Should change to NewWorkoutCreationWindow");

    }

    @Test
    void OpenNewExercise_callsUiChangeWindow_test() {
        presenter.OpenNewExercise();
        ArgumentCaptor<Window> windowCaptor = ArgumentCaptor.forClass(Window.class);
        verify(mockUi).ChangeWindow(windowCaptor.capture());
        assertInstanceOf(NewExerciseWindow.class, windowCaptor.getValue());
    }

    @Test
    void SaveCurrentExercise_setsField_test() {
        Exercise testExercise = new RepExercise();
        testExercise.setTitle("Test Ex");
        presenter.currentExercise = null;

        presenter.SaveCurrentExercise(testExercise);

        assertSame(testExercise, presenter.currentExercise);
    }


    @Test
    void OpenNewRepSet_callsUiChangeWindow_test() {
        presenter.OpenNewRepSet();
        ArgumentCaptor<Window> windowCaptor = ArgumentCaptor.forClass(Window.class);
        verify(mockUi).ChangeWindow(windowCaptor.capture());
        assertTrue(windowCaptor.getValue() instanceof NewRepSetWindow);
    }

    @Test
    void OpenNewTimeSet_callsUiChangeWindow_test() {
        presenter.OpenNewTimeSet();
        ArgumentCaptor<Window> windowCaptor = ArgumentCaptor.forClass(Window.class);
        verify(mockUi).ChangeWindow(windowCaptor.capture());
        assertTrue(windowCaptor.getValue() instanceof NewTimeSetWindow);
    }


    @Test
    void AddSetToCurrentExercise_callsExerciseAddSet_test() {
        Exercise mockExercise = mock(RepExercise.class);
        presenter.currentExercise = mockExercise;
        Set testSet = new RepSet(10, 50);

        presenter.AddSetToCurrentExercise(testSet);

        verify(mockExercise, times(1)).AddSet(testSet);
    }

    @Test
    void AddExerciseToCurrentWorkout_addsExerciseToList_test() {
        Workout testWorkout = new Workout("Test Workout");
        presenter.currentWorkout = testWorkout;
        Exercise testExercise = new RepExercise();
        testExercise.setTitle("Added Ex");

        presenter.AddExerciseToCurrentWorkout(testExercise);

        assertEquals(1, presenter.currentWorkout.getExercises().size());
        assertSame(testExercise, presenter.currentWorkout.getExercises().get(0));
    }

    @Test
    void saveCurrentWorkout_whenWorkoutNull_doesNothing_test() {
        presenter.currentWorkout = null;
        presenter.saveCurrentWorkout();

        fileManagerMockedStatic.verify(
                () -> FileManager.saveWorkout(any(Workout.class), anyString()),
                never()
        );
        verify(mockUi, never()).ClearHistory();
        verify(mockUi, never()).ChangeWindow(any());
    }

    @Test
    void saveCurrentWorkout_whenFileDoesNotExist_savesWithBaseName_test() {
        Workout workoutToSave = new Workout("My_Workout");
        presenter.currentWorkout = workoutToSave;
        String expectedDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String expectedFilename = expectedDate + "_My_Workout.json";
        Path mockDestPath = mock(Path.class); // Mock the final path object

        Path mockWorkoutDirPath = Paths.get("Workouts"); // Get the mocked path
        when(mockWorkoutDirPath.resolve(expectedFilename)).thenReturn(mockDestPath);

        filesMockedStatic.when(() -> Files.exists(mockDestPath)).thenReturn(false);

        fileManagerMockedStatic.when(() -> FileManager.saveWorkout(any(Workout.class), anyString()))
                .thenAnswer(invocation -> null); // Does nothing, returns void basically

        presenter.saveCurrentWorkout();

        fileManagerMockedStatic.verify(() -> FileManager.saveWorkout(eq(workoutToSave), eq(expectedFilename)), times(1));
        verify(mockUi, times(1)).ClearHistory();
        verify(mockUi, times(1)).ChangeWindow(any(MainMenuWindow.class));

        assertNull(presenter.currentWorkout);
    }

    @Test
    void saveCurrentWorkout_whenFileExists_savesWithCounter_test(){

        Workout workoutToSave = new Workout("My Workout");
        presenter.currentWorkout = workoutToSave;
        String expectedDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String sanitizedTitle = "My_Workout"; // Sanitized version
        String baseFilename = expectedDate + "_" + sanitizedTitle;
        String initialFilename = baseFilename + ".json";
        String firstAttemptFilename = baseFilename + "(1).json"; // Expect this one

        Path mockWorkoutDirPath = Paths.get("Workouts");
        Path mockInitialPath = mock(Path.class);
        Path mockFirstAttemptPath = mock(Path.class);

        when(mockWorkoutDirPath.resolve(initialFilename)).thenReturn(mockInitialPath);
        when(mockWorkoutDirPath.resolve(firstAttemptFilename)).thenReturn(mockFirstAttemptPath);

        filesMockedStatic.when(() -> Files.exists(mockInitialPath)).thenReturn(true);
        filesMockedStatic.when(() -> Files.exists(mockFirstAttemptPath)).thenReturn(false);

        fileManagerMockedStatic.when(() -> FileManager.saveWorkout(any(Workout.class), anyString()))
                .thenAnswer(invocation -> null);

        presenter.saveCurrentWorkout();

        fileManagerMockedStatic.verify(() -> FileManager.saveWorkout(eq(workoutToSave), eq(firstAttemptFilename)), times(1));
        verify(mockUi, times(1)).ClearHistory();
        verify(mockUi, times(1)).ChangeWindow(any(MainMenuWindow.class));

        assertNull(presenter.currentWorkout);
    }

    @Test
    void saveCurrentWorkout_whenSaveThrowsIOException_handlesError_test() throws IOException {
        Workout workoutToSave = new Workout("Error Workout");
        presenter.currentWorkout = workoutToSave;
        String expectedDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String expectedFilename = expectedDate + "_Error_Workout.json";
        Path mockDestPath = mock(Path.class);

        Path mockWorkoutDirPath = Paths.get("Workouts");
        when(mockWorkoutDirPath.resolve(expectedFilename)).thenReturn(mockDestPath);
        filesMockedStatic.when(() -> Files.exists(mockDestPath)).thenReturn(false);

        IOException testException = new IOException("Disk full");
        fileManagerMockedStatic.when(() -> FileManager.saveWorkout(eq(workoutToSave), eq(expectedFilename)))
                .thenThrow(testException);

        presenter.saveCurrentWorkout();

        verify(mockUi, never()).ClearHistory();
        verify(mockUi, never()).ChangeWindow(any(MainMenuWindow.class));
        assertSame(workoutToSave, presenter.currentWorkout);
    }


    @Test
    void OpenWorkoutView_loadsAndChangesWindow_test() {
        String testFilename = "test_workout.json";
        Workout mockWorkout = new Workout("Loaded Workout");
        fileManagerMockedStatic.when(() -> FileManager.loadWorkoutByFileName(testFilename))
                .thenReturn(mockWorkout);

        presenter.OpenWorkoutView(testFilename);


        fileManagerMockedStatic.verify(() -> FileManager.loadWorkoutByFileName(eq(testFilename)), times(1));

        ArgumentCaptor<Window> windowCaptor = ArgumentCaptor.forClass(Window.class);
        verify(mockUi).ChangeWindow(windowCaptor.capture());
        assertTrue(windowCaptor.getValue() instanceof WorkoutViewWindow,
                "Should change to WorkoutViewWindow when workout loads successfully");
    }

    @Test
    void OpenWorkoutView_whenLoadFails_changesWindowWithCorrectType_test() { // Renamed slightly
        String testFilename = "missing_workout.json";
        fileManagerMockedStatic.when(() -> FileManager.loadWorkoutByFileName(testFilename))
                .thenReturn(null);

        presenter.OpenWorkoutView(testFilename);


        fileManagerMockedStatic.verify(() -> FileManager.loadWorkoutByFileName(eq(testFilename)), times(1));

        ArgumentCaptor<Window> windowCaptor = ArgumentCaptor.forClass(Window.class);
        verify(mockUi).ChangeWindow(windowCaptor.capture());
        assertTrue(windowCaptor.getValue() instanceof WorkoutViewWindow,
                "Should change to WorkoutViewWindow even if workout loading fails");
    }

}