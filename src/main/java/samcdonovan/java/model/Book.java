package samcdonovan.java.model;

import jakarta.persistence.*;

/**
 * Book class containing data about each book in the database.
 */
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    private String isbn;
    private double price;

    public Book() {
    }

    /**
     * Book class constructor; creates a new Book object with a title, author, ISBN and a price.
     *
     * @param title  The title of the book
     * @param author The book's author
     * @param isbn   The unique ISBN for the book
     * @param price  The price of the book at the bookstore
     */
    public Book(String title, String author, String isbn, double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    /* Getters and setters */
    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public double getPrice() {
        return this.price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        String bookString = "";

        bookString += "ID = " + this.id + "\n";
        bookString += "Title = " + this.title + "\n";
        bookString += "Author = " + this.author + "\n";
        bookString += "ISBN = " + this.isbn + "\n";
        bookString += "Price = " + this.price + "\n";

        return bookString;
    }
}
