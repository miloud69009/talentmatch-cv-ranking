package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 * Builder class responsible for reading a YAML file and creating an {@link Applicant} object.
 */
public class ApplicantBuilder {

    /** The YAML file containing the applicant's data. */
    private final File file;

    /**
     * Creates a builder for the specified YAML file.
     *
     * @param f The YAML file describing the applicant.
     */
    public ApplicantBuilder(final File f) {
        this.file = f;
    }

    /**
     * Creates a builder for the specified YAML filename.
     *
     * @param filename The name of the YAML file describing the applicant.
     */
    public ApplicantBuilder(final String filename) {
        this.file = new File(filename);
    }

    /**
     * Builds the applicant instance from the YAML file provided at construction.
     *
     * @return The constructed {@link Applicant} object.
     * @throws Error If the file cannot be found.
     */
    public Applicant build() {
        Applicant a = new Applicant();
        Yaml yaml = new Yaml();
        Map<String, Object> map;
        try {
            map = yaml.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Error(e);
        }

        a.setName((String) map.get("name"));


        @SuppressWarnings("unchecked")
        Map<String, Object> skillsMap = (Map<String, Object>) map.get("skills");
        if (skillsMap != null) {
            for (Map.Entry<String, Object> entry : skillsMap.entrySet()) {
                String skillName = entry.getKey();
                Object valueObj = entry.getValue();

                int value;
                if (valueObj instanceof Integer) {
                    value = (Integer) valueObj;
                } else if (valueObj instanceof Number) {
                    value = ((Number) valueObj).intValue();
                } else {
                    continue;
                }

                a.setSkill(skillName, value);
            }
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> expMap = (Map<String, Object>) map.get("experience");

        if (expMap != null) {
            for (Map.Entry<String, Object> entry : expMap.entrySet()) {
                String company = entry.getKey();

                @SuppressWarnings("unchecked")
                Map<String, Object> expData = (Map<String, Object>) entry.getValue();

                int start = (Integer) expData.get("start");
                int end = (Integer) expData.get("end");

                @SuppressWarnings("unchecked")
                List<String> keywords = (List<String>) expData.get("keywords");

                ExperienceEntry exp = new ExperienceEntry(company, start, end, keywords);
                a.addExperience(company, exp);
            }
        }

        return a;
    }
}
