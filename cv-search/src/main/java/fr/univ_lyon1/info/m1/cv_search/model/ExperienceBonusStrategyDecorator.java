package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Décorateur de stratégie qui ajoute un bonus basé sur l'expérience pro.
 */
public class ExperienceBonusStrategyDecorator implements SelectionStrategy {

    private final SelectionStrategy delegate;
    private final double yearsWeight;

    public ExperienceBonusStrategyDecorator(final SelectionStrategy delegate,
                                            final double yearsWeight) {
        this.delegate = delegate;
        this.yearsWeight = yearsWeight;
    }

    @Override
    public boolean isSelected(final Applicant applicant,
                              final List<String> requiredSkills) {
        // On garde la même logique de sélection que la stratégie décorée
        return delegate.isSelected(applicant, requiredSkills);
    }

    @Override
    public double computeScore(final Applicant applicant,
                               final List<String> requiredSkills) {
        double baseScore = delegate.computeScore(applicant, requiredSkills);
        double bonus = computeExperienceBonus(applicant, requiredSkills);
        return baseScore + bonus;
    }

    private double computeExperienceBonus(final Applicant applicant,
                                          final List<String> requiredSkills) {
        double bonus = 0.0;

        // ✅ Correction ici : on parcourt les valeurs de la map
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

    @Override
    public String getLabel() {
        return delegate.getLabel() + " + exp";
    }
}
