package GymNotebook.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SetTest {
    @Test
    public void testSetGettersAndSetters() {
        StregthSet set = new StregthSet((short) 15,(short) 120);

        assertEquals((short)15, set.getRepCount());
        assertEquals((short)120, set.getWeight());
    }

    @Test
    public void testSetJson() throws JsonProcessingException {
        StregthSet set = new StregthSet((short)1,(short)60);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(set);

        assertEquals("{\"repCount\":1,\"weight\":60}", json);
    }


//    @Test
//    public void testFail(){
//        assertFalse(true);
//    }
}