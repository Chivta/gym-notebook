package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {
    @Test
    public void testToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        StrengthExercise exercise = new StrengthExercise("Bench Press");

        StregthSet set = new StregthSet((short)3,(short)45);

        exercise.getSets().add(set);
        exercise.getSets().add(set);

        String json = mapper.writeValueAsString(exercise);

        String expected = "{" +
                "\"@type\":\"StrengthExercise\","+
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
        StrengthExercise actual = new StrengthExercise("Bench Press");

        StregthSet set = new StregthSet((short)3,(short)45);
        actual.getSets().add(set);
        actual.getSets().add(set);

        ObjectMapper mapper = new ObjectMapper();

        String json = "{" +
                "\"@type\":\"StrengthExercise\","+
                "\"title\":\"Bench Press\"," +
                "\"sets\":" +
                "[" +
                "{\"@type\":\"StrengthSet\",\"repCount\":3,\"weight\":45}," +
                "{\"@type\":\"StrengthSet\",\"repCount\":3,\"weight\":45}" +
                "]" +
                "}";
        StrengthExercise expected = mapper.readValue(json, StrengthExercise.class);
        assertEquals(mapper.writeValueAsString(actual),mapper.writeValueAsString(expected));

    }
}