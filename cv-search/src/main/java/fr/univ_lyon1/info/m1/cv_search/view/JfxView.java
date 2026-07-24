package fr.univ_lyon1.info.m1.cv_search.view;

import fr.univ_lyon1.info.m1.cv_search.controller.CvController;
import fr.univ_lyon1.info.m1.cv_search.model.ApplicantScore;
import fr.univ_lyon1.info.m1.cv_search.model.ModelListener;
import fr.univ_lyon1.info.m1.cv_search.model.SearchModel;
import fr.univ_lyon1.info.m1.cv_search.model.StrategyChoice;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main view of the application, implemented using JavaFX.
 * It observes the {@link SearchModel} to update the display automatically.
 */
public class JfxView implements ModelListener {

    /** Maximum skill score used by the progress bar. */
    private static final double MAX_SCORE = 100.0;

    /** The search model to observe. */
    private final SearchModel model;

    /** The controller used to propagate user actions. */
    private CvController controller;

    /** Container for the list of skills currently being searched. */
    private FlowPane searchSkillsPane;

    /** Container for the search results. */
    private VBox resultBox;

    /** Dropdown menu to select the search strategy. */
    private ComboBox<StrategyChoice> strategyBox;

    /** Input field for adding new skills. */
    private TextField skillTextField;

    /** Label showing the current number of results. */
    private Label resultCountLabel;

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

        stage.setTitle("TalentMatch - CV Search");
        stage.setMinWidth(900);
        stage.setMinHeight(620);

        BorderPane root = new BorderPane();
        root.getStyleClass().add("app-root");
        root.setTop(createHeader());
        root.setCenter(createWorkspace());
        root.setBottom(createFooter());

        Scene scene = new Scene(root, width, height);
        addStylesheet(scene);
        stage.setScene(scene);
        stage.show();

        modelUpdate();
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
     * Creates the application header.
     *
     * @return Header node.
     */
    private Node createHeader() {
        Label logo = new Label("CV");
        logo.getStyleClass().add("brand-logo");

        Label title = new Label("TalentMatch");
        title.getStyleClass().add("brand-title");

        Label subtitle = new Label("Candidate search and ranking");
        subtitle.getStyleClass().add("brand-subtitle");

        VBox texts = new VBox(2, title, subtitle);
        HBox header = new HBox(12, logo, texts);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("app-header");
        return header;
    }

    /**
     * Creates the two-column workspace.
     *
     * @return Workspace node.
     */
    private Node createWorkspace() {
        VBox searchPanel = createSearchPanel();
        searchPanel.setMinWidth(300);
        searchPanel.setPrefWidth(330);
        searchPanel.setMaxWidth(360);

        VBox resultsPanel = createResultsPanel();
        HBox.setHgrow(resultsPanel, Priority.ALWAYS);

        HBox workspace = new HBox(20, searchPanel, resultsPanel);
        workspace.setPadding(new Insets(22));
        workspace.getStyleClass().add("workspace");
        return workspace;
    }

    /**
     * Creates the search criteria panel.
     *
     * @return Search panel.
     */
    private VBox createSearchPanel() {
        Label sectionLabel = new Label("SEARCH CRITERIA");
        sectionLabel.getStyleClass().add("section-label");

        Label title = new Label("Find the right profile");
        title.getStyleClass().add("section-title");

        Label description = new Label(
                "Add the required skills and choose a selection strategy."
        );
        description.setWrapText(true);
        description.getStyleClass().add("muted-text");

        Node newSkillWidget = createNewSkillWidget();

        Label requiredSkillsLabel = new Label("Required skills");
        requiredSkillsLabel.getStyleClass().add("field-label");

        Node currentSkillsWidget = createCurrentSearchSkillsWidget();
        Node strategyWidget = createStrategyWidget();
        Node actionsWidget = createActionsWidget();

        VBox panel = new VBox(
                14,
                sectionLabel,
                title,
                description,
                new Separator(),
                newSkillWidget,
                requiredSkillsLabel,
                currentSkillsWidget,
                new Separator(),
                strategyWidget,
                actionsWidget
        );
        panel.getStyleClass().addAll("panel", "search-panel");
        return panel;
    }

    /**
     * Creates the widget used to enter a new skill.
     *
     * @return Skill input widget.
     */
    private Node createNewSkillWidget() {
        Label label = new Label("Skill");
        label.getStyleClass().add("field-label");

        skillTextField = new TextField();
        skillTextField.setPromptText("Example: Java");
        HBox.setHgrow(skillTextField, Priority.ALWAYS);

        Button addButton = new Button("Add");
        addButton.getStyleClass().addAll("button", "secondary-button");

        EventHandler<ActionEvent> skillHandler = event -> {
            String text = skillTextField.getText().strip();
            if (text.isEmpty()) {
                return;
            }
            if (controller != null) {
                controller.addRequiredSkill(text);
            }
            skillTextField.setText("");
            skillTextField.requestFocus();
        };

        addButton.setOnAction(skillHandler);
        skillTextField.setOnAction(skillHandler);

        HBox row = new HBox(10, skillTextField, addButton);
        row.setAlignment(Pos.CENTER_LEFT);
        return new VBox(8, label, row);
    }

    /**
     * Creates the widget showing the selected skills.
     *
     * @return Selected skills widget.
     */
    private Node createCurrentSearchSkillsWidget() {
        searchSkillsPane = new FlowPane(8, 8);
        searchSkillsPane.getStyleClass().add("skills-pane");
        return searchSkillsPane;
    }

    /**
     * Creates the strategy selection widget.
     *
     * @return Strategy widget.
     */
    private Node createStrategyWidget() {
        Label label = new Label("Selection strategy");
        label.getStyleClass().add("field-label");

        strategyBox = new ComboBox<>();
        strategyBox.getItems().addAll(StrategyChoice.values());
        strategyBox.getSelectionModel().select(StrategyChoice.ALL_50);
        strategyBox.setMaxWidth(Double.MAX_VALUE);

        strategyBox.setOnAction(event -> {
            if (controller == null) {
                return;
            }
            StrategyChoice choice = strategyBox.getValue();
            controller.setStrategyChoice(choice);
        });

        return new VBox(8, label, strategyBox);
    }

    /**
     * Creates the clear and search buttons.
     *
     * @return Action buttons.
     */
    private Node createActionsWidget() {
        Button clearButton = new Button("Clear");
        clearButton.getStyleClass().addAll("button", "ghost-button");
        clearButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(clearButton, Priority.ALWAYS);
        clearButton.setOnAction(event -> {
            if (controller != null) {
                controller.clearSkills();
            }
        });

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().addAll("button", "primary-button");
        searchButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(searchButton, Priority.ALWAYS);
        searchButton.setOnAction(event -> {
            if (controller != null) {
                controller.search();
            }
        });

        HBox actions = new HBox(10, clearButton, searchButton);
        actions.setPadding(new Insets(6, 0, 0, 0));
        return actions;
    }

    /**
     * Creates the result panel.
     *
     * @return Result panel.
     */
    private VBox createResultsPanel() {
        Label sectionLabel = new Label("RESULTS");
        sectionLabel.getStyleClass().add("section-label");

        Label title = new Label("Matching candidates");
        title.getStyleClass().add("section-title");

        resultCountLabel = new Label("0 candidates");
        resultCountLabel.getStyleClass().add("result-count");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox heading = new HBox(12, title, spacer, resultCountLabel);
        heading.setAlignment(Pos.CENTER_LEFT);

        resultBox = new VBox(12);
        resultBox.getStyleClass().add("results-box");

        ScrollPane scrollPane = new ScrollPane(resultBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("results-scroll");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        VBox panel = new VBox(12, sectionLabel, heading, scrollPane);
        panel.getStyleClass().addAll("panel", "results-panel");
        return panel;
    }

    /**
     * Creates a candidate display card.
     *
     * @param applicantScore Candidate and score.
     * @param requiredSkills Skills used for the current search.
     * @param rank Candidate rank in the displayed results.
     * @return Candidate card.
     */
    private Node createApplicantCard(final ApplicantScore applicantScore,
                                     final List<String> requiredSkills,
                                     final int rank) {
        var applicant = applicantScore.getApplicant();
        double score = applicantScore.getScore();

        Label rankLabel = new Label("#" + rank);
        rankLabel.getStyleClass().add("rank-badge");

        Label nameLabel = new Label(applicant.getName());
        nameLabel.getStyleClass().add("candidate-name");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label scoreLabel = new Label(String.format("%.1f / 100", score));
        scoreLabel.getStyleClass().add("score-badge");

        HBox header = new HBox(10, rankLabel, nameLabel, spacer, scoreLabel);
        header.setAlignment(Pos.CENTER_LEFT);

        ProgressBar scoreBar = new ProgressBar(normalizeScore(score));
        scoreBar.setMaxWidth(Double.MAX_VALUE);
        scoreBar.getStyleClass().add("score-progress");

        FlowPane skillPane = new FlowPane(8, 8);
        skillPane.getStyleClass().add("candidate-skills");

        List<String> skillSummaries = new ArrayList<>();
        for (String skill : requiredSkills) {
            int value = applicant.getSkill(skill);
            if (value > 0) {
                skillSummaries.add(skill + " = " + value);
            }
        }

        if (skillSummaries.isEmpty()) {
            Label emptySkills = new Label("No required skill found");
            emptySkills.getStyleClass().add("muted-text");
            skillPane.getChildren().add(emptySkills);
        } else {
            for (String summary : skillSummaries) {
                Label skillLabel = new Label(summary);
                skillLabel.getStyleClass().add("candidate-skill");
                skillPane.getChildren().add(skillLabel);
            }
        }

        VBox card = new VBox(12, header, scoreBar, skillPane);
        card.getStyleClass().add("candidate-card");
        return card;
    }

    /**
     * Creates the footer.
     *
     * @return Footer node.
     */
    private Node createFooter() {
        Label footer = new Label(
                "JavaFX interface - MVC architecture - Candidate ranking"
        );
        footer.getStyleClass().add("footer-text");

        HBox footerBox = new HBox(footer);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.getStyleClass().add("app-footer");
        return footerBox;
    }

    /**
     * Adds the optional external stylesheet.
     * The application remains launchable before the CSS file is added.
     *
     * @param scene Scene to style.
     */
    private void addStylesheet(final Scene scene) {
        URL stylesheet = getClass().getResource("/styles/app.css");
        if (stylesheet != null) {
            scene.getStylesheets().add(stylesheet.toExternalForm());
        }
    }

    /**
     * Converts a score to a progress value between 0 and 1.
     *
     * @param score Candidate score.
     * @return Normalized score.
     */
    private double normalizeScore(final double score) {
        return Math.max(0.0, Math.min(1.0, score / MAX_SCORE));
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

        searchSkillsPane.getChildren().clear();
        List<String> skills = model.getRequiredSkills();

        if (skills.isEmpty()) {
            Label emptyLabel = new Label("No skill selected");
            emptyLabel.getStyleClass().add("empty-skills");
            searchSkillsPane.getChildren().add(emptyLabel);
        } else {
            for (String skill : skills) {
                searchSkillsPane.getChildren().add(createSkillChip(skill));
            }
        }

        resultBox.getChildren().clear();
        List<ApplicantScore> results = model.getResults();
        resultCountLabel.setText(formatResultCount(results.size()));

        if (results.isEmpty()) {
            VBox emptyState = createEmptyState();
            resultBox.getChildren().add(emptyState);
            return;
        }

        int rank = 1;
        for (ApplicantScore applicantScore : results) {
            Node card = createApplicantCard(applicantScore, skills, rank);
            resultBox.getChildren().add(card);
            rank++;
        }
    }

    /**
     * Creates a removable skill chip.
     *
     * @param skill Skill name.
     * @return Skill chip.
     */
    private Node createSkillChip(final String skill) {
        Label label = new Label(skill);
        label.getStyleClass().add("skill-chip-label");

        Button removeButton = new Button("x");
        removeButton.getStyleClass().add("skill-chip-remove");
        removeButton.setOnAction(event -> {
            if (controller != null) {
                controller.removeRequiredSkill(skill);
            }
        });

        HBox chip = new HBox(6, label, removeButton);
        chip.setAlignment(Pos.CENTER);
        chip.getStyleClass().add("skill-chip");
        return chip;
    }

    /**
     * Creates the empty result state.
     *
     * @return Empty state.
     */
    private VBox createEmptyState() {
        Label icon = new Label("CV");
        icon.getStyleClass().add("empty-icon");

        Label title = new Label("No candidate displayed");
        title.getStyleClass().add("empty-title");

        Label message = new Label(
                "Choose skills and launch a search to display matching candidates."
        );
        message.setWrapText(true);
        message.setMaxWidth(380);
        message.setAlignment(Pos.CENTER);
        message.getStyleClass().add("muted-text");

        VBox emptyState = new VBox(12, icon, title, message);
        emptyState.setAlignment(Pos.CENTER);
        emptyState.getStyleClass().add("empty-state");
        return emptyState;
    }

    /**
     * Formats the result counter.
     *
     * @param count Number of candidates.
     * @return Display text.
     */
    private String formatResultCount(final int count) {
        if (count == 1) {
            return "1 candidate";
        }
        return count + " candidates";
    }
}
