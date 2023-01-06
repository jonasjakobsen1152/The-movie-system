package BLL;

import BE.Category;
import DAL.ICategoriesDAO;
import DAL.db.CategoryDAO_DB;

import java.util.List;

public class CategoryManager {

    ICategoriesDAO categoriesDAO_DB;

    public CategoryManager(){categoriesDAO_DB = new CategoryDAO_DB();}

    public Category createCategory(String categoryName) throws Exception{
        return categoriesDAO_DB.createCategory(categoryName);
    }

    public List<Category> getAllCategories() throws Exception {
        return categoriesDAO_DB.getAllCategories();
    }
}
