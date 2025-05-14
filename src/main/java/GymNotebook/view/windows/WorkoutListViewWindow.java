package GymNotebook.view.windows;

import GymNotebook.service.WorkoutListService;
import GymNotebook.presenter.commands.ICommand;
import GymNotebook.presenter.commands.OpenWorkoutView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutListViewWindow extends Window {
    private final WorkoutListService workoutListService;

    public WorkoutListViewWindow(WorkoutListService workoutListService) {
        this.workoutListService = workoutListService;

        this.header = "Workout List (Sorted by Date)";
    }


    @Override
    protected void SendBody() {
        int currentPage = workoutListService.getCurrentPage();
        int maxPage = workoutListService.getMaxPage();
        int totalItems = workoutListService.getItemsCount();

        inputOptions.clear();

        inputOptions.addFirst("[Number] - Select");

        if (totalItems == 0) {
            System.out.println("No saved workouts found.");
            return;
        }

        System.out.println("--- Page " + currentPage + " of " + maxPage + " (Total: " + totalItems + ") ---");

        List<String> workoutsOnCurrentPage = workoutListService.getCurrentPageWorkouts();

        for(String line : workoutsOnCurrentPage){
            System.out.println(line);
        }


        if (workoutListService.isPreviousPage())  inputOptions.add("[P] - Previous Page");
        if (workoutListService.isNextPage()) inputOptions.add("[N] - Next Page");
    }

    @Override
    public List<ICommand> HandleInput(String input) throws WindowException {
        List<ICommand> ICommands = new ArrayList<>();

        switch (input) {
            case "n":
                workoutListService.NextPage();
                break;
            case "p":
                workoutListService.PreviousPage();
                break;
            default:
                ICommands.addAll(HandleWorkoutNumber(input));
                break;
        }

        return ICommands;
    }

    private List<ICommand> HandleWorkoutNumber(String input) throws WindowException {
        List<ICommand> ICommands = new ArrayList<>();

        try {
            int optionNumber = Integer.parseInt(input);

            String WorkoutName = workoutListService.getWorkoutFilename(optionNumber);

            if (!WorkoutName.isEmpty()) {
                ICommands.add(new OpenWorkoutView(WorkoutName));
            } else {
                throw new WindowException("ERR: Invalid workout number. Please enter a number between 1 and " + workoutListService.getItemsCount());
            }
        } catch (NumberFormatException e) {
            throw new WindowException("ERR: Invalid input.");
        }
        return ICommands;
    }
}