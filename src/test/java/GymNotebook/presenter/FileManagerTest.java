package GymNotebook.presenter;

import GymNotebook.model.StrengthExercise;
import GymNotebook.model.StregthSet;
import GymNotebook.model.Workout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {

    String fullFileName = "09.04.2025";

//    @Test
//    public void testSaveWorkout() {
//        Workout workout = new Workout("Day 1");
//
//        StrengthExercise exercise = new StrengthExercise("Bench Press");
//
//        StregthSet set = new StregthSet(1,60);
//
//        exercise.getSets().add(set);
//
//        workout.getExercises().add(exercise);
//
//        try{
//            FileManager.saveWorkout(workout,fullFileName);
//        }
//        catch(Exception e){
//            System.err.println(e.getMessage());
//        }
//    }

//    @Test
//    public void testLoadWorkout() throws JsonProcessingException {
//        Workout actuallWorkout = FileManager.LoadWorkout(fullFileName);
//
//        assertNotNull(actuallWorkout);
//
//        //System.out.println(actuallWorkout);
//
//        Workout expectedWorkout = new Workout("Day 1");
//
//        StrengthExercise exercise = new StrengthExercise("Bench Press");
//
//        StregthSet set = new StregthSet(1,60);
//
//        exercise.getSets().add(set);
//
//        expectedWorkout.getExercises().add(exercise);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        assertEquals(mapper.writeValueAsString(expectedWorkout),mapper.writeValueAsString(actuallWorkout));
//    }
}
