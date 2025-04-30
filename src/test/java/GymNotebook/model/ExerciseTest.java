package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    private ObjectMapper objectMapper;
    private Exercise exercise;

    @com.fasterxml.jackson.annotation.JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY, property = "@type")
    @com.fasterxml.jackson.annotation.JsonSubTypes({
            @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = RepSet.class, name = "RepSet"),
            @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = TimeSet.class, name = "TimeSet")
    })
    static abstract class Set {}


    @BeforeEach
    void setUp_test() {
        objectMapper = new ObjectMapper();
        exercise = new Exercise();
    }

    @Test
    void constructor_initializesDefaults_test() {
        assertNull(exercise.getTitle());
        assertNotNull(exercise.getSets());
        assertTrue(exercise.getSets().isEmpty());
    }

    @Test
    void setAndGetTitle_test() {
        String testTitle = "Leg Day";
        exercise.setTitle(testTitle);
        assertEquals(testTitle, exercise.getTitle());
    }

    @Test
    void addSet_increasesListSizeAndContainsSets_test() {
        RepSet set1 = new RepSet(12, 100);
        TimeSet set2 = new TimeSet(60.0, 0);

        exercise.AddSet(set1);
        assertEquals(1, exercise.getSets().size());
        assertTrue(exercise.getSets().contains(set1));

        exercise.AddSet(set2);
        assertEquals(2, exercise.getSets().size());
        assertTrue(exercise.getSets().contains(set1));
        assertTrue(exercise.getSets().contains(set2));
    }

    @Test
    void toString_noTitleNoSets_test() {
        assertEquals("Untitled Exercise", exercise.toString());
    }

    @Test
    void toString_withTitleNoSets_test() {
        exercise.setTitle("Cardio");
        assertEquals("Cardio", exercise.toString());
    }

    @Test
    void toString_withTitleAndSets_test() {
        exercise.setTitle("Pull Workout");
        exercise.AddSet(new RepSet(8, 50));
        exercise.AddSet(new TimeSet(45, 10));

        String nl = System.lineSeparator();
        String expected = "Pull Workout" + nl +
                " - 50 kg : 8" + nl +
                " - 10 kg : 45 sec";
        assertEquals(expected, exercise.toString());
    }

    @Test
    void jsonSerializationDeserialization_withPolymorphicSets_test() throws JsonProcessingException {
        exercise.setTitle("Mixed Sets Exercise");
        RepSet repSet = new RepSet(5, 90);
        TimeSet timeSet = new TimeSet(30, 20);
        exercise.AddSet(repSet);
        exercise.AddSet(timeSet);

        String json = objectMapper.writeValueAsString(exercise);

        assertTrue(json.contains("\"title\":\"Mixed Sets Exercise\""));
        assertTrue(json.contains("\"sets\""));
        assertTrue(json.contains("\"@type\":\"RepSet\""));
        assertTrue(json.contains("\"repCount\":5"));
        assertTrue(json.contains("\"@type\":\"TimeSet\""));
        assertTrue(json.contains("\"time\":30"));

        Exercise deserializedExercise = objectMapper.readValue(json, Exercise.class);

        assertNotNull(deserializedExercise);
        assertEquals("Mixed Sets Exercise", deserializedExercise.getTitle());
        assertNotNull(deserializedExercise.getSets());
        assertEquals(2, deserializedExercise.getSets().size());

        assertTrue(deserializedExercise.getSets().get(0) instanceof RepSet);
        assertEquals(5, ((RepSet) deserializedExercise.getSets().get(0)).getRepCount());
        assertEquals(90, ((RepSet) deserializedExercise.getSets().get(0)).getWeight());

        assertTrue(deserializedExercise.getSets().get(1) instanceof TimeSet);
        assertEquals(30, ((TimeSet) deserializedExercise.getSets().get(1)).getTime());
        assertEquals(20, ((TimeSet) deserializedExercise.getSets().get(1)).getWeight());
    }
}