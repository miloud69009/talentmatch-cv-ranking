package fr.univ_lyon1.info.m1.cv_search.model;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for the StrategyFactory class.
 */
public class StrategyFactoryTest {

    /**
     * Verifies that the factory creates the correct strategy instance for each choice.
     */
    @Test
    public void testFactoryCreatesCorrectStrategies() {
        // Test AllAtLeastStrategy variants
        SelectionStrategy s1 = StrategyFactory.create(StrategyChoice.ALL_50);
        assertThat(s1, instanceOf(AllAtLeastStrategy.class));
        assertThat(s1.getLabel(), is("All >= 50%"));

        SelectionStrategy s2 = StrategyFactory.create(StrategyChoice.ALL_60);
        assertThat(s2, instanceOf(AllAtLeastStrategy.class));

        SelectionStrategy s3 = StrategyFactory.create(StrategyChoice.ALL_80);
        assertThat(s3, instanceOf(AllAtLeastStrategy.class));

        // Test AverageAtLeastStrategy
        SelectionStrategy s4 = StrategyFactory.create(StrategyChoice.AVG_50);
        assertThat(s4, instanceOf(AverageAtLeastStrategy.class));

        // Test Decorator
        SelectionStrategy s5 = StrategyFactory.create(StrategyChoice.AVG_50_EXP);
        assertThat(s5, instanceOf(ExperienceBonusStrategyDecorator.class));
    }
}
