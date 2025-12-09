package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * A strategy selecting applicants whose average score on the required skills
 * is above a given threshold.
 */
public class AverageAtLeastStrategy implements SelectionStrategy {

    /** The minimum average score required. */
    private final int threshold;

    /** The label describing the strategy. */
    private final String label;

    /**
     * Constructor.
     *
     * @param threshold The minimum average required.
     * @param label     The label describing the strategy.
     */
    public AverageAtLeastStrategy(final int threshold, final String label) {
        this.threshold = threshold;
        this.label = label;
    }

    /**
     * Determines if the applicant passes the selection based on the average.
     *
     * @param applicant      The applicant to evaluate.
     * @param requiredSkills The skills required for the search.
     * @return {@code true} if the computed average is greater than or equal to the threshold,
     * or {@code true} if no skills are required.
     */
    @Override
    public boolean isSelected(final Applicant applicant,
                              final List<String> requiredSkills) {

        if (requiredSkills.isEmpty()) {
            return true;
        }

        double avg = computeScore(applicant, requiredSkills);
        return avg >= threshold;
    }

    /**
     * Computes the average score of the applicant over required skills.
     *
     * @param applicant      The applicant to evaluate.
     * @param requiredSkills The list of required skills.
     * @return The average score, or 0.0 if no skills are required.
     */
    @Override
    public double computeScore(final Applicant applicant,
                               final List<String> requiredSkills) {

        if (requiredSkills.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (String skill : requiredSkills) {
            sum += applicant.getSkill(skill);
        }

        return sum / (double) requiredSkills.size();
    }

    /**
     * Gets the label describing this strategy.
     *
     * @return The label describing this strategy.
     */
    @Override
    public String getLabel() {
        return label;
    }
}
