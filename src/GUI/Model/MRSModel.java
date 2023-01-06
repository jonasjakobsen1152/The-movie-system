package GUI.Model;

public class MRSModel {
    private MovieModel movieModel;
    private CategoryModel categoryModel;


    public MRSModel() throws Exception {
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();

    }

    public MovieModel getMovieModel(){return movieModel;}

    public CategoryModel getCategoryModel(){return categoryModel;}

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {this.categoryModel = categoryModel;}
}
