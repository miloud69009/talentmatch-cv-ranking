package fr.univ_lyon1.info.m1.cv_search.view;


import fr.univ_lyon1.info.m1.cv_search.controller.CvController;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantScore;
import fr.univ_lyon1.info.m1.cv_search.model.ModelListener;
import fr.univ_lyon1.info.m1.cv_search.model.SearchModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import java.util.List;


/**
 * Main view of the application, implemented using JavaFX.
 */
public class JfxView implements ModelListener {
    private final SearchModel model;
    private CvController controller;
    private HBox searchSkillsBox;
    private VBox resultBox;
    private ComboBox<StrategyChoice> strategyBox;
    private TextField skillTextField;

    private enum StrategyChoice {
        ALL_50("tout ≥ 50%"),
        ALL_60("tout ≥ 60%"),
        AVG_50("moyenne ≥ 50%");

        private final String label;

        StrategyChoice(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /**
     * Create the main view of the application.
     */
    public JfxView(final Stage stage, final int width, final int height, SearchModel model) {
        this.model = model;
        this.model.addListener(this);


        // Name of window
        stage.setTitle("Search for CV");

        VBox root = new VBox();

        Node newSkillBox = createNewSkillWidget();
        root.getChildren().add(newSkillBox);

        Node searchSkillsBox = createCurrentSearchSkillsWidget();
        root.getChildren().add(searchSkillsBox);
        Node strategy = createStrategyWidget();
        root.getChildren().add(strategy);


        Node search = createSearchWidget();
        root.getChildren().add(search);

        Node resultBox = createResultsWidget();
        root.getChildren().add(resultBox);

        // Everything's ready: add it to the scene and display it
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }


    public void setController(CvController controller) {
        this.controller = controller;
    }

    /**
     * Create the text field to enter a new skill.
     */
    private Node createNewSkillWidget() {
        HBox newSkillBox = new HBox();
        Label labelSkill = new Label("Skill:");
        TextField textField = new TextField();
        Button submitButton = new Button("Add skill");
        newSkillBox.getChildren().addAll(labelSkill, textField, submitButton);
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
     * Create the widget showing the list of applicants.
     */
    private Node createResultsWidget() {
        resultBox = new VBox();
        return resultBox;
    }

    /**
     * Create the widget used to trigger the search.
     */
    private Node createSearchWidget() {
        Button search = new Button("Search");
        search.setOnAction(event -> {
            if (controller != null) {
                controller.search();
            }
            // Les résultats seront affichés dans modelUpdate()
        });
        return search;
    }



    private Node createStrategyWidget() {
        HBox box = new HBox();
        Label label = new Label("Strategy:");
        strategyBox = new ComboBox<>();
        strategyBox.getItems().addAll(StrategyChoice.ALL_50, StrategyChoice.AVG_50, StrategyChoice.ALL_60);
        strategyBox.getSelectionModel().select(StrategyChoice.ALL_50);

        strategyBox.setOnAction(event -> {
            if (controller == null) {
                return;
            }
            StrategyChoice choice = strategyBox.getValue();
            switch (choice) {
                case ALL_50 -> controller.setAllAtLeast50();
                case ALL_60 -> controller.setAllAtLeast60();
                case AVG_50 -> controller.setAverageAtLeast50();
            }
        });

        box.getChildren().addAll(label, strategyBox);
        box.setSpacing(10);
        return box;
    }

    /**
     * Create the widget showing the list of skills currently searched
     * for.
     */
    private Node createCurrentSearchSkillsWidget() {
        searchSkillsBox = new HBox();
        return searchSkillsBox;
    }

    @Override
    public void modelUpdate() {
        // 1) Mettre à jour les skills affichées
        searchSkillsBox.getChildren().clear();
        List<String> skills = model.getRequiredSkills();
        for (String skill : skills) {
            Button skillBtn = new Button(skill);
            skillBtn.setOnAction(event -> {
                if (controller != null) {
                    controller.removeRequiredSkill(skill);
                }
            });
            searchSkillsBox.getChildren().add(skillBtn);
        }

        // 2) Mettre à jour les résultats
        resultBox.getChildren().clear();
        for (ApplicantScore as : model.getResults()) {
            String text = as.getApplicant().getName()
                    + " — "
                    + String.format("%.1f", as.getScore());
            resultBox.getChildren().add(new Label(text));
        }
    }

}
