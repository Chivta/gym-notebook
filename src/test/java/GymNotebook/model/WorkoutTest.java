// File: src/test/java/GymNotebook/model/WorkoutTest.java
package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {

    private ObjectMapper objectMapper;
    private Workout workout;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        // No MixIns needed as Exercise and Set annotations are now correct
        workout = new Workout("Full Body Test");
    }

    @Test
    void constructorsAndTitle_test() {
        assertEquals("Full Body Test", workout.getTitle());
        assertNotNull(workout.getExercises());
        assertTrue(workout.getExercises().isEmpty());

        Workout workoutNoTitle = new Workout();
        assertNull(workoutNoTitle.getTitle());
        assertNotNull(workoutNoTitle.getExercises());

        // Note: @AllArgsConstructor might conflict if other constructors exist
        // Let's test the state after using the title-only constructor
        Workout workoutTitleOnly = new Workout("Leg Day");
        assertEquals("Leg Day", workoutTitleOnly.getTitle());
        assertNotNull(workoutTitleOnly.getExercises());
        assertTrue(workoutTitleOnly.getExercises().isEmpty());
    }

    @Test
    void addExercise_addsDifferentExerciseTypes_test() {
        RepExercise repEx = new RepExercise();
        repEx.setTitle("Squats");
        TimeExercise timeEx = new TimeExercise();
        timeEx.setTitle("Running");

        workout.AddExercise(repEx);
        assertEquals(1, workout.getExercises().size());
        assertTrue(workout.getExercises().contains(repEx));

        workout.AddExercise(timeEx); // Now works correctly
        assertEquals(2, workout.getExercises().size());
        assertTrue(workout.getExercises().contains(timeEx));
    }

    @Test
    void toString_format_test() {
        RepExercise squats = new RepExercise();
        squats.setTitle("Squats");
        squats.AddSet(new RepSet(10, 80));
        squats.AddSet(new RepSet(8, 90));

        TimeExercise plank = new TimeExercise();
        plank.setTitle("Plank");
        plank.AddSet(new TimeSet(60, 0));

        workout.AddExercise(squats);
        workout.AddExercise(plank);

        // Expected format with "--" replacement
        String expected = "Full Body Test" + System.lineSeparator() +
                " - Squats" + System.lineSeparator() +
                " -- 80 kg : 10" + System.lineSeparator() +
                " -- 90 kg : 8" + System.lineSeparator() +
                " - Plank" + System.lineSeparator() +
                " -- 0 kg : 60 sec";
        assertEquals(expected, workout.toString());
    }

    @Test
    void jsonSerializationDeserialization_withPolymorphism_test() throws JsonProcessingException {
        // Arrange: Create workout with different Exercise and Set types
        RepExercise pushups = new RepExercise();
        pushups.setTitle("Push Ups");
        pushups.AddSet(new RepSet(15, 0));

        TimeExercise cardio = new TimeExercise();
        cardio.setTitle("Jumping Jacks");
        cardio.AddSet(new TimeSet(45, 0));

        workout.AddExercise(pushups);
        workout.AddExercise(cardio);

        // Act: Serialize
        String json = objectMapper.writeValueAsString(workout);
        // System.out.println(json); // Optional: print JSON for debugging

        // Assert JSON content (key types)
        assertTrue(json.contains("\"title\":\"Full Body Test\""));
        assertTrue(json.contains("\"exercises\""));
        assertTrue(json.contains("\"@type\":\"RepExercise\""));
        assertTrue(json.contains("\"@type\":\"TimeExercise\"")); // Should now work
        assertTrue(json.contains("\"@type\":\"RepSet\""));     // Updated name
        assertTrue(json.contains("\"@type\":\"TimeSet\""));    // Should now work

        // Act: Deserialize
        Workout deserializedWorkout = objectMapper.readValue(json, Workout.class);

        // Assert deserialized Workout object
        assertNotNull(deserializedWorkout);
        assertEquals(workout.getTitle(), deserializedWorkout.getTitle());
        assertEquals(workout.getExercises().size(), deserializedWorkout.getExercises().size());

        // Assert Exercise types and content within the list
        assertTrue(deserializedWorkout.getExercises().get(0) instanceof RepExercise);
        assertEquals("Push Ups", deserializedWorkout.getExercises().get(0).getTitle());
        assertEquals(1, deserializedWorkout.getExercises().get(0).getSets().size());
        assertTrue(deserializedWorkout.getExercises().get(0).getSets().get(0) instanceof RepSet);

        assertTrue(deserializedWorkout.getExercises().get(1) instanceof TimeExercise); // Should now work
        assertEquals("Jumping Jacks", deserializedWorkout.getExercises().get(1).getTitle());
        assertEquals(1, deserializedWorkout.getExercises().get(1).getSets().size());
        assertTrue(deserializedWorkout.getExercises().get(1).getSets().get(0) instanceof TimeSet);
    }
}