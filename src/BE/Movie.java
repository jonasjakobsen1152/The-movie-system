package BE;

public class Movie {
    private int id;
    private String title;
    private int year;
    private float imdbRating;
    private float personalRating;
    private String filepath;


    public Movie(int id, String title, int year, float imdbRating, float personalRating, String filepath){
        this.id = id;
        this.title = title;
        this.year = year;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.filepath = filepath;
    }

    public int getId() {return id;}

    public String getTitle() {return title;}

    public int getYear() {return year;}

    public float getImdbRating() {return imdbRating;}

    public float getPersonalRating() {return personalRating;}

    public String getFilepath() {return filepath;}

    @Override
    public String toString() {
        return title
                ", title='" + title + '\'' +
                ", year=" + year +
                ", imdbRating=" + imdbRating +
                ", personalRating=" + personalRating +
                '}';
    }
}
