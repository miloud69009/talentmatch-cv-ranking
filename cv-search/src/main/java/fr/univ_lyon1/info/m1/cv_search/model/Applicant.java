package fr.univ_lyon1.info.m1.cv_search.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Applicant, i.e. person having a name and a list of (skill, score) pairs.
 */
public class Applicant {

    /** Map containing the skills and their associated scores. */
    private final Map<String, Integer> skills = new HashMap<>();

    /** Map containing professional experiences, keyed by company name. */
    private final Map<String, ExperienceEntry> experiences = new HashMap<>();

    /** The applicant's name. */
    private String name;

    /**
     * Gets the score for a given skill.
     *
     * @param skillName The name of the skill to query.
     * @return The score of the skill, or 0 if the skill is not found.
     */
    public int getSkill(final String skillName) {
        return skills.getOrDefault(skillName, 0);
    }

    /**
     * Assigns a score to a skill for the current applicant.
     *
     * @param skillName The name of the skill to set.
     * @param value     The score value.
     */
    public void setSkill(final String skillName, final int value) {
        skills.put(skillName, value);
    }

    /**
     * Gets the applicant's name.
     *
     * @return The name of the applicant.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the applicant's name.
     *
     * @param name The new name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Adds a professional experience for this applicant.
     *
     * @param company The company name (used as key).
     * @param exp     The experience entry details.
     */
    public void addExperience(final String company, final ExperienceEntry exp) {
        experiences.put(company, exp);
    }

    /**
     * Gets an unmodifiable view of the applicant's experiences.
     *
     * @return A map of experiences where the key is the company name.
     */
    public Map<String, ExperienceEntry> getExperiences() {
        return Collections.unmodifiableMap(experiences);
    }
}
