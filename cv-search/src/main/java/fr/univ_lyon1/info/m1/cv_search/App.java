package fr.univ_lyon1.info.m1.cv_search;

import fr.univ_lyon1.info.m1.cv_search.controller.CvController;
import fr.univ_lyon1.info.m1.cv_search.model.SearchModel;
import fr.univ_lyon1.info.m1.cv_search.model.StrategyChoice;
import fr.univ_lyon1.info.m1.cv_search.view.JfxView;
import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * With JavaFX, start() is called when the application is launched.
     *
     * @param primaryStage The primary stage for this application.
     * @throws Exception If an error occurs during application startup.
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        SearchModel model = new SearchModel(
                new File("."),
                StrategyChoice.ALL_50
        );

        CvController controller = new CvController(model);
        JfxView view1 = new JfxView(primaryStage, 600, 600, model);
        view1.setController(controller);

        Stage secondStage = new Stage();
        JfxView view2 = new JfxView(secondStage, 400, 400, model);
        view2.setController(controller);
    }

    /**
     * A main method in case the user launches the application using
     * App as the main class.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
