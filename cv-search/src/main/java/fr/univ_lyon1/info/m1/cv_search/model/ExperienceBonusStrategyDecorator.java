package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Decorator strategy that adds a score bonus based on professional experience.
 * It wraps another {@link SelectionStrategy} and increases the score
 * proportionally to the number of years spent on jobs related to required skills.
 */
public class ExperienceBonusStrategyDecorator implements SelectionStrategy {

    /** The underlying strategy being decorated. */
    private final SelectionStrategy delegate;

    /** The multiplier applied per year of relevant experience. */
    private final double yearsWeight;

    /**
     * Creates an experience bonus decorator.
     *
     * @param delegate    The base strategy to decorate.
     * @param yearsWeight The multiplier applied per year of relevant experience.
     */
    public ExperienceBonusStrategyDecorator(final SelectionStrategy delegate,
                                            final double yearsWeight) {
        this.delegate = delegate;
        this.yearsWeight = yearsWeight;
    }

    /**
     * Uses the wrapped strategy to determine if the applicant is selected.
     *
     * @param applicant       The applicant to evaluate.
     * @param requiredSkills  The skills required by the search.
     * @return {@code true} if selected by the underlying strategy, {@code false} otherwise.
     */
    @Override
    public boolean isSelected(final Applicant applicant,
                              final List<String> requiredSkills) {
        return delegate.isSelected(applicant, requiredSkills);
    }

    /**
     * Computes the score using the wrapped strategy plus experience bonus.
     *
     * @param applicant       The applicant to evaluate.
     * @param requiredSkills  The skills required by the search.
     * @return The computed score combining the base strategy score and the experience bonus.
     */
    @Override
    public double computeScore(final Applicant applicant,
                               final List<String> requiredSkills) {
        double baseScore = delegate.computeScore(applicant, requiredSkills);
        double bonus = computeExperienceBonus(applicant, requiredSkills);
        return baseScore + bonus;
    }

    /**
     * Computes additional score based on relevant years of experience.
     *
     * @param applicant       The applicant whose experience is evaluated.
     * @param requiredSkills  The search skills used for matching.
     * @return The total experience bonus.
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
     * Gets the label of the decorated strategy including the bonus indicator.
     *
     * @return The label of the decorated strategy.
     */
    @Override
    public String getLabel() {
        return delegate.getLabel() + " + exp";
    }
}
