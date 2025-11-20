package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Une expérience professionnelle d'un candidat.
 */
public class ExperienceEntry {
    private final String company;
    private final int start;
    private final int end;
    private final List<String> keywords;

    public ExperienceEntry(final String company,
                           final int start,
                           final int end,
                           final List<String> keywords) {
        this.company = company;
        this.start = start;
        this.end = end;
        this.keywords = keywords;
    }

    public String getCompany() {
        return company;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Nombre d'années d'expérience sur ce poste.
     */
    public int getDurationYears() {
        return end - start;
    }
}
