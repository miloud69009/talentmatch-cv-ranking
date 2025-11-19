package fr.univ_lyon1.info.m1.cv_search.model;
import fr.univ_lyon1.info.m1.cv_search.model.StrategyChoice;


public class StrategyFactory {


    private StrategyFactory() {

    }
    public static SelectionStrategy create(final StrategyChoice choice) {
        return switch (choice) {
            case ALL_50 -> new AllAtLeastStrategy(50, "tout ≥ 50%");
            case ALL_60 -> new AllAtLeastStrategy(60, "tout ≥ 60%");
            case ALL_80 -> new AllAtLeastStrategy(80, "tout ≥ 80%");
            case AVG_50 -> new AverageAtLeastStrategy(50, "moyenne ≥ 50%");
            };

    }

}