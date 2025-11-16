package fr.univ_lyon1.info.m1.cv_search.model;
import java.util.List;

/**
 * Représente une stratégie de sélection de candidats.
 *
 * Cette interface permet de changer facilement la manière
 * dont on décide si un candidat est bon ou non, sans
 * modifier le reste du code.
 */

public interface SelectionStrategy{

    boolean isSelected(Applicant applicant, List<String> requiredSkills);

    double computeScore(Applicant applicant, List<String> requiredSkills);

    String getLabel();

}