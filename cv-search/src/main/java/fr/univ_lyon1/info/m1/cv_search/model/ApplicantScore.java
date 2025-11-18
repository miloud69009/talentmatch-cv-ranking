package fr.univ_lyon1.info.m1.cv_search.model;

public class ApplicantScore implements Comparable<ApplicantScore> {

    private final Applicant applicant;
    private final double score;

    public ApplicantScore(Applicant applicant, double score) {
        this.applicant = applicant;
        this.score = score;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(ApplicantScore other) {
        return Double.compare(other.score, this.score);    }

    @Override
    public String toString() {
        return applicant.getName() + " : " + score;
    }
}