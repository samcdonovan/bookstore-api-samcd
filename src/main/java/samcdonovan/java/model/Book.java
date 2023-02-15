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
        String bookString = "{";

        if (this.id != 0) bookString += "'id'=" + this.id + ", ";
        if (this.title != null) bookString += "'title'='" + this.title + "', ";
        if (this.author != null) bookString += "'author'='" + this.author + "', ";
        if (this.isbn != null) bookString += "'isbn'='" + this.isbn + "', ";
        if (this.price != 0.0) bookString += "'price'  " + this.price;

        bookString += "}";

        /* remove comma and space from end of string if they exist */
        if (bookString.charAt(bookString.length() - 3) == ',')
            bookString = bookString.substring(0, bookString.length() - 3)
                    + bookString.substring(bookString.length() - 1, bookString.length());

        return bookString;
    }
}
