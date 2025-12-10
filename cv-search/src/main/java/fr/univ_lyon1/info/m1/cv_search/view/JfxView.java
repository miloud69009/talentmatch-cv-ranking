package fr.univ_lyon1.info.m1.cv_search.view;

import fr.univ_lyon1.info.m1.cv_search.controller.CvController;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantScore;
import fr.univ_lyon1.info.m1.cv_search.model.ModelListener;
import fr.univ_lyon1.info.m1.cv_search.model.SearchModel;
import fr.univ_lyon1.info.m1.cv_search.model.StrategyChoice;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main view of the application, implemented using JavaFX.
 * It observes the {@link SearchModel} to update the display automatically.
 */
public class JfxView implements ModelListener {

    /** The search model to observe. */
    private final SearchModel model;

    /** The controller used to propagate user actions. */
    private CvController controller;

    /** Container for the list of skills currently being searched. */
    private HBox searchSkillsBox;

    /** Container for the search results. */
    private VBox resultBox;

    /** Dropdown menu to select the search strategy. */
    private ComboBox<StrategyChoice> strategyBox;

    /** Input field for adding new skills. */
    private TextField skillTextField;

    /**
     * Creates the main view of the application.
     *
     * @param stage  The primary stage of the JavaFX application.
     * @param width  The window width.
     * @param height The window height.
     * @param model  The search model to observe.
     */
    public JfxView(final Stage stage,
                   final int width,
                   final int height,
                   final SearchModel model) {
        this.model = model;
        this.model.addListener(this);

        // Name of window
        stage.setTitle("Search for CV");

        VBox root = new VBox();

        Node newSkillBox = createNewSkillWidget();
        root.getChildren().add(newSkillBox);

        Node searchSkillsBoxNode = createCurrentSearchSkillsWidget();
        root.getChildren().add(searchSkillsBoxNode);

        Node strategy = createStrategyWidget();
        root.getChildren().add(strategy);

        Node search = createSearchWidget();
        root.getChildren().add(search);

        Node resultBoxNode = createResultsWidget();
        root.getChildren().add(resultBoxNode);

        // Everything's ready: add it to the scene and display it
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the controller for this view.
     *
     * @param controller The controller to use.
     */
    public void setController(final CvController controller) {
        this.controller = controller;
    }

    /**
     * Creates the widget (text field + buttons) to enter a new skill.
     *
     * @return The Node containing the skill creation widgets.
     */
    private Node createNewSkillWidget() {
        HBox newSkillBox = new HBox();
        Label labelSkill = new Label("Skill:");
        TextField textField = new TextField();
        skillTextField = textField;

        Button submitButton = new Button("Add skill");
        Button clearButton = new Button("Clear");

        clearButton.setOnAction(event -> {
            if (controller != null) {
                controller.clearSkills();
            }
        });

        newSkillBox.getChildren().addAll(labelSkill, textField,
                submitButton, clearButton);
        newSkillBox.setSpacing(10);

        EventHandler<ActionEvent> skillHandler = event -> {
            String text = textField.getText().strip();
            if (text.isEmpty()) {
                return; // Do nothing
            }
            if (controller != null) {
                controller.addRequiredSkill(text);
            }
            textField.setText("");
            textField.requestFocus();
        };

        submitButton.setOnAction(skillHandler);
        textField.setOnAction(skillHandler);
        return newSkillBox;
    }

    /**
     * Creates the widget showing the list of applicants.
     *
     * @return The Node containing the results.
     */
    private Node createResultsWidget() {
        resultBox = new VBox();
        return resultBox;
    }

    /**
     * Creates the widget used to trigger the search.
     *
     * @return The Node containing the search button.
     */
    private Node createSearchWidget() {
        Button search = new Button("Search");
        search.setOnAction(event -> {
            if (controller != null) {
                controller.search();
            }
        });
        return search;
    }

    /**
     * Creates the widget used to select the strategy.
     *
     * @return The Node containing the strategy selector.
     */
    private Node createStrategyWidget() {
        HBox box = new HBox();
        Label label = new Label("Strategy:");
        strategyBox = new ComboBox<>();
        strategyBox.getItems().addAll(StrategyChoice.values());
        strategyBox.getSelectionModel().select(StrategyChoice.ALL_50);

        strategyBox.setOnAction(event -> {
            if (controller == null) {
                return;
            }
            StrategyChoice choice = strategyBox.getValue();
            controller.setStrategyChoice(choice);
        });

        box.getChildren().addAll(label, strategyBox);
        box.setSpacing(10);
        return box;
    }

    /**
     * Creates the widget showing the list of skills currently searched for.
     *
     * @return The Node containing the list of active skills.
     */
    private Node createCurrentSearchSkillsWidget() {
        searchSkillsBox = new HBox();
        return searchSkillsBox;
    }

    /**
     * Called when the model notifies the view of a change.
     * Updates the skill list, the strategy selection, and the search results.
     */
    @Override
    public void modelUpdate() {
        StrategyChoice choice = model.getStrategyChoice();
        if (choice != null && strategyBox.getValue() != choice) {
            strategyBox.getSelectionModel().select(choice);
        }

        searchSkillsBox.getChildren().clear();
        List<String> skills = model.getRequiredSkills();
        for (String skill : skills) {
            HBox box = new HBox();
            Label labelSkill = new Label(skill + " ");
            Button removeButton = new Button("x");
            removeButton.setOnAction(event -> {
                if (controller != null) {
                    controller.removeRequiredSkill(skill);
                }
            });
            box.setStyle("-fx-padding: 2;"
                    + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 1;"
                    + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;"
                    + "-fx-border-color: black;");
            box.setAlignment(Pos.BASELINE_CENTER);
            box.getChildren().addAll(labelSkill, removeButton);
            searchSkillsBox.getChildren().add(box);
        }

        resultBox.getChildren().clear();

        List<String> required = model.getRequiredSkills();

        for (ApplicantScore as : model.getResults()) {
            var applicant = as.getApplicant();

            List<String> skillSummaries = new ArrayList<>();
            for (String skill : required) {
                int value = applicant.getSkill(skill);
                if (value > 0) {
                    skillSummaries.add(skill + "=" + value);
                }
            }

            String skillsText;
            if (skillSummaries.isEmpty()) {
                skillsText = " | Skills: (none found)";
            } else {
                skillsText = " | Skills: " + String.join(", ", skillSummaries);
            }

            String text = applicant.getName()
                    + " — "
                    + String.format("%.1f", as.getScore())
                    + skillsText;

            resultBox.getChildren().add(new Label(text));
        }
    }
}
