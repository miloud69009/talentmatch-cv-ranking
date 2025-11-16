package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;
import java.util.ArrayList;

public class SearchModel {

    private ApplicantList applicants;

    private List<String> requiredSkills = new ArrayList<>();

    private SelectionStrategy strategy;

    private List<ApplicantScore> results = new ArrayList<>();

    private List<ModelListener> listeners = new ArrayList<>();

    public void addRequiredSkill(String skill) {

    }

    public void removeRequiredSkill(String skill) {

    }

    public void setStrategy(SelectionStrategy strategy) {

    }

    public void search() {

    }

    public List<ApplicantScore> getResults() {
        return results;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }


    public void notifyListeners() {
        for (ModelListener l : listeners) {
            l.modelUpdate();

        }
    }
}