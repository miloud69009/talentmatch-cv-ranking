package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.List;

/**
 * Represents a professional experience entry for an applicant.
 * Stores company name, start and end years and associated keywords.
 */
public class ExperienceEntry {

    private final String company;
    private final int start;
    private final int end;
    private final List<String> keywords;

    /**
     * Create an experience entry.
     *
     * @param company  name of the company
     * @param start    starting year of the job
     * @param end      ending year of the job
     * @param keywords list of keywords related to the job
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
     * @return the company name
     */
    public String getCompany() {
        return company;
    }

    /**
     * @return the start year
     */
    public int getStart() {
        return start;
    }

    /**
     * @return the end year
     */
    public int getEnd() {
        return end;
    }

    /**
     * @return list of experience keywords
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Compute the duration of the experience in years.
     *
     * @return duration in years (end - start)
     */
    public int getDurationYears() {
        return end - start;
    }
}
