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
        this.footer = "[Number] - Select";
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
        this.footer = "[Number] - Select";

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
        System.out.println("-------------------------");

        if(currentPage > 1) this.footer += ", [N] - Next Page";
        if (currentPage < totalPages) this.footer += ", [B] Back";
        this.AddBackOptionToFooter();
    }

    /**
     * Handles user input, updating pagination or setting the info field.
     * Overrides method from parent (if defined there, otherwise just implements it).
     * @param input User input string.
     */
    @Override
    public void HandleInput(String input) {
        this.info = "";

        input = input.trim().toLowerCase();
        switch (input) {
            case "n": // Next Page
                if (currentPage < totalPages) {
                    currentPage++;
                } else {
                    this.info = "INFO: This is the last page.";
                }
                break;
            case "p": // Previous Page
                if (currentPage > 1) {
                    currentPage--;
                } else {
                    this.info = "INFO: This is the first page.";
                }
                break;
            case "b": // Back
                this.info = "INFO: Returning back...";
                presenter.GoBack();
                break;
            default: // Handle numeric input
                try {
                    int optionNumber = Integer.parseInt(input);

                    // Validate against total items
                    if (optionNumber >= 1 && optionNumber <= totalItems) {
                        int absoluteIndexZeroBased = optionNumber - 1;
                        String selectedFilename = allWorkoutFilenames.get(absoluteIndexZeroBased);

                        presenter.OpenWorkoutView(selectedFilename);

                    } else {
                        this.info = "ERROR: Invalid workout number. Please enter a number between 1 and " + totalItems + ".";
                    }
                } catch (NumberFormatException e) {
                    this.info = "ERROR: Unknown command or invalid number.";
                }
                break;
        }
    }
}