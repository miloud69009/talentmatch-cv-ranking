package fr.univ_lyon1.info.m1.cv_search.model;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Applicant, i.e. person having a name and a list of (skill, score) pairs.
 */
public class Applicant {
    private Map<String, Integer> skills = new HashMap<>();
    private String name;

    private Map<String, ExperienceEntry> experiences = new HashMap<>();

    /**
     * Get the score for a given skill.
     */
    public int getSkill(final String skillName) {
        return skills.getOrDefault(skillName, 0);
    }

    /**
     * Assign score {@param value} to skill {@param skillName} for the current applicant.
     */
    public void setSkill(final String skillName, final int value) {
        skills.put(skillName, value);
    }

    /**
     * @return the applicant's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the applicant's name.
     *
     * @param name the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Add a professional experience for this applicant.
     *
     * @param company the company name (used as key)
     * @param exp     the experience entry
     */
    public void addExperience(final String company, final ExperienceEntry exp) {
        experiences.put(company, exp);
    }

    /**
     * @return an unmodifiable view of the applicant's experiences
     */
    public Map<String, ExperienceEntry> getExperiences() {
        return Collections.unmodifiableMap(experiences);
    }


}
