package GymNotebook.presenter;

public class FileManagerTest {

    String fullFileName = "09.04.2025";

//    @Test
//    public void testSaveWorkout() {
//        Workout workout = new Workout("Day 1");
//
//        RepExercise exercise = new RepExercise("Bench Press");
//
//        RepSet set = new RepSet(1,60);
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
//        RepExercise exercise = new RepExercise("Bench Press");
//
//        RepSet set = new RepSet(1,60);
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
