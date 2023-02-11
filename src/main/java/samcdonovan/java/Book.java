package samcdonovan.java;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private float price;

    public Book(String title, String author, String isbn, float price){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    /* Getters and setters */
    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getIsbn(){
        return this.isbn;
    }
    public float getPrice(){
        return this.price;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public void setPrice(float price){
        this.price = price;
    }
}
