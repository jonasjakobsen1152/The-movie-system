package GUI.Controller;

import GUI.Model.MRSModel;

public abstract class BaseController {
    private MRSModel model;

    public MRSModel getModel(){return model;};

    public void setModel(MRSModel model){this.model = model;}

    public abstract void setup();
}
