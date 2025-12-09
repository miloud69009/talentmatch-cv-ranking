package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model of the CV search application.
 *
 * It stores the list of applicants, the required skills,
 * the current selection strategy and the search results.
 * It also implements the observable part of the MVC pattern
 * using {@link ModelListener}.
 */
public class SearchModel {

    /** The list of all available applicants. */
    private final ApplicantList applicants;

    /** The list of skills currently required for the search. */
    private final List<String> requiredSkills = new ArrayList<>();

    /** The list of search results (applicant + score). */
    private final List<ApplicantScore> results = new ArrayList<>();

    /** The list of listeners observing this model. */
    private final List<ModelListener> listeners = new ArrayList<>();

    /** The current selection strategy. */
    private SelectionStrategy strategy;

    /** The choice representing the current strategy. */
    private StrategyChoice currentChoice;

    /**
     * Creates a model by loading applicants from YAML files
     * in the given directory, and selecting an initial strategy.
     *
     * @param directory     Directory containing the YAML CVs.
     * @param initialChoice Initial strategy choice.
     */
    public SearchModel(final File directory, final StrategyChoice initialChoice) {
        this.applicants = new ApplicantListBuilder(directory).build();
        setStrategyChoice(initialChoice);
    }

    /**
     * Adds a required skill to the search.
     *
     * @param skill Skill to add (ignored if null, empty or already present).
     */
    public void addRequiredSkill(final String skill) {
        if (skill == null) {
            return;
        }
        String trimmed = skill.strip();
        if (trimmed.isEmpty()) {
            return;
        }
        if (!requiredSkills.contains(trimmed)) {
            requiredSkills.add(trimmed);
            notifyListeners();
        }
    }

    /**
     * Removes a required skill from the search.
     *
     * @param skill The skill to remove.
     */
    public void removeRequiredSkill(final String skill) {
        if (requiredSkills.remove(skill)) {
            notifyListeners();
        }
    }

    /**
     * Sets the current strategy choice and updates the concrete strategy.
     *
     * @param choice The selected strategy choice.
     */
    public void setStrategyChoice(final StrategyChoice choice) {
        if (choice == null) {
            return;
        }
        currentChoice = choice;
        strategy = StrategyFactory.create(choice);
        notifyListeners();
    }

    /**
     * Gets the current strategy choice.
     *
     * @return The currently selected strategy choice.
     */
    public StrategyChoice getStrategyChoice() {
        return currentChoice;
    }

    /**
     * Runs the search according to the current required skills
     * and strategy, updates the results list, and sorts them.
     */
    public void search() {
        results.clear();

        if (strategy == null) {
            return;
        }

        for (Applicant a : applicants) {
            if (strategy.isSelected(a, requiredSkills)) {
                double score = strategy.computeScore(a, requiredSkills);
                ApplicantScore as = new ApplicantScore(a, score);
                results.add(as);
            }
        }

        Collections.sort(results);
        notifyListeners();
    }

    /**
     * Clears all required skills from the search.
     */
    public void clearRequiredSkills() {
        requiredSkills.clear();
        notifyListeners();
    }

    /**
     * Gets the list of results as an unmodifiable list.
     *
     * @return The list of applicant scores.
     */
    public List<ApplicantScore> getResults() {
        return Collections.unmodifiableList(results);
    }

    /**
     * Gets the list of required skills as an unmodifiable list.
     *
     * @return The list of required skills.
     */
    public List<String> getRequiredSkills() {
        return Collections.unmodifiableList(requiredSkills);
    }

    /**
     * Adds a listener that will be notified when the model changes.
     *
     * @param listener The listener to add.
     */
    public void addListener(final ModelListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Notifies all registered listeners that the model has changed.
     */
    private void notifyListeners() {
        for (ModelListener l : listeners) {
            l.modelUpdate();
        }
    }
}
