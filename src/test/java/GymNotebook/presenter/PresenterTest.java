package GymNotebook.presenter;

import GymNotebook.model.Exercise;
import GymNotebook.model.RepSet;
import GymNotebook.model.Set;
import GymNotebook.model.Workout;
import GymNotebook.view.UIManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PresenterTest {

    @Mock
    private UIManager uiManager;
    private Presenter presenter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        presenter = new Presenter(uiManager);
    }

    @Test
    void goBack_test() {
        presenter.GoBack();
        verify(uiManager).GoBack();
    }


    @Test
    void addSetToCurrentExercise_test() {
        presenter.currentExercise = new Exercise();
        Set set = new RepSet(10, 50);
        presenter.AddSetToCurrentExercise(set);
        assertEquals(1, presenter.currentExercise.getSets().size());
        assertEquals(set, presenter.currentExercise.getSets().get(0));
    }

    @Test
    void addExerciseToCurrentWorkout_test() {
        presenter.currentWorkout = new Workout();
        Exercise exerciseToAdd = new Exercise();
        exerciseToAdd.setTitle("Присідання");
        presenter.AddExerciseToCurrentWorkout(exerciseToAdd);

        assertEquals(1, presenter.currentWorkout.getExercises().size());
        Exercise retrievedExercise = presenter.currentWorkout.getExercises().get(0);
        assertEquals(exerciseToAdd, retrievedExercise);
        assertEquals("Присідання", retrievedExercise.getTitle());
    }
}