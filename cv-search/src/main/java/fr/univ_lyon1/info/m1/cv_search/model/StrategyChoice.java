package fr.univ_lyon1.info.m1.cv_search.model;

/**
 * Liste des stratégies disponibles pour la sélection des CV.
 */
public enum StrategyChoice {
    ALL_50("tout ≥ 50%"),
    ALL_60("tout ≥ 60%"),
    AVG_50("moyenne ≥ 50%");

    private final String label;

    StrategyChoice(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
