package GUI.Model;

import BE.Category;
import BLL.CategoryManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryModel {

    private CategoryManager categoryManager;
    private ObservableList<Category> categoriesToBeViewed;

    public CategoryModel() throws Exception {
        categoryManager = new CategoryManager();
        categoriesToBeViewed = FXCollections.observableArrayList();
        categoriesToBeViewed.addAll(categoryManager.getAllCategories());
    }

    public ObservableList<Category> getCategoriesToBeViewed(){return categoriesToBeViewed;}

    public void createCategory(String categoryName) throws Exception{
        Category category = categoryManager.createCategory(categoryName);
        categoriesToBeViewed.add(category);
    }

    public ObservableList<Category> getAllCategories(){return categoriesToBeViewed;}

    public void deleteCategory(Category deletedCategory) throws Exception {
        categoryManager.deleteCategory(deletedCategory);

    }

    public void showList() throws Exception {
        categoriesToBeViewed.clear();
        categoriesToBeViewed.addAll(categoryManager.getAllCategories());
    }


}
