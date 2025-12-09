package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Represents a professional experience entry for an applicant.
 * Stores company name, start and end years and associated keywords.
 */
public class ExperienceEntry {

    /** The name of the company. */
    private final String company;

    /** The start year of the experience. */
    private final int start;

    /** The end year of the experience. */
    private final int end;

    /** The list of keywords associated with this experience. */
    private final List<String> keywords;

    /**
     * Creates an experience entry.
     *
     * @param company  The name of the company.
     * @param start    The starting year of the job.
     * @param end      The ending year of the job.
     * @param keywords The list of keywords related to the job.
     */
    public ExperienceEntry(final String company,
                           final int start,
                           final int end,
                           final List<String> keywords) {
        this.company = company;
        this.start = start;
        this.end = end;
        this.keywords = keywords;
    }

    /**
     * Gets the company name.
     *
     * @return The company name.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Gets the start year.
     *
     * @return The start year.
     */
    public int getStart() {
        return start;
    }

    /**
     * Gets the end year.
     *
     * @return The end year.
     */
    public int getEnd() {
        return end;
    }

    /**
     * Gets the list of experience keywords.
     *
     * @return The list of keywords.
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Computes the duration of the experience in years.
     *
     * @return The duration in years (calculated as end - start).
     */
    public int getDurationYears() {
        return end - start;
    }
}
