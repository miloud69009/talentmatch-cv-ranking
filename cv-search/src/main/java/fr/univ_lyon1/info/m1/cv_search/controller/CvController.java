package fr.univ_lyon1.info.m1.cv_search.controller;

import fr.univ_lyon1.info.m1.cv_search.model.SearchModel;
import fr.univ_lyon1.info.m1.cv_search.model.StrategyChoice;

/**
 * Controller between the view and the {@link SearchModel}.
 * Receives user actions from the view and updates the model.
 */
public final class CvController {
    private final SearchModel model;

    /**
     * Create a controller bound to the given model.
     *
     * @param model The search model to control.
     */
    public CvController(final SearchModel model) {
        this.model = model;
    }

    /**
     * Add a required skill to the model.
     *
     * @param skill Name of the skill to add.
     */
    public void addRequiredSkill(final String skill) {
        model.addRequiredSkill(skill);
    }

    /**
     * Clear all required skills and refresh the search results.
     */
    public void clearSkills() {
        model.clearRequiredSkills();
        model.search();
    }

    /**
     * Remove a required skill from the model.
     *
     * @param skill Name of the skill to remove.
     */
    public void removeRequiredSkill(final String skill) {
        model.removeRequiredSkill(skill);
    }

    /**
     * Change the current selection strategy in the model.
     *
     * @param choice Strategy choice selected by the user.
     */
    public void setStrategyChoice(final StrategyChoice choice) {
        model.setStrategyChoice(choice);
    }

    /**
     * Trigger the search in the model using current strategy and skills.
     */
    public void search() {
        model.search();
    }
}
