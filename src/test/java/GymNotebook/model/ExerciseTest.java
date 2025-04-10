package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {
    @Test
    public void testToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        RepExercise exercise = new RepExercise();
        exercise.setTitle("BenchPress");

        RepSet set = new RepSet((short)3,(short)45);

        exercise.getSets().add(set);
        exercise.getSets().add(set);

        String json = mapper.writeValueAsString(exercise);

        String expected = "{" +
                "\"@type\":\"RepExercise\","+
                "\"title\":\"Bench Press\"," +
                "\"sets\":" +
                "[" +
                "{\"@type\":\"StrengthSet\",\"repCount\":3,\"weight\":45}," +
                "{\"@type\":\"StrengthSet\",\"repCount\":3,\"weight\":45}" +
                "]" +
                "}";

        assertEquals(expected,json);

    }

    @Test
    public void testFromJson() throws JsonProcessingException {
        RepExercise actual = new RepExercise();
        actual.setTitle("BenchPress");

        RepSet set = new RepSet((short)3,(short)45);
        actual.getSets().add(set);
        actual.getSets().add(set);

        ObjectMapper mapper = new ObjectMapper();

        String json = "{" +
                "\"@type\":\"RepExercise\","+
                "\"title\":\"Bench Press\"," +
                "\"sets\":" +
                "[" +
                "{\"@type\":\"StrengthSet\",\"repCount\":3,\"weight\":45}," +
                "{\"@type\":\"StrengthSet\",\"repCount\":3,\"weight\":45}" +
                "]" +
                "}";
        RepExercise expected = mapper.readValue(json, RepExercise.class);
        assertEquals(mapper.writeValueAsString(actual),mapper.writeValueAsString(expected));

    }
}