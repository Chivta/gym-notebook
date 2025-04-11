// File: src/test/java/GymNotebook/model/RepExerciseTest.java
package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList; // Needed for List

import static org.junit.jupiter.api.Assertions.*;

class RepExerciseTest {

    private ObjectMapper objectMapper;
    private RepExercise exercise;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        // No MixIns needed anymore
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
        TimeSet timeSet = new TimeSet(60, 10); // Now works correctly due to updated Set annotations

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
        // Arrange
        exercise.AddSet(new RepSet(5, 70));
        exercise.AddSet(new TimeSet(45, 15)); // Now works correctly

        // Act: Serialize
        String json = objectMapper.writeValueAsString(exercise);
        // Assert JSON content
        assertTrue(json.contains("\"@type\":\"RepExercise\"")); // Type of Exercise
        assertTrue(json.contains("\"title\":\"Bench Press\""));
        assertTrue(json.contains("\"sets\""));
        assertTrue(json.contains("\"@type\":\"RepSet\""));    // Type of RepSet
        assertTrue(json.contains("\"@type\":\"TimeSet\""));   // Type of TimeSet
        assertTrue(json.contains("\"repCount\":5"));
        assertTrue(json.contains("\"weight\":70"));
        assertTrue(json.contains("\"time\":45"));
        assertTrue(json.contains("\"weight\":15"));

        // Act: Deserialize into abstract Exercise
        Exercise deserializedExercise = objectMapper.readValue(json, Exercise.class);

        // Assert deserialized object
        assertNotNull(deserializedExercise);
        assertTrue(deserializedExercise instanceof RepExercise);
        assertEquals(exercise.getTitle(), deserializedExercise.getTitle());
        assertEquals(exercise.getSets().size(), deserializedExercise.getSets().size());

        // Assert types of sets within the list
        assertTrue(deserializedExercise.getSets().get(0) instanceof RepSet);
        assertEquals(5, ((RepSet) deserializedExercise.getSets().get(0)).getRepCount());
        assertTrue(deserializedExercise.getSets().get(1) instanceof TimeSet);
        assertEquals(45, ((TimeSet) deserializedExercise.getSets().get(1)).getTime());
    }
}