package GymNotebook.model;

public record ParameterDescriptor(
        String parameterKey,
        String promptMessage,
        InputType expectedInputType,
        boolean isOptional
)
{
    public enum InputType { INTEGER, DOUBLE, STRING, BOOLEAN }
}
