package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepExerciseTest {

    private ObjectMapper objectMapper;
    private RepExercise exercise;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        exercise = new RepExercise();
        exercise.setTitle("Bench Press");
    }

    @Test
    void constructorAndTitle_test() {
        assertNotNull(exercise.getSets());
        assertTrue(exercise.getSets().isEmpty());
        assertEquals("Bench Press", exercise.getTitle());
        exercise.setTitle("Push Ups");
        assertEquals("Push Ups", exercise.getTitle());
    }

    @Test
    void addSet_addsDifferentSetTypes_test() {
        RepSet repSet = new RepSet(10, 50);
        TimeSet timeSet = new TimeSet(60, 10);

        exercise.AddSet(repSet);
        assertEquals(1, exercise.getSets().size());
        assertTrue(exercise.getSets().contains(repSet));

        exercise.AddSet(timeSet);
        assertEquals(2, exercise.getSets().size());
        assertTrue(exercise.getSets().contains(timeSet));
    }

    @Test
    void toString_format_test() {
        exercise.AddSet(new RepSet(8, 60));
        exercise.AddSet(new RepSet(6, 65));

        String expected = "Bench Press" + System.lineSeparator() +
                " - 60 kg : 8" + System.lineSeparator() +
                " - 65 kg : 6";
        assertEquals(expected, exercise.toString());
    }

    @Test
    void jsonPolymorphism_RepExerciseAsExercise_test() throws JsonProcessingException {
        exercise.AddSet(new RepSet(5, 70));
        exercise.AddSet(new TimeSet(45, 15));

        String json = objectMapper.writeValueAsString(exercise);
        assertTrue(json.contains("\"@type\":\"RepExercise\""));
        assertTrue(json.contains("\"title\":\"Bench Press\""));
        assertTrue(json.contains("\"sets\""));
        assertTrue(json.contains("\"@type\":\"RepSet\""));
        assertTrue(json.contains("\"@type\":\"TimeSet\""));
        assertTrue(json.contains("\"repCount\":5"));
        assertTrue(json.contains("\"weight\":70"));
        assertTrue(json.contains("\"time\":45"));
        assertTrue(json.contains("\"weight\":15"));

        Exercise deserializedExercise = objectMapper.readValue(json, Exercise.class);

        assertNotNull(deserializedExercise);
        assertTrue(deserializedExercise instanceof RepExercise);
        assertEquals(exercise.getTitle(), deserializedExercise.getTitle());
        assertEquals(exercise.getSets().size(), deserializedExercise.getSets().size());

        assertTrue(deserializedExercise.getSets().get(0) instanceof RepSet);
        assertEquals(5, ((RepSet) deserializedExercise.getSets().get(0)).getRepCount());
        assertTrue(deserializedExercise.getSets().get(1) instanceof TimeSet);
        assertEquals(45, ((TimeSet) deserializedExercise.getSets().get(1)).getTime());
    }
}