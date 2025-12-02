package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Selection strategy where all required skills must be at least a given threshold.
 */
public class AllAtLeastStrategy implements SelectionStrategy {

    /** The minimum score required for each skill. */
    private final int threshold;

    /** The human-readable label for this strategy. */
    private final String label;

    /**
     * Create a strategy requiring all skills to be at least {@code threshold}.
     *
     * @param threshold Minimal score for each required skill.
     * @param label     Human-readable label for this strategy.
     */
    public AllAtLeastStrategy(final int threshold, final String label) {
        this.threshold = threshold;
        this.label = label;
    }

    /**
     * Determines if an applicant is selected based on the threshold.
     * The applicant is selected only if every required skill has a score
     * greater than or equal to the threshold.
     *
     * @param applicant      The applicant to evaluate.
     * @param requiredSkills The list of skills required for the position.
     * @return {@code true} if all skills are above the threshold, {@code false} otherwise.
     */
    @Override
    public boolean isSelected(final Applicant applicant,
                              final List<String> requiredSkills) {

        for (String skill : requiredSkills) {
            if (applicant.getSkill(skill) < threshold) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes the average score of the applicant for the required skills.
     *
     * @param applicant      The applicant to evaluate.
     * @param requiredSkills The list of skills required for the position.
     * @return The average score of the applicant on the required skills,
     * or 0.0 if no skills are required.
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
     * Gets the label of this strategy.
     *
     * @return The human-readable label.
     */
    @Override
    public String getLabel() {
        return label;
    }
}
