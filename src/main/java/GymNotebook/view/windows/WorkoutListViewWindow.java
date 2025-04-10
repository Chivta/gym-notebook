package GymNotebook.view.windows;

import GymNotebook.presenter.Presenter;

import java.util.List;

public class WorkoutListViewWindow extends Window {
    private List<String> allWorkoutFilenames;
    private int currentPage = 1;
    private final int itemsPerPage = 10;
    private int totalPages = 0;
    private int totalItems = 0;

    private final Presenter presenter;

    public WorkoutListViewWindow(Presenter presenter) {
        this.presenter = presenter;

        this.header = "Workout List (Sorted by Date)";
        inputOptions.addFirst("[Number] - Select");
        loadWorkoutData();
    }

    // No getInfo() needed - handled by parent or direct access

    private void loadWorkoutData() {
        this.allWorkoutFilenames = presenter.GetWorkoutFilenamesSorted();
        this.totalItems = this.allWorkoutFilenames.size();
        this.totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        // Reset to page 1 when data is loaded/reloaded
        this.currentPage = 1;
    }

    @Override
    protected void SendBody() {
        if (allWorkoutFilenames == null || allWorkoutFilenames.isEmpty()) {
            System.out.println("No saved workouts found.");
            return;
        }
        // Reseting footer
        inputOptions.addFirst("[Number] - Select");

        // Ensure current page is valid
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;

        // Calculate page boundaries
        int startIndex = (currentPage - 1) * itemsPerPage;
        if (startIndex < 0) startIndex = 0;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);
        List<String> currentPageFiles = allWorkoutFilenames.subList(startIndex, endIndex);

        System.out.println("--- Page " + currentPage + " of " + totalPages + " (Total: " + totalItems + ") ---");

        if (currentPageFiles.isEmpty() && totalItems > 0) {
            System.out.println("No workouts on this page.");
        } else {
            for (int i = 0; i < currentPageFiles.size(); i++) {
                String filename = currentPageFiles.get(i);
                int absoluteNumber = startIndex + i + 1;
                System.out.println(absoluteNumber + ". " + filename);
            }
        }

        if(currentPage > 1) inputOptions.add("[N] - Next Page");
        if (currentPage < totalPages) inputOptions.add("[P] - Previous Page");
    }

    @Override
    public void HandleInput(String input) {
        switch (input) {
            case "n":
                HandleNextPage();
                break;
            case "p":
                HandlePreviousPage();
                break;
            default:
                HandleWorkoutNumber(input);
                break;
        }
    }

    private void HandleNextPage(){
        if (currentPage < totalPages) {
            currentPage++;
        } else {
            this.info = "INFO: This is the last page.";
        }
    }

    private void HandlePreviousPage(){
        if (currentPage > 1) {
            currentPage--;
        } else {
            this.info = "INFO: This is the first page.";
        }
    }

    private void HandleWorkoutNumber(String input){
        try {
            int optionNumber = Integer.parseInt(input);

            // Validate against total items
            if (optionNumber >= 1 && optionNumber <= totalItems) {
                int absoluteIndexZeroBased = optionNumber - 1;
                String selectedFilename = allWorkoutFilenames.get(absoluteIndexZeroBased);

                presenter.OpenWorkoutView(selectedFilename);

            } else {
                this.info = "ERR: Invalid workout number. Please enter a number between 1 and " + totalItems + ".";
            }
        } catch (NumberFormatException e) {
            this.info = "ERR: Invalid input.";
        }
    }
}