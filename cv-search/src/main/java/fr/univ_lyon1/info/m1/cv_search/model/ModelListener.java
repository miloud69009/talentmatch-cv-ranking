package fr.univ_lyon1.info.m1.cv_search.model;

/**
 * Listener interface used by the model to notify views of updates.
 */
public interface ModelListener {

    /**
     * Called when the model has changed and the view must refresh.
     */
    void modelUpdate();
}
