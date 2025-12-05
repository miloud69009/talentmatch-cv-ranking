package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * A more ethical selection strategy that tolerates one weakness.
 * The applicant is selected if they fail at most one required skill check.
 */
public class TolerantStrategy implements SelectionStrategy {

    /** The minimum score required for skills. */
    private final int threshold;

    /** The human-readable label. */
    private final String label;

    /**
     * Constructor.
     *
     * @param threshold The score threshold for skills.
     * @param label     The display label.
     */
    public TolerantStrategy(final int threshold, final String label) {
        this.threshold = threshold;
        this.label = label;
    }

    /**
     * Selects the applicant if they have at most one skill below the threshold.
     *
     * @param applicant      The applicant to evaluate.
     * @param requiredSkills The list of required skills.
     * @return {@code true} if the applicant has 0 or 1 weak skill, {@code false} otherwise.
     */
    @Override
    public boolean isSelected(final Applicant applicant,
                              final List<String> requiredSkills) {
        if (requiredSkills.isEmpty()) {
            return true;
        }

        int failures = 0;
        for (String skill : requiredSkills) {
            if (applicant.getSkill(skill) < threshold) {
                failures++;
            }
        }

        // Ethical rule: We tolerate exactly one failure (weakness)
        return failures <= 1;
    }

    /**
     * Computes the average score.
     *
     * @param applicant      The applicant to evaluate.
     * @param requiredSkills The list of required skills.
     * @return The average score.
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
     * @return The label.
     */
    @Override
    public String getLabel() {
        return label;
    }
}
