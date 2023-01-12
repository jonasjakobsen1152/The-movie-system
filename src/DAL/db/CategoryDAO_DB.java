package DAL.db;

import BE.Category;
import DAL.ICategoriesDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO_DB implements ICategoriesDAO {

    private MyDatabaseConnector dbConnector;

    public CategoryDAO_DB() {
        dbConnector = new MyDatabaseConnector();
    }

    /**
     * Gets the categories from the Categories table in the database
     *
     * @return List<Category>
     * @throws Exception
     */
    @Override
    public List<Category> getAllCategories() throws SQLException {

        List<Category> categoryList = new ArrayList<>();

        //SQL string that selects everything from the table "Categories"
        String sql = "SELECT * FROM Categories;";
        try (Connection connection = dbConnector.getConnection()) {

            Statement statement = connection.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int categoryID = resultSet.getInt("CategoryID");
                    String categoryName = resultSet.getString("Category");

                    Category category = new Category(categoryID, categoryName);
                    categoryList.add(category);
                }
            }
        }
        return categoryList;
    }

    /**
     * Create a new category in the table Categories
     *
     * @param categoryName
     * @return Category
     * @throws Exception
     */
    public Category createCategory(String categoryName) throws Exception {
        //SQL string that creates a new category in the "Categories" table
        String sql = "INSERT INTO Categories (Category) VALUES (?);";

        try (Connection connection = dbConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, categoryName);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            //Creates a new category based on the given name
            Category category = new Category(id, categoryName);
            return category;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create a category", ex);
        }
    }

    /**
     * Delete a category in the table Categories
     * @param deletedCategory
     */
    @Override
    public void deleteCategory(Category deletedCategory) {
        try(Connection conn = dbConnector.getConnection()) {

            //SQL string that deletes a category from the "Categories" table
            String sql = "" + "DELETE FROM Categories WHERE Category = (?) AND CategoryID = (?);";
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Binding parameters
            stmt.setString(1, deletedCategory.getCategory());
            stmt.setInt(2, deletedCategory.getId());

            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException("Could noget delte category", e);
        }
    }
}
