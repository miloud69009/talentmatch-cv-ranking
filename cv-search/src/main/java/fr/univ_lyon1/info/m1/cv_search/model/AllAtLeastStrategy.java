package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Selection strategy where all required skills must be at least a given threshold.
 */
public class AllAtLeastStrategy implements SelectionStrategy {

    private final int threshold;
    private final String label;

    /**
     * Create a strategy requiring all skills to be at least {@code threshold}.
     *
     * @param threshold Minimal score for each required skill
     * @param label     Human-readable label for this strategy
     */
    public AllAtLeastStrategy(final int threshold, final String label) {
        this.threshold = threshold;
        this.label = label;
    }

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

    @Override
    public String getLabel() {
        return label;
    }
}
