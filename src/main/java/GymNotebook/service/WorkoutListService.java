package GymNotebook.service;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutListService {
    private final List<String> allWorkouts;
    @Getter
    private List<String> currentPageWorkouts;

    @Getter
    private final int ItemsPerPage;
    @Getter
    private int CurrentPage;
    @Getter
    private int MaxPage;
    @Getter
    private int ItemsCount;

    public WorkoutListService(List<String> allWorkouts) {
        ItemsPerPage = 10;
        CurrentPage = 1;
        MaxPage = 0;
        ItemsCount = 0;
        currentPageWorkouts = new ArrayList<>();
        this.allWorkouts = allWorkouts;
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
        if (index > 0 && index <= ItemsCount){
            return allWorkouts.get(index-1);
        }
        return "";
    }

    public boolean isNextPage(){
        return CurrentPage < MaxPage;
    }

    public boolean isPreviousPage(){
        return CurrentPage > 1;
    }
}