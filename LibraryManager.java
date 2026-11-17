package LibraryApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    private List<Book> books = new ArrayList<>();
    private final String FILE_NAME = "books.json";

    public LibraryManager() {
        loadBooksFromJson();
    }

    
    public void loadBooksFromJson() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            books.add(new Book(253910, "Pride and Prejudice", "Jane Austen", "Love", 10, 7));
            books.add(new Book(391520, "Programming in ANSI C", "E. Balagurusamy", "Educational", 15, 10));
            books.add(new Book(715332, "Shrimad Bhagavad Gita", "Krishna Dvaipayana", "Motivational", 20, 18));
            books.add(new Book(935141, "Java: The Complete Reference", "Herbert Schildt", "Educational", 12, 9));
            books.add(new Book(459901, "It", "Stephan King", "Horror", 7, 5));
            saveBooksToJson();
        } else {
            try {
                String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
                books = parseJsonToBooks(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    public void saveBooksToJson() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            json.append("  {\n");
            json.append("    \"isbn\": ").append(b.getIsbn()).append(",\n");
            json.append("    \"title\": \"").append(b.getTitle()).append("\",\n");
            json.append("    \"author\": \"").append(b.getAuthor()).append("\",\n");
            json.append("    \"genre\": \"").append(b.getGenre()).append("\",\n");
            json.append("    \"quantity\": ").append(b.getQuantity()).append(",\n");
            json.append("    \"checkedOut\": ").append(b.getCheckedOut()).append("\n");
            json.append("  }").append(i < books.size() - 1 ? "," : "").append("\n");
        }
        json.append("]");

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private List<Book> parseJsonToBooks(String json) {
        List<Book> list = new ArrayList<>();
        try {
            json = json.trim();
            if (json.startsWith("[")) json = json.substring(1);
            if (json.endsWith("]")) json = json.substring(0, json.length() - 1);
            
            String[] items = json.split("\\},\\s*\\{");
            for (String item : items) {
                item = item.replace("{", "").replace("}", "").trim();
                String[] lines = item.split(",\n");
                
                int isbn = 0, qty = 0, out = 0;
                String title = "", author = "", genre = "";

                for (String line : lines) {
                    String[] parts = line.split(":");
                    if (parts.length < 2) continue;
                    String key = parts[0].trim().replace("\"", "");
                    String val = parts[1].trim().replace("\"", "");

                    switch (key) {
                        case "isbn": isbn = Integer.parseInt(val); break;
                        case "title": title = val; break;
                        case "author": author = val; break;
                        case "genre": genre = val; break;
                        case "quantity": qty = Integer.parseInt(val); break;
                        case "checkedOut": out = Integer.parseInt(val); break;
                    }
                }
                list.add(new Book(isbn, title, author, genre, qty, out));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    
    public boolean checkOutBook(int isbn) {
        for (Book b : books) {
            if (b.getIsbn() == isbn) {
                if (b.getAvailable() > 0) {
                    b.setCheckedOut(b.getCheckedOut() + 1);
                    saveBooksToJson();
                    return true;
                }
            }
        }
        return false;
    }

    
    public boolean checkInBook(int isbn) {
        for (Book b : books) {
            if (b.getIsbn() == isbn) {
                if (b.getCheckedOut() > 0) {
                    b.setCheckedOut(b.getCheckedOut() - 1);
                    saveBooksToJson();
                    return true;
                }
            }
        }
        return false;
    }
}