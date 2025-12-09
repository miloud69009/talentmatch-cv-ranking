package fr.univ_lyon1.info.m1.cv_search.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

import fr.univ_lyon1.info.m1.cv_search.model.SearchModel;
import fr.univ_lyon1.info.m1.cv_search.model.StrategyChoice;
import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * Tests for the CvController.
 */
public class CvControllerTest {

    /**
     * Verifies that controller actions (add/clear skills) update the model correctly.
     */
    @Test
    public void testControllerModifiesModel() {
        // Given
        SearchModel model = new SearchModel(new File("."), StrategyChoice.ALL_50);
        CvController controller = new CvController(model);

        // When
        controller.addRequiredSkill("PHP");

        // Then
        assertThat(model.getRequiredSkills(), contains("PHP"));

        // When
        controller.clearSkills();

        // Then
        assertThat(model.getRequiredSkills(), is(empty()));
    }

    /**
     * Verifies that removing a skill via the controller updates the model.
     */
    @Test
    public void testControllerRemovesSkill() {
        // Given
        SearchModel model = new SearchModel(new File("."), StrategyChoice.ALL_50);
        CvController controller = new CvController(model);

        controller.addRequiredSkill("Java");

        // When
        controller.removeRequiredSkill("Java");

        // Then
        assertThat(model.getRequiredSkills(), is(empty()));
    }
}
