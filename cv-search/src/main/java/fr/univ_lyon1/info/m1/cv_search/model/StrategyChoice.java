package fr.univ_lyon1.info.m1.cv_search.model;

/**
 * List of available strategies for resume selection.
 */
public enum StrategyChoice {

    /** Strategy requiring all skills to be at least 50%. */
    ALL_50("All >= 50%"),

    /** Strategy requiring all skills to be at least 60%. */
    ALL_60("All >= 60%"),

    /** Strategy requiring the average score to be at least 50%. */
    AVG_50("Average >= 50%"),

    /** Strategy requiring all skills to be at least 80%. */
    ALL_80("All >= 80%"),

    /** Strategy requiring average >= 50% plus an experience bonus. */
    AVG_50_EXP("Average >= 50% + exp"),

    /** Strategy tolerating one weakness (Ethical choice). */
    TOLERANT_50("Tolerant >= 50% (1 fail allowed)");

    /** The human-readable label for the strategy. */
    private final String label;

    /**
     * Constructor.
     *
     * @param label The label to display for this strategy.
     */
    StrategyChoice(final String label) {
        this.label = label;
    }

    /**
     * Returns the string representation of the strategy choice.
     *
     * @return The label of the strategy.
     */
    @Override
    public String toString() {
        return label;
    }
}
