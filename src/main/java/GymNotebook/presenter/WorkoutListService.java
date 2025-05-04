package GymNotebook.presenter;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutListService {
    private List<String> allWorkouts;
    @Getter
    private List<String> currentPageWorkouts;

    private final int ItemsPerPage;
    @Getter
    private int CurrentPage;
    @Getter
    private int MaxPage;
    @Getter
    private int ItemsCount;

    public WorkoutListService() {
        ItemsPerPage = 10;
        CurrentPage = 1;
        MaxPage = 0;
        ItemsCount = 0;
        currentPageWorkouts = new ArrayList<>();
        LoadAllWorkouts();
    }

    private void LoadAllWorkouts() {
        allWorkouts = FileManager.getAllWorkoutFilenamesSortedByDateDesc();
        ItemsCount = allWorkouts.size();
        MaxPage = (int) Math.ceil((double) ItemsCount / ItemsPerPage);
        if (MaxPage == 0) {
            MaxPage = 1;
        }
        LoadWorkoutsOnCurrentPage();
    }

    private void LoadWorkoutsOnCurrentPage() {
        currentPageWorkouts.clear();
        int startIndex = (CurrentPage - 1) * ItemsPerPage;
        int endIndex = Math.min(startIndex + ItemsPerPage, allWorkouts.size());
        if (startIndex < allWorkouts.size()) {
            for (int i = startIndex; i < endIndex; i++) {
                currentPageWorkouts.add((i + 1) + ". " + allWorkouts.get(i)); // Add index to the filename
            }
        } else {
            currentPageWorkouts = new ArrayList<>();
        }
    }

    public void NextPage() {
        if (CurrentPage < MaxPage) {
            CurrentPage++;
            LoadWorkoutsOnCurrentPage();
        }
    }

    public void PreviousPage() {
        if (CurrentPage > 1) {
            CurrentPage--;
            LoadWorkoutsOnCurrentPage();
        }
    }

    public String getWorkoutFilename(int index) {
        return allWorkouts.get(index-1);
    }
}