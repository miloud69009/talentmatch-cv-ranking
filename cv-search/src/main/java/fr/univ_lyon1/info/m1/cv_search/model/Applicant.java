package fr.univ_lyon1.info.m1.cv_search.model;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    public void addExperience(final String company, final ExperienceEntry exp) {
        experiences.put(company, exp);
    }

    public Map<String, ExperienceEntry> getExperiences() {
        return Collections.unmodifiableMap(experiences);
    }


}
