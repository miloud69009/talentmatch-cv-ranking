package fr.univ_lyon1.info.m1.cv_search.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the SelectionStrategy implementations.
 */
public class StrategyTest {

    private Applicant applicant;

    /**
     * Setup a dummy applicant before each test.
     */
    @BeforeEach
    public void setUp() {
        applicant = new Applicant();
        applicant.setName("John Doe");
        applicant.setSkill("Java", 80);
        applicant.setSkill("C++", 40);
        applicant.setSkill("Python", 50);
    }

    /**
     * Verify that AllAtLeastStrategy selects correctly when criteria are met.
     */
    @Test
    public void testAllAtLeastStrategySuccess() {
        SelectionStrategy strategy = new AllAtLeastStrategy(50, "Tout >= 50");
        List<String> skills = Arrays.asList("Java", "Python");

        boolean selected = strategy.isSelected(applicant, skills);

        assertThat(selected, is(true));
    }

    /**
     * Verify that AllAtLeastStrategy rejects when one criteria is missing.
     */
    @Test
    public void testAllAtLeastStrategyFailure() {
        SelectionStrategy strategy = new AllAtLeastStrategy(50, "Tout >= 50");
        List<String> skills = Arrays.asList("Java", "C++"); // C++ is 40

        boolean selected = strategy.isSelected(applicant, skills);

        assertThat(selected, is(false));
    }

    /**
     * Verify that AverageAtLeastStrategy calculates the correct mean.
     */
    @Test
    public void testAverageStrategy() {
        SelectionStrategy strategy = new AverageAtLeastStrategy(50, "Moyenne >= 50");
        List<String> skills = Arrays.asList("Java", "C++"); // (80+40)/2 = 60

        boolean selected = strategy.isSelected(applicant, skills);
        double score = strategy.computeScore(applicant, skills);

        assertThat(selected, is(true));
        assertThat(score, is(60.0));
    }

    /**
     * Verify that the Decorator adds the expected bonus points.
     */
    @Test
    public void testExperienceDecorator() {
        ExperienceEntry exp = new ExperienceEntry("Google", 2010, 2015,
                Collections.singletonList("Java"));
        applicant.addExperience("Google", exp);

        SelectionStrategy base = new AverageAtLeastStrategy(50, "Base");
        // +0.2 per year
        SelectionStrategy decorated = new ExperienceBonusStrategyDecorator(base, 0.2);
        List<String> skills = Collections.singletonList("Java");

        double baseScore = base.computeScore(applicant, skills);
        double finalScore = decorated.computeScore(applicant, skills);

        // Base score for Java is 80.
        // Bonus: 5 years * 0.2 = 1.0. Total should be 81.0
        assertThat(baseScore, is(80.0));
        assertThat(finalScore, is(81.0));
    }

    /**
     * Verify that TolerantStrategy accepts an applicant with one weakness.
     */
    @Test
    public void testTolerantStrategy() {
        // Given: A candidate strong in Java/Python (80) but weak in C++ (10)
        applicant.setSkill("Java", 80);
        applicant.setSkill("Python", 80);
        applicant.setSkill("C++", 10);

        // Strategies
        SelectionStrategy strict = new AllAtLeastStrategy(50, "Strict");
        SelectionStrategy tolerant = new TolerantStrategy(50, "Tolerant");

        List<String> skills = Arrays.asList("Java", "Python", "C++");

        // Then
        // Strict strategy rejects (because of C++)
        assertThat(strict.isSelected(applicant, skills), is(false));

        // Tolerant strategy accepts (only 1 failure allowed)
        assertThat(tolerant.isSelected(applicant, skills), is(true));
    }
}
