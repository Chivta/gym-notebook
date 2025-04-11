package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeExerciseTest {

    private ObjectMapper objectMapper;
    private TimeExercise exercise;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        exercise = new TimeExercise();
        exercise.setTitle("Plank");
    }

    @Test
    void constructorAndTitle_test() {
        assertNotNull(exercise.getSets());
        assertTrue(exercise.getSets().isEmpty());
        assertEquals("Plank", exercise.getTitle());
        exercise.setTitle("Running");
        assertEquals("Running", exercise.getTitle());
    }

    @Test
    void addSet_addsDifferentSetTypes_test() {
        TimeSet timeSet = new TimeSet(180, 0);
        RepSet repSet = new RepSet(15, 10);

        exercise.AddSet(timeSet);
        assertEquals(1, exercise.getSets().size());
        assertTrue(exercise.getSets().contains(timeSet));

        exercise.AddSet(repSet);
        assertEquals(2, exercise.getSets().size());
        assertTrue(exercise.getSets().contains(repSet));
    }

    @Test
    void toString_format_test() {
        exercise.AddSet(new TimeSet(60, 0));
        exercise.AddSet(new TimeSet(45, 0));

        String expected = "Plank" + System.lineSeparator() +
                " - 0 kg : 60 sec" + System.lineSeparator() +
                " - 0 kg : 45 sec";
        assertEquals(expected, exercise.toString());
    }

    @Test
    void jsonDirectSerialization_TimeExercise_test() throws JsonProcessingException {
        exercise.AddSet(new TimeSet(90, 5));
        exercise.AddSet(new RepSet(20, 0));

        String json = objectMapper.writeValueAsString(exercise);
        assertTrue(json.contains("\"@type\":\"TimeExercise\""));
        assertTrue(json.contains("\"title\":\"Plank\""));
        assertTrue(json.contains("\"@type\":\"TimeSet\""));
        assertTrue(json.contains("\"@type\":\"RepSet\""));

        TimeExercise deserializedExercise = objectMapper.readValue(json, TimeExercise.class);

        assertNotNull(deserializedExercise);
        assertEquals(exercise.getTitle(), deserializedExercise.getTitle());
        assertEquals(exercise.getSets().size(), deserializedExercise.getSets().size());
        assertTrue(deserializedExercise.getSets().get(0) instanceof TimeSet);
        assertTrue(deserializedExercise.getSets().get(1) instanceof RepSet);
    }

    @Test
    void jsonPolymorphism_TimeExerciseAsExercise_test() throws JsonProcessingException {
        exercise.AddSet(new TimeSet(30, 0));
        String json = objectMapper.writeValueAsString(exercise);

        Exercise deserializedExercise = objectMapper.readValue(json, Exercise.class);

        assertNotNull(deserializedExercise);
        assertTrue(deserializedExercise instanceof TimeExercise, "Deserialized object should be TimeExercise");
        TimeExercise deserializedTimeEx = (TimeExercise) deserializedExercise;
        assertEquals(exercise.getTitle(), deserializedTimeEx.getTitle());
        assertEquals(exercise.getSets().size(), deserializedTimeEx.getSets().size());
        assertTrue(deserializedTimeEx.getSets().get(0) instanceof TimeSet);
    }
}