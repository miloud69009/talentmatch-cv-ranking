package fr.univ_lyon1.info.m1.cv_search.model;

/**
 * Factory responsible for creating {@link SelectionStrategy} instances.
 */
public final class StrategyFactory {

    /** Weight applied to experience when using the experience bonus strategy. */
    private static final double YEARS_WEIGHT = 0.2;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private StrategyFactory() {
        // prevents instantiation
    }

    /**
     * Creates a concrete {@link SelectionStrategy} based on the chosen option.
     *
     * @param choice The selected strategy choice.
     * @return A new SelectionStrategy instance corresponding to the choice.
     * @throws IllegalArgumentException If the provided choice is unknown.
     */
    public static SelectionStrategy create(final StrategyChoice choice) {
        switch (choice) {
            case ALL_50:
                return new AllAtLeastStrategy(50, "All >= 50%");
            case ALL_60:
                return new AllAtLeastStrategy(60, "All >= 60%");
            case ALL_80:
                return new AllAtLeastStrategy(80, "All >= 80%");
            case AVG_50:
                return new AverageAtLeastStrategy(50, "Average >= 50%");
            case AVG_50_EXP:
                return new ExperienceBonusStrategyDecorator(
                        new AverageAtLeastStrategy(50, "Average >= 50%"),
                        YEARS_WEIGHT
                );
            default:
                throw new IllegalArgumentException("Unknown strategy choice: " + choice);
        }
    }
}
