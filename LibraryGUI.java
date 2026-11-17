package LibraryApp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibraryGUI extends JFrame {
    private LibraryManager manager = new LibraryManager();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public LibraryGUI() {
        setTitle("Library Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Top Panel (Search & Controls)
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Library System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search Title");
        JButton refreshButton = new JButton("Refresh");

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(new JLabel("Keyword:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(refreshButton);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel (Table View)
        String[] columns = {"ISBN", "Title", "Author", "Genre", "Total", "Checked Out", "Available"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        loadTableData(manager.getAllBooks());

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom Panel (Check In / Check Out Buttons)
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton checkOutButton = new JButton("Check Out (Take Book)");
        JButton checkInButton = new JButton("Check In (Return Book)");

        bottomPanel.add(checkOutButton);
        bottomPanel.add(checkInButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Event Listeners ---

        refreshButton.addActionListener(e -> loadTableData(manager.getAllBooks()));

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().toLowerCase();
            List<Book> filtered = new java.util.ArrayList<>();
            for (Book b : manager.getAllBooks()) {
                if (b.getTitle().toLowerCase().contains(keyword)) {
                    filtered.add(b);
                }
            }
            loadTableData(filtered);
        });

        checkOutButton.addActionListener(e -> {
            String isbnStr = JOptionPane.showInputDialog(this, "Enter ISBN to Check Out:");
            if (isbnStr != null && !isbnStr.trim().isEmpty()) {
                try {
                    int isbn = Integer.parseInt(isbnStr.trim());
                    if (manager.checkOutBook(isbn)) {
                        JOptionPane.showMessageDialog(this, "Book Checked Out Successfully!");
                        loadTableData(manager.getAllBooks());
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: Book not found or out of stock!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ISBN number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkInButton.addActionListener(e -> {
            String isbnStr = JOptionPane.showInputDialog(this, "Enter ISBN to Check In (Return):");
            if (isbnStr != null && !isbnStr.trim().isEmpty()) {
                try {
                    int isbn = Integer.parseInt(isbnStr.trim());
                    if (manager.checkInBook(isbn)) {
                        JOptionPane.showMessageDialog(this, "Book Returned Successfully!");
                        loadTableData(manager.getAllBooks());
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: Book not found or none checked out!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ISBN number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void loadTableData(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book b : books) {
            Object[] row = {
                b.getIsbn(),
                b.getTitle(),
                b.getAuthor(),
                b.getGenre(),
                b.getQuantity(),
                b.getCheckedOut(),
                b.getAvailable()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI().setVisible(true);
        });
    }
}