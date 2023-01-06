package GUI.Controller;

import GUI.Model.CategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;

public class CategoryController extends BaseController {

    public TextField txtCategory;
    private CategoryModel categoryModel;

    public CategoryController() throws Exception {
        categoryModel = new CategoryModel();
    }

    public void handleCreateCategory(ActionEvent actionEvent) {
        String categoryName = txtCategory.getText();
        try {
            categoryModel.createCategory(categoryName);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void setup() {
        categoryModel = getModel().getCategoryModel();
    }

}
