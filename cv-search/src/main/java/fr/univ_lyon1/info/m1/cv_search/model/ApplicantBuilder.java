package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


import org.yaml.snakeyaml.Yaml;

/**
 * Builder reading a Yaml file to build an Applicant object.
 */
public class ApplicantBuilder {

    private File file;

    /**
     * @param f Yaml file describing the applicant.
     */
    public ApplicantBuilder(final File f) {
        this.file = f;
    }

    /**
     * @param filename Name of the Yaml file describing the applicant.
     */
    public ApplicantBuilder(final String filename) {
        this.file = new File(filename);
    }

    /**
     * Build the applicant from the Yaml file provided to the constructor.
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

        // Cast may fail if the Yaml is incorrect. Ideally we should provide
        // clean error messages.
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

                ExperienceEntry exp =
                        new ExperienceEntry(company, start, end, keywords);

                // ✔️ addExperience(company, exp)
                a.addExperience(company, exp);
            }
        }

        return a;
    }
}
