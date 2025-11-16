package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

public class AverageAtLeastStrategy implements SelectionStrategy {

    private final int threshold;
    private final String label;

    public AverageAtLeastStrategy(int threshold, String label) {
        this.threshold = threshold;
        this.label = label;
    }

    @Override
    public boolean isSelected(Applicant applicant, List<String> requiredSkills) {

    if (requiredSkills.isEmpty()) {
        return true;
    }

    double avg = computeScore (applicant, requiredSkills);
    return avg >= threshold;

    }

    @Override
    public double computeScore(Applicant applicant, List<String> requiredSkills){
        if (requiredSkills.isEmpty()) {
            return 0.0;
        }
        int somme = 0;
        for (String skill : requiredSkills) {
            somme += applicant.getSkill(skill);
        }
        return somme / (double) requiredSkills.size();
    }

    @Override
    public String getLabel() {
        return label;
    }




}