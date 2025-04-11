package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepSetTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void constructorAndGetters_test() {
        RepSet repSet = new RepSet(10, 50);
        assertEquals(10, repSet.getRepCount());
        assertEquals(50, repSet.getWeight());
    }

    @Test
    void setters_test() {
        RepSet repSet = new RepSet();
        repSet.setRepCount(8);
        repSet.setWeight(60);
        assertEquals(8, repSet.getRepCount());
        assertEquals(60, repSet.getWeight());
    }

    @Test
    void toString_format_test() {
        RepSet repSet = new RepSet(12, 55);
        assertEquals("55 kg : 12", repSet.toString());
    }

    @Test
    void jsonPolymorphism_RepSetAsSet_test() throws JsonProcessingException {
        RepSet originalSet = new RepSet(8, 70);
        String json = objectMapper.writeValueAsString(originalSet);

        assertTrue(json.contains("\"@type\":\"RepSet\""), "JSON should contain type info 'RepSet'");
        assertTrue(json.contains("\"repCount\":8"));
        assertTrue(json.contains("\"weight\":70"));

        Set deserializedSet = objectMapper.readValue(json, Set.class);

        assertNotNull(deserializedSet);
        assertTrue(deserializedSet instanceof RepSet, "Deserialized object should be RepSet");
        RepSet deserializedRepSet = (RepSet) deserializedSet;
        assertEquals(originalSet.getRepCount(), deserializedRepSet.getRepCount());
        assertEquals(originalSet.getWeight(), deserializedRepSet.getWeight());
    }

    @Test
    void jsonDirectSerialization_RepSet_test() throws JsonProcessingException {
        RepSet originalSet = new RepSet(5, 100);
        String json = objectMapper.writeValueAsString(originalSet);

        RepSet deserializedSet = objectMapper.readValue(json, RepSet.class);

        assertNotNull(deserializedSet);
        assertEquals(originalSet.getRepCount(), deserializedSet.getRepCount());
        assertEquals(originalSet.getWeight(), deserializedSet.getWeight());
        assertTrue(json.contains("\"@type\":\"RepSet\""));
    }
}