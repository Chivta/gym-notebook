package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {
    @Test
    public void testToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Exercise exercise = new Exercise("Bench Press");

        Set set = new Set((short)3,(short)45);

        exercise.getSets().add(set);
        exercise.getSets().add(set);

        String json = mapper.writeValueAsString(exercise);

        String expected = "{\"title\":\"Bench Press\"," +
                "\"sets\":" +
                    "[" +
                        "{\"repCount\":3,\"weight\":45}," +
                        "{\"repCount\":3,\"weight\":45}" +
                    "]" +
                "}";

        assertEquals(expected,json);

    }

    @Test
    public void testFromJson() throws JsonProcessingException {
        Exercise exercise = new Exercise("Bench Press");
        Set set = new Set((short)3,(short)45);
        exercise.getSets().add(set);
        exercise.getSets().add(set);

        ObjectMapper mapper = new ObjectMapper();

        String json = "{\"title\":\"Bench Press\"," +
                "\"sets\":" +
                    "[" +
                        "{\"repCount\":3,\"weight\":45}," +
                        "{\"repCount\":3,\"weight\":45}" +
                    "]" +
                "}";
        Exercise expected = mapper.readValue(json, Exercise.class);

        //assertEquals( mapper.writeValueAsString(exercise),mapper.writeValueAsString(expected));
        assertEquals(expected.getTitle(), exercise.getTitle());
        assertArrayEquals(exercise.getSets().toArray(), exercise.getSets().toArray());

    }
}