package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Represents a candidate selection strategy.
 * A strategy evaluates an applicant based on required skills, computes a
 * relevance score, and indicates whether the applicant should be selected or not.
 */
public interface SelectionStrategy {

    /**
     * Indicates if an applicant is selected according to the strategy.
     *
     * @param applicant      The applicant to evaluate.
     * @param requiredSkills The list of required skills.
     * @return {@code true} if the applicant matches the criteria, {@code false} otherwise.
     */
    boolean isSelected(Applicant applicant, List<String> requiredSkills);

    /**
     * Computes a relevance score for a given applicant.
     *
     * @param applicant      The applicant to evaluate.
     * @param requiredSkills The list of required skills.
     * @return A score representing the relevance of the applicant.
     */
    double computeScore(Applicant applicant, List<String> requiredSkills);

    /**
     * Gets the human-readable label of the strategy.
     *
     * @return The label of the strategy (displayed in the user interface).
     */
    String getLabel();
}
