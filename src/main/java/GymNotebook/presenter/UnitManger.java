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

    public enum WeightUnits{
        kg,
        lbs
    }


    public static final double KgInLbs = 2.2;
    public static double KGtoLBS(double KG){
        return RoundToDecimalPlaces(KG * KgInLbs,2);
    }
    public static double LBStoKG(double LBS){
        return RoundToDecimalPlaces(LBS / KgInLbs,2);
    }

    public static double RoundToDecimalPlaces(double value, int order){
        int multiplier  = (int) Math.pow(10,order);
        return (double)Math.round(value * multiplier) / multiplier ;
    }

    public UnitManger(){
        unitChangeListeners = new ArrayList<>();
        units = WeightUnits.kg;
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
            listener.Notify();
        }
    }

    public void Subscribe(UnitChangeListener listener){
        unitChangeListeners.add(listener);
    }

}
