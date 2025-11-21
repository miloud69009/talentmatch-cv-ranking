package fr.univ_lyon1.info.m1.cv_search.model;

/**
 * Pair containing an applicant and their computed score.
 */
public class ApplicantScore implements Comparable<ApplicantScore> {

    private final Applicant applicant;
    private final double score;

    /**
     * Constructor.
     *
     * @param applicant the applicant
     * @param score     computed score
     */
    public ApplicantScore(final Applicant applicant, final double score) {
        this.applicant = applicant;
        this.score = score;
    }

    /**
     * @return the applicant.
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * @return the computed score.
     */
    public double getScore() {
        return score;
    }

    /**
     * Sort scores in descending order (highest score first).
     *
     * @param other another ApplicantScore
     * @return comparison result
     */
    @Override
    public int compareTo(final ApplicantScore other) {
        return Double.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return applicant.getName() + " : " + score;
    }
}
