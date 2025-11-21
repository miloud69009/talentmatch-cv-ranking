package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Représente une stratégie de sélection de candidats.
 *
 * Une stratégie évalue un candidat en fonction des compétences
 * requises, calcule un score de pertinence et indique si le
 * candidat doit être retenu ou non.
 */
public interface SelectionStrategy {

    /**
     * Indique si un candidat est sélectionné selon la stratégie.
     *
     * @param applicant       le candidat évalué
     * @param requiredSkills  la liste des compétences recherchées
     * @return true si le candidat correspond aux critères, false sinon
     */
    boolean isSelected(Applicant applicant, List<String> requiredSkills);

    /**
     * Calcule un score de pertinence pour un candidat donné.
     *
     * @param applicant       le candidat évalué
     * @param requiredSkills  la liste des compétences recherchées
     * @return un score entre 0 et 100
     */
    double computeScore(Applicant applicant, List<String> requiredSkills);

    /**
     * @return le nom lisible de la stratégie (affiché dans l'interface)
     */
    String getLabel();
}
