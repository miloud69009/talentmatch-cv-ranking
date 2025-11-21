package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Decorator strategy that adds a score bonus based on professional experience.
 * It wraps another {@link SelectionStrategy} and increases the score
 * proportionally to the number of years spent on jobs related to required skills.
 */
public class ExperienceBonusStrategyDecorator implements SelectionStrategy {

    private final SelectionStrategy delegate;
    private final double yearsWeight;

    /**
     * Create an experience bonus decorator.
     *
     * @param delegate    the base strategy to decorate
     * @param yearsWeight multiplier applied per year of relevant experience
     */
    public ExperienceBonusStrategyDecorator(final SelectionStrategy delegate,
                                            final double yearsWeight) {
        this.delegate = delegate;
        this.yearsWeight = yearsWeight;
    }

    /**
     * Uses the wrapped strategy to determine if the applicant is selected.
     *
     * @param applicant       the applicant to evaluate
     * @param requiredSkills  skills required by the search
     * @return true if selected by the underlying strategy
     */
    @Override
    public boolean isSelected(final Applicant applicant,
                              final List<String> requiredSkills) {
        return delegate.isSelected(applicant, requiredSkills);
    }

    /**
     * Computes the score using the wrapped strategy plus experience bonus.
     *
     * @param applicant       the applicant to evaluate
     * @param requiredSkills  skills required by the search
     * @return computed score with experience bonus
     */
    @Override
    public double computeScore(final Applicant applicant,
                               final List<String> requiredSkills) {
        double baseScore = delegate.computeScore(applicant, requiredSkills);
        double bonus = computeExperienceBonus(applicant, requiredSkills);
        return baseScore + bonus;
    }

    /**
     * Compute additional score based on relevant years of experience.
     *
     * @param applicant       applicant whose experience is evaluated
     * @param requiredSkills  search skills used for matching
     * @return total experience bonus
     */
    private double computeExperienceBonus(final Applicant applicant,
                                          final List<String> requiredSkills) {
        double bonus = 0.0;

        for (ExperienceEntry exp : applicant.getExperiences().values()) {
            int years = exp.getDurationYears();

            for (String kw : exp.getKeywords()) {
                if (requiredSkills.contains(kw)) {
                    bonus += years * yearsWeight;
                }
            }
        }

        return bonus;
    }

    /**
     * @return label of the decorated strategy including bonus indicator
     */
    @Override
    public String getLabel() {
        return delegate.getLabel() + " + exp";
    }
}
