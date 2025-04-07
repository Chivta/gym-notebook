package GymNotebook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SetTest {
    @Test
    public void testSetConstructor() {
        Set set = new Set((short) 10, (short) 100);

        assertEquals(10, set.getRepCount());
        assertEquals(100, set.getWeight());
    }

    @Test
    public void testSetGettersAndSetters() {
        Set set = new Set();

        set.setRepCount((short) 15);
        set.setWeight((short) 120);

        assertEquals(15, set.getRepCount());
        assertEquals(120, set.getWeight());
    }

    @Test
    public void testSetJson() throws JsonProcessingException {
        Set set = new Set((short)1,(short)60);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(set);

        assertEquals("{\"repCount\":1,\"weight\":60}", json);
    }


//    @Test
//    public void testFail(){
//        assertFalse(true);
//    }
}