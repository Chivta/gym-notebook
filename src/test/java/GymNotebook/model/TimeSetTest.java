package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeSetTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void constructorAndGetters_test() {
        TimeSet timeSet = new TimeSet(60, 20);
        assertEquals(60, timeSet.getTime());
        assertEquals(20, timeSet.getWeight());
    }

    @Test
    void setters_test() {
        TimeSet timeSet = new TimeSet();
        timeSet.setTime(45);
        timeSet.setWeight(15);
        assertEquals(45, timeSet.getTime());
        assertEquals(15, timeSet.getWeight());
    }

    @Test
    void toString_format_test() {
        TimeSet timeSet = new TimeSet(90, 25);
        assertEquals("25 kg : 90 sec", timeSet.toString());
    }

    @Test
    void jsonDirectSerialization_TimeSet_test() throws JsonProcessingException {
        TimeSet originalSet = new TimeSet(120, 10);
        String json = objectMapper.writeValueAsString(originalSet);

        TimeSet deserializedSet = objectMapper.readValue(json, TimeSet.class);

        assertNotNull(deserializedSet);
        assertEquals(originalSet.getTime(), deserializedSet.getTime());
        assertEquals(originalSet.getWeight(), deserializedSet.getWeight());
        assertTrue(json.contains("\"@type\":\"TimeSet\""));
    }

    @Test
    void jsonPolymorphism_TimeSetAsSet_test() throws JsonProcessingException {
        TimeSet originalSet = new TimeSet(30, 5);
        String json = objectMapper.writeValueAsString(originalSet);

        Set deserializedSet = objectMapper.readValue(json, Set.class);

        assertNotNull(deserializedSet);
        assertTrue(deserializedSet instanceof TimeSet, "Deserialized object should be TimeSet");
        TimeSet deserializedTimeSet = (TimeSet) deserializedSet;
        assertEquals(originalSet.getTime(), deserializedTimeSet.getTime());
        assertEquals(originalSet.getWeight(), deserializedTimeSet.getWeight());
    }
}