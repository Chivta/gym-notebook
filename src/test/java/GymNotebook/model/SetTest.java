package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SetTest {
    @Test
    public void testSetGettersAndSetters() {
        RepSet set = new RepSet( 15, 120);

        assertEquals(15, set.getRepCount());
        assertEquals(120, set.getWeight());
    }

    @Test
    public void testSetJson() throws JsonProcessingException {
        RepSet set = new RepSet(1, 60);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(set);

        assertEquals("{\"@type\":\"RepSet\",\"repCount\":1,\"weight\":60}", json);
    }

}