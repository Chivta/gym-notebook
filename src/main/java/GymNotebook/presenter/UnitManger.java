package GymNotebook.presenter;

import GymNotebook.model.UnitChangeListener;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class UnitManger {
    private final List<UnitChangeListener> unitChangeListeners;

    @Getter
    @Setter
    private WeightUnits units;

    public static enum WeightUnits{
        kg,
        lbs
    }


    public static final double KgInLbs = 2.2;
    public static double KGtoLBS(double KG){
        return KG * KgInLbs;
    }
    public static double LBStoKG(double LBS){
        return LBS / KgInLbs;
    }

    public UnitManger(){
        unitChangeListeners = new ArrayList<>();
    }

    public void ChangeUnits(){
        switch (units){
            case lbs:
                units = WeightUnits.kg;
                break;
            case kg:
                units = WeightUnits.lbs;
                break;
        }
        NotifyAllSubscribers();
    }

    private void NotifyAllSubscribers(){
        for(var listener : unitChangeListeners){
            listener.Notify(units);
        }
    }

    public void Subscribe(UnitChangeListener listener){
        unitChangeListeners.add(listener);
    }

}
