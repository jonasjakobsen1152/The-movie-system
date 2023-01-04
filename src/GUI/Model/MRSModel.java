package GUI.Model;

public class MRSModel {
    private MovieModel movieModel;
    private CategoryModel categoryModel;

    public MRSModel(){
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();

    }

    public MovieModel getMovieModel(){return movieModel;}

    public CategoryModel getCategoryModel(){return categoryModel;}

}
