package GymNotebook.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import GymNotebook.model.Workout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class JsonWorkoutStorage implements WorkoutStorageStrategy {

    private final ObjectMapper objectMapper;
    private static final String WORKOUTS_DIR_NAME = "Workouts";

    public JsonWorkoutStorage() {
        this.objectMapper = createObjectMapper();
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    @Override
    public void saveWorkout(Workout workout, String filename) throws IOException {
        File file = Paths.get(WORKOUTS_DIR_NAME, filename).toFile();
        objectMapper.writeValue(file, workout);
    }

    @Override
    public Workout loadWorkout(String filename) throws IOException {
        File file = Paths.get(WORKOUTS_DIR_NAME, filename).toFile();
        return objectMapper.readValue(file, Workout.class);
    }
}