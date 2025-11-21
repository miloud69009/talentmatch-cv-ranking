package fr.univ_lyon1.info.m1.cv_search.model;

import java.io.File;

/**
 * Builder reading YAML files in a directory to construct an {@link ApplicantList}.
 */
public final class ApplicantListBuilder {

    private final File directory;

    /**
     * Constructor.
     *
     * @param directory Directory where YAML files for applicants should be searched.
     */
    public ApplicantListBuilder(final File directory) {
        this.directory = directory;
    }

    /**
     * Builds the list of applicants found in the directory.
     *
     * @return the built applicant list
     */
    public ApplicantList build() {
        ApplicantList applicants = new ApplicantList();

        File[] files = directory.listFiles();
        if (files == null) {
            return applicants;
        }

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".yaml")) {
                Applicant applicant = new ApplicantBuilder(file).build();
                applicants.add(applicant);
            }
        }

        return applicants;
    }
}
