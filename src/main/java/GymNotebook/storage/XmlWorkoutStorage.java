package GymNotebook.storage;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import GymNotebook.model.Workout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class XmlWorkoutStorage implements WorkoutStorageStrategy {

    private final XmlMapper xmlMapper;
    private static final String WORKOUTS_DIR_NAME = "Workouts";

    public XmlWorkoutStorage() {
        this.xmlMapper = createXmlMapper();
    }

    private XmlMapper createXmlMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        return mapper;
    }

    @Override
    public void saveWorkout(Workout workout, String filename) throws IOException {
        File file = Paths.get(WORKOUTS_DIR_NAME, filename).toFile();
        xmlMapper.writeValue(file, workout);
    }

    @Override
    public Workout loadWorkout(String filename) throws IOException {
        File file = Paths.get(WORKOUTS_DIR_NAME, filename).toFile();
        return xmlMapper.readValue(file, Workout.class);
    }
}