package BE;

public class Category {

    int id;
    String category;
public Category(int id, String category){
    this.id = id;
    this.category = category;

}

public int getId(){
    return id;
}

public String getCategory(){
    return category;
}
}
