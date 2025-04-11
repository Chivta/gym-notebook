package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {

    private ObjectMapper objectMapper;
    private Workout workout;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
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

        Workout workoutTitleOnly = new Workout("Leg Day");
        assertEquals("Leg Day", workoutTitleOnly.getTitle());
        assertNotNull(workoutTitleOnly.getExercises());
        assertTrue(workoutTitleOnly.getExercises().isEmpty());
    }

    @Test
    void addExercise_addsExercises_test() {
        Exercise squats = new Exercise();
        squats.setTitle("Squats");
        Exercise running = new Exercise();
        running.setTitle("Running");

        workout.AddExercise(squats);
        assertEquals(1, workout.getExercises().size());
        assertTrue(workout.getExercises().contains(squats));

        workout.AddExercise(running);
        assertEquals(2, workout.getExercises().size());
        assertTrue(workout.getExercises().contains(running));
    }

    @Test
    void toString_format_test() {
        Exercise squats = new Exercise();
        squats.setTitle("Squats");
        squats.AddSet(new RepSet(10, 80));
        squats.AddSet(new RepSet(8, 90));

        Exercise plank = new Exercise();
        plank.setTitle("Plank");
        plank.AddSet(new TimeSet(60, 0));

        workout.AddExercise(squats);
        workout.AddExercise(plank);

        String expected = "Full Body Test" + System.lineSeparator() +
                " - Squats" + System.lineSeparator() +
                " -- 80 kg : 10" + System.lineSeparator() +
                " -- 90 kg : 8" + System.lineSeparator() +
                " - Plank" + System.lineSeparator() +
                " -- 0 kg : 60 sec";
        assertEquals(expected, workout.toString());
    }

    @Test
    void jsonSerializationDeserialization_test() throws JsonProcessingException {
        Exercise pushups = new Exercise();
        pushups.setTitle("Push Ups");
        pushups.AddSet(new RepSet(15, 0));

        Exercise cardio = new Exercise();
        cardio.setTitle("Jumping Jacks");
        cardio.AddSet(new TimeSet(45, 0));

        workout.AddExercise(pushups);
        workout.AddExercise(cardio);

        String json = objectMapper.writeValueAsString(workout);

        assertTrue(json.contains("\"title\":\"Full Body Test\""));
        assertTrue(json.contains("\"exercises\""));
        assertTrue(json.contains("\"title\":\"Push Ups\""));
        assertTrue(json.contains("\"@type\":\"RepSet\""));
        assertTrue(json.contains("\"title\":\"Jumping Jacks\""));
        assertTrue(json.contains("\"@type\":\"TimeSet\""));

        Workout deserializedWorkout = objectMapper.readValue(json, Workout.class);

        assertNotNull(deserializedWorkout);
        assertEquals(workout.getTitle(), deserializedWorkout.getTitle());
        assertEquals(workout.getExercises().size(), deserializedWorkout.getExercises().size());

        assertEquals("Push Ups", deserializedWorkout.getExercises().get(0).getTitle());
        assertEquals(1, deserializedWorkout.getExercises().get(0).getSets().size());
        assertTrue(deserializedWorkout.getExercises().get(0).getSets().get(0) instanceof RepSet);

        assertEquals("Jumping Jacks", deserializedWorkout.getExercises().get(1).getTitle());
        assertEquals(1, deserializedWorkout.getExercises().get(1).getSets().size());
        assertTrue(deserializedWorkout.getExercises().get(1).getSets().get(0) instanceof TimeSet);
    }

    @Test
    void equals_test() throws JsonProcessingException{
        String json = "{\n" +
                "  \"title\" : \"Full Body Test\",\n" +
                "  \"exercises\" : [ {\n" +
                "    \"title\" : \"Bench press\",\n" +
                "    \"sets\" : [ {\n" +
                "      \"@type\" : \"RepSet\",\n" +
                "      \"repCount\" : 5,\n" +
                "      \"weight\" : 60\n" +
                "    } ]\n" +
                "  } ]\n" +
                "}";

        Workout expected = objectMapper.readValue(json,Workout.class);

        RepSet set = new RepSet();

        set.setWeight(60);
        set.setRepCount(5);

        Exercise ex = new Exercise("Bench press");
        ex.AddSet(set);

        workout.AddExercise(ex);
        workout.setTitle("Full Body Test");

        assertEquals(workout,expected);
    }
}