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

    private ApplicantList applicants;

    private final List<String> requiredSkills = new ArrayList<>();

    private SelectionStrategy strategy;

    private StrategyChoice currentChoice;

    private final List<ApplicantScore> results = new ArrayList<>();

    private final List<ModelListener> listeners = new ArrayList<>();

    /**
     * Create a model by loading applicants from Yaml files
     * in the given directory, and selecting an initial strategy.
     *
     * @param directory     directory containing the Yaml CVs
     * @param initialChoice initial strategy choice
     */
    public SearchModel(final File directory, final StrategyChoice initialChoice) {
        this.applicants = new ApplicantListBuilder(directory).build();
        // Initialise strategy + currentChoice
        setStrategyChoice(initialChoice);
    }

    /**
     * Add a required skill to the search.
     *
     * @param skill skill to add (ignored if null, empty or already present)
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
     * Remove a required skill from the search.
     *
     * @param skill the skill to remove
     */
    public void removeRequiredSkill(final String skill) {
        if (requiredSkills.remove(skill)) {
            notifyListeners();
        }
    }

    /**
     * Set the current strategy choice and update the concrete strategy.
     *
     * @param choice the selected strategy choice
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
     * Get the current strategy choice.
     *
     * @return currently selected strategy choice
     */
    public StrategyChoice getStrategyChoice() {
        return currentChoice;
    }

    /**
     * Run the search according to the current required skills
     * and strategy, and update the results list.
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
     * Clear all required skills from the search.
     */
    public void clearRequiredSkills() {
        requiredSkills.clear();
        notifyListeners();
    }

    /**
     * Get the list of results as an unmodifiable list.
     *
     * @return the list of applicant scores
     */
    public List<ApplicantScore> getResults() {
        return Collections.unmodifiableList(results);
    }

    /**
     * Get the list of required skills as an unmodifiable list.
     *
     * @return the list of required skills
     */
    public List<String> getRequiredSkills() {
        return Collections.unmodifiableList(requiredSkills);
    }

    /**
     * Add a listener that will be notified when the model changes.
     *
     * @param listener the listener to add
     */
    public void addListener(final ModelListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Notify all registered listeners that the model has changed.
     */
    private void notifyListeners() {
        for (ModelListener l : listeners) {
            l.modelUpdate();
        }
    }
}
