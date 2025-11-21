package fr.univ_lyon1.info.m1.cv_search;

import javafx.application.Application;
import javafx.stage.Stage;
import fr.univ_lyon1.info.m1.cv_search.model.SearchModel;
import fr.univ_lyon1.info.m1.cv_search.controller.CvController;
import fr.univ_lyon1.info.m1.cv_search.view.JfxView;
import fr.univ_lyon1.info.m1.cv_search.model.StrategyChoice;

import java.io.File;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        SearchModel model = new SearchModel(
                new File("."),
              //  new AllAtLeastStrategy(50, "tout >= 50%")
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
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
