package fr.univ_lyon1.info.m1.cv_search.controller;

import fr.univ_lyon1.info.m1.cv_search.model.SearchModel;
import fr.univ_lyon1.info.m1.cv_search.model.StrategyChoice;

/**
 * Controller acting as a bridge between the view and the {@link SearchModel} model.
 * Receives user actions from the view and updates the model accordingly.
 */
public final class CvController {

    /** The search model managed by this controller. */
    private final SearchModel model;

    /**
     * Creates a controller linked to the given model.
     *
     * @param searchModel The search model to control.
     */
    public CvController(final SearchModel searchModel) {
        this.model = searchModel;
    }

    /**
     * Adds a required skill to the model.
     *
     * @param skill Name of the skill to add.
     */
    public void addRequiredSkill(final String skill) {
        model.addRequiredSkill(skill);
    }

    /**
     * Clears all required skills and refreshes the search results.
     */
    public void clearSkills() {
        model.clearRequiredSkills();
        model.search();
    }

    /**
     * Removes a required skill from the model.
     *
     * @param skill Name of the skill to remove.
     */
    public void removeRequiredSkill(final String skill) {
        model.removeRequiredSkill(skill);
    }

    /**
     * Changes the current selection strategy in the model.
     *
     * @param choice The strategy choice selected by the user.
     */
    public void setStrategyChoice(final StrategyChoice choice) {
        model.setStrategyChoice(choice);
    }

    /**
     * Triggers the search in the model using the current strategy and skills.
     */
    public void search() {
        model.search();
    }
}
