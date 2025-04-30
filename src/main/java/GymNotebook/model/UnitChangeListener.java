package GymNotebook.model;
import GymNotebook.presenter.UnitManger.WeightUnits;

public interface UnitChangeListener {
    void Notify(WeightUnits newUnits);
}
