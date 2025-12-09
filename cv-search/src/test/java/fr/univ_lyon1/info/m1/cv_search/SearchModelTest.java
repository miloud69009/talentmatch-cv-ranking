package fr.univ_lyon1.info.m1.cv_search.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests for the SearchModel class (skills management and sorting).
 */
public class SearchModelTest {

    /**
     * Verify adding and removing skills from the model.
     */
    @Test
    public void testAddAndRemoveSkills() {
        // Given
        SearchModel model = new SearchModel(new File("."), StrategyChoice.ALL_50);

        // When
        model.addRequiredSkill("Java");

        // Then
        assertThat(model.getRequiredSkills(), hasSize(1));
        assertThat(model.getRequiredSkills(), contains("Java"));

        // When
        model.removeRequiredSkill("Java");

        // Then
        assertThat(model.getRequiredSkills(), is(empty()));
    }

    /**
     * Verifies that the search updates the results list correctly.
     */
    @Test
    public void testSearchUpdatesResults() {
        // Given
        SearchModel model = new SearchModel(new File("."), StrategyChoice.ALL_50);


        model.addRequiredSkill("c");
        // When
        model.search();

        // Then
        assertThat(model.getResults().isEmpty(), is(false));

        assertThat(model.getResults().get(0).getScore() >= 50, is(true));
    }

    /**
     * Verify that ApplicantScore comparison logic provides descending sort.
     */
    @Test
    public void testSortingLogic() {
        // Given
        Applicant a1 = new Applicant();
        a1.setName("Boss");
        ApplicantScore scoreHigh = new ApplicantScore(a1, 90.0);

        Applicant a2 = new Applicant();
        a2.setName("Junior");
        ApplicantScore scoreLow = new ApplicantScore(a2, 10.0);

        // When
        int comparison = scoreHigh.compareTo(scoreLow);

        // Then
        boolean isSortedCorrectly = comparison < 0;
        assertThat(isSortedCorrectly, is(true));
    }
}
