package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Collections;

public class SearchModel {

    private ApplicantList applicants;

    private List<String> requiredSkills = new ArrayList<>();

    private SelectionStrategy strategy;

    private StrategyChoice currentChoice;


    private List<ApplicantScore> results = new ArrayList<>();

    private List<ModelListener> listeners = new ArrayList<>();

  /*  public SearchModel(File directory, SelectionStrategy initialStrategy) {

        this.applicants= new ApplicantListBuilder(directory).build();
        this.strategy = initialStrategy;


    }*/

    public SearchModel(final File directory, final StrategyChoice initialChoice) {
        this.applicants = new ApplicantListBuilder(directory).build();
        setStrategyChoice(initialChoice);   // initialise strategy + currentChoice
    }

    public void addRequiredSkill(String skill) {

        if(skill == null) {
            return;
        }
        String trimmed = skill.strip();
        if(trimmed.isEmpty()) {
            return;
        }
        if (!requiredSkills.contains(trimmed)) {
            requiredSkills.add(trimmed);
            notifyListeners();
        }

    }

    public void removeRequiredSkill(String skill) {
        if(requiredSkills.remove(skill)) {
            notifyListeners();
        }
    }

    /*public void setStrategy(SelectionStrategy strategy) {

        if(strategy == null) {
            return;
        }
        this.strategy = strategy;
        notifyListeners();

    }*/
   public void setStrategyChoice(final StrategyChoice choice) {
        if (choice == null) {
            return;
        }
        currentChoice = choice;

        strategy = StrategyFactory.create(choice);
        notifyListeners();
    }




    public StrategyChoice getStrategyChoice() {
        return currentChoice;
    }



    public void search() {
        results.clear();

        if (strategy == null) {
            return;
        }

        for (Applicant a : applicants) {
            if(strategy.isSelected(a, requiredSkills)) {
                double score = strategy.computeScore(a, requiredSkills);

                ApplicantScore as = new ApplicantScore(a, score);

                results.add(as);
            }
        }
        Collections.sort(results);

        notifyListeners();
    }

    public List<ApplicantScore> getResults() {
        return Collections.unmodifiableList (results);
    }

    public List<String> getRequiredSkills() {
        return Collections.unmodifiableList(requiredSkills);    }

    public void addListener(ModelListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }    }


    private void notifyListeners() {
        for (ModelListener l : listeners) {
            l.modelUpdate();

        }
    }
}