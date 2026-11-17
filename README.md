# Library Management System 📚

A simple Desktop-based **Library Management System** built using Java Swing for the graphical user interface and standard Java file I/O for manual JSON-based data persistence.

---

## 🚀 Features
* **Interactive GUI**: Built using Java Swing (`JFrame`, `JTable`, `JTextField`, `JButton`).
* **Book Inventory Management**: View all books, their total stock, checked-out counts, and real-time available quantities.
* **Search Functionality**: Filter books instantly by title keywords.
* **Check-In / Check-Out System**: Borrow or return books securely using their unique ISBN.
* **Data Persistence**: Automatically reads and writes inventory data to a local `books.json` file.

---

## 🛠️ Tech Stack
* **Language**: Java
* **UI Framework**: Java Swing / AWT
* **Data Storage**: Custom JSON parser & File I/O (`books.json`)

---

## 📂 Project Structure
```text
LibraryApp/
│
├── Book.java          # Book model/entity class
├── LibraryManager.java # Business logic and JSON data handler
└── LibraryGUI.java    # Swing-based graphical user interface