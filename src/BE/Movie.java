package BE;

public class Movie {
    private int id;
    private String title;
    private Float imdbRating;
    private Float personalRating;
    private String filepath;


    public Movie(int id, String title, float imdbRating, float personalRating, String filepath){
        this.id = id;
        this.title = title;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.filepath = filepath;
    }


    public int getId() {return id;}

    public String getTitle() {return title;}

    public Float getImdbRating() {return imdbRating;}

    public Float getPersonalRating() {return personalRating;}

    public String getFilepath() {return filepath;}

    @Override
    public String toString() {
        return title + imdbRating + personalRating;
    }
}
