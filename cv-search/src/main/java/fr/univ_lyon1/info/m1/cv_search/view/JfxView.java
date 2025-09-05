package fr.univ_lyon1.info.m1.cv_search.view;

import java.io.File;

import fr.univ_lyon1.info.m1.cv_search.model.Applicant;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantList;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantListBuilder;
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


/**
 * Main view of the application, implemented using JavaFX.
 */
public class JfxView {
    private HBox searchSkillsBox;
    private VBox resultBox;
    private ComboBox<Strategy> strategyBox;
    private static enum Strategy {
        ALL_50("tout \u2265 50%", 50),
        ALL_60("tout \u2265 60%", 60);
        private final String label;
        private final int threshold;

        Strategy(String label, int threshold) {
            this.label = label;
            this.threshold = threshold;
        }

        @Override
        public String toString() {
            return label;
        }

        public int threshold() {
            return threshold;
        }
    }

        /**
     * Create the main view of the application.
     */
    public JfxView(final Stage stage, final int width, final int height) {
        // Name of window
        stage.setTitle("Search for CV");

        VBox root = new VBox();

        Node newSkillBox = createNewSkillWidget();
        root.getChildren().add(newSkillBox);

        Node searchSkillsBox = createCurrentSearchSkillsWidget();
        root.getChildren().add(searchSkillsBox);
        Node strategy =  createStrategyWidget();
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

        EventHandler<ActionEvent> skillHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                String text = textField.getText().strip();
                if (text.equals("")) {
                    return; // Do nothing
                }

                Button skillBtn = new Button(text);
                searchSkillsBox.getChildren().add(skillBtn);
                skillBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent event) {
                        searchSkillsBox.getChildren().remove(skillBtn);
                    }
                });

                textField.setText("");
                textField.requestFocus();
            }
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
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                // TODO: This code is unacceptably dirty!
                // TODO: You MUST rewrite it for the final version.
                Strategy chosen = (strategyBox != null && strategyBox.getValue() !=null)
                        ? strategyBox.getValue()
                        : Strategy.ALL_50;
                int threshold = chosen.threshold();
                ApplicantList listApplicants
                    = new ApplicantListBuilder(new File(".")).build();
                resultBox.getChildren().clear();
                for (Applicant a : listApplicants) {
                    boolean selected = true;
                    // TODO: OMG, don't ever do that ...
                    for (Node skill : searchSkillsBox.getChildren()) {
                        String skillName = ((Button) skill).getText();
                        if (a.getSkill(skillName) < threshold) {
                            selected = false;
                            break;
                        }
                    }
                    if (selected) {
                        resultBox.getChildren().add(new Label(a.getName()));
                    }
                }
            }
        });
        return search;
    }

    private Node createStrategyWidget(){
        HBox box = new HBox();
        Label label = new Label("Strategy:");
        strategyBox = new ComboBox<>();
        strategyBox.getItems().addAll(Strategy.ALL_50,Strategy.ALL_60);
        strategyBox.getSelectionModel().select(Strategy.ALL_50);
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
}
