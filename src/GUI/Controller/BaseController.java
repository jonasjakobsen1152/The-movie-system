package GUI.Controller;

import GUI.Model.MRSModel;

public abstract class BaseController {
    public BaseController() throws Exception {
       try{
           this.model = new MRSModel();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }
    private MRSModel model;

    public MRSModel getModel(){return model;};

    public abstract void setup(); //This is here so we can override it in MainController
}
