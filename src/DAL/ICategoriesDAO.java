package DAL;

import BE.Category;
import BE.Movie;

import java.util.List;

public interface ICategoriesDAO {
    public List<Category> getAllCategories() throws Exception;

    public Category createCategory(String categoryName) throws Exception;


}
