package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * A strategy selecting applicants whose average score on the required skills
 * is above a given threshold.
 */
public class AverageAtLeastStrategy implements SelectionStrategy {

    private final int threshold;
    private final String label;

    /**
     * Constructor.
     *
     * @param threshold minimum average required
     * @param label     label describing the strategy
     */
    public AverageAtLeastStrategy(final int threshold, final String label) {
        this.threshold = threshold;
        this.label = label;
    }

    /**
     * Determines if the applicant passes the selection based on the average.
     *
     * @param applicant      applicant to evaluate
     * @param requiredSkills skills required for the search
     * @return true if the computed average is above threshold
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
     * @param applicant      applicant to evaluate
     * @param requiredSkills list of required skills
     * @return average score
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
     * @return label describing this strategy.
     */
    @Override
    public String getLabel() {
        return label;
    }
}
