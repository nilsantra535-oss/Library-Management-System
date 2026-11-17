package LibraryApp;

public class Book {
    private int isbn;
    private String title;
    private String author;
    private String genre;
    private int quantity;
    private int checkedOut;

    public Book(int isbn, String title, String author, String genre, int quantity, int checkedOut) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
        this.checkedOut = checkedOut;
    }

    // Getters and Setters
    public int getIsbn() { return isbn; }
    public void setIsbn(int isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getCheckedOut() { return checkedOut; }
    public void setCheckedOut(int checkedOut) { this.checkedOut = checkedOut; }

    public int getAvailable() {
        return quantity - checkedOut;
    }
}