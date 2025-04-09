package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SetTest {
    @Test
    public void testSetGettersAndSetters() {
        RepSet set = new RepSet((short) 15,(short) 120);

        assertEquals((short)15, set.getRepCount());
        assertEquals((short)120, set.getWeight());
    }

    @Test
    public void testSetJson() throws JsonProcessingException {
        RepSet set = new RepSet((short)1,(short)60);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(set);

        assertEquals("{\"@type\":\"StrengthSet\",\"repCount\":1,\"weight\":60}", json);
    }


//    @Test
//    public void testFail(){
//        assertFalse(true);
//    }
}