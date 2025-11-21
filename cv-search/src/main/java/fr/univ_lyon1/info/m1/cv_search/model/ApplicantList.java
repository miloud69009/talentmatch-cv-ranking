package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper around {@link List} of {@link Applicant}.
 * Provides iteration utilities for the list of applicants.
 */
public class ApplicantList implements Iterable<Applicant> {

    private List<Applicant> list = new ArrayList<>();

    /**
     * Adds an applicant to the list.
     *
     * @param applicant the applicant to add
     */
    void add(final Applicant applicant) {
        list.add(applicant);
    }

    /**
     * Returns the number of applicants in the list.
     *
     * @return size of the list
     */
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<Applicant> iterator() {
        return list.iterator();
    }

    /**
     * Clears the list of applicants.
     */
    public void clear() {
        list.clear();
    }

    /**
     * Replaces the content of this list with the content of another list.
     *
     * @param other the applicant list used as replacement
     */
    public void setList(final ApplicantList other) {
        this.list = other.list;
    }
}
