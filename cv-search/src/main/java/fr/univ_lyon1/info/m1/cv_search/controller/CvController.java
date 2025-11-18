package fr.univ_lyon1.info.m1.cv_search.controller;

import fr.univ_lyon1.info.m1.cv_search.model.SearchModel;
import fr.univ_lyon1.info.m1.cv_search.model.AllAtLeastStrategy;
import fr.univ_lyon1.info.m1.cv_search.model.AverageAtLeastStrategy;

public class CvController {
    private final SearchModel model;
    //private final JfxView view;

    public CvController(SearchModel model/*, JfxView view*/) {
        this.model = model;
      //  this.view = view;

       // model.addListener(view);
    }

    public void addRequiredSkill(String skill) {
        model.addRequiredSkill(skill);
    }

    public void removeRequiredSkill(String skill) {
        model.removeRequiredSkill(skill);
    }

    public void setAllAtLeast50() {
        model.setStrategy(new AllAtLeastStrategy(50, "tout >= 50%"));
    }

    public void setAllAtLeast60() {
        model.setStrategy(new AllAtLeastStrategy(60, "tout >= 60%"));
    }

    public void setAverageAtLeast50() {
        model.setStrategy(new AverageAtLeastStrategy(50, "moyenne >=50%"));
    }
    public void search() {
        model.search();
    }
}