
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {

    JTextField idField, nameField, quantityField, priceField;
    JTable productTable;
    DefaultTableModel tableModel;
    Connection con;

    Color green = new Color(22, 101, 52);
    Color lightGreen = new Color(240, 249, 242);
    Color white = Color.WHITE;
    Color darkText = new Color(35, 45, 38);

    public Main() {
        connectDatabase();
        createGUI();

        if (con != null) {
            loadProducts();
        }

        setVisible(true);
    }

    void connectDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/inventory_db";
            String user = "root";

            // Apna MySQL password yahan likho
            String password = "YOUR PASSWORD";

            con = DriverManager.getConnection(url, user, password);
            System.out.println("MySQL Connected Successfully!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Database connection failed!\n" + e.getMessage()
            );
        }
    }

    void createGUI() {
        setTitle("Inventory Management System");
        setSize(950, 650);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(lightGreen);
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(18, 20, 18, 20)
        );

        // Heading
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(green);
        headerPanel.setBorder(
            BorderFactory.createEmptyBorder(18, 20, 18, 20)
        );

        JLabel title = new JLabel("INVENTORY MANAGEMENT SYSTEM");
        title.setForeground(white);
        title.setFont(new Font("Arial", Font.BOLD, 23));

        JLabel subtitle = new JLabel(
            "Manage products, stock and prices easily"
        );
        subtitle.setForeground(new Color(220, 245, 225));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel headingText = new JPanel(new GridLayout(2, 1, 0, 5));
        headingText.setBackground(green);
        headingText.add(title);
        headingText.add(subtitle);
        headerPanel.add(headingText, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Input fields
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 12, 12));
        formPanel.setBackground(white);
        formPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(210, 230, 215)
                ),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
            )
        );

        idField = new JTextField();
        nameField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();

        styleField(idField);
        styleField(nameField);
        styleField(quantityField);
        styleField(priceField);

        addFormField(formPanel, "Product ID", idField);
        addFormField(formPanel, "Product Name", nameField);
        addFormField(formPanel, "Quantity", quantityField);
        addFormField(formPanel, "Price (Rs.)", priceField);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBackground(lightGreen);
        buttonPanel.setBorder(
            BorderFactory.createEmptyBorder(12, 0, 0, 0)
        );

        JButton addButton = createButton("Add Product");
        JButton searchButton = createButton("Search Product");
        JButton updateButton = createButton("Update Product");
        JButton deleteButton = createButton("Delete Product");
        JButton viewButton = createButton("View All");
        JButton lowStockButton = createButton("Low Stock Alert");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(lowStockButton);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(lightGreen);
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Products table
        tableModel = new DefaultTableModel(
            new String[] {
                "Product ID", "Product Name", "Quantity", "Price (Rs.)"
            },
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(tableModel);
        productTable.setRowHeight(28);
        productTable.setFont(new Font("Arial", Font.PLAIN, 13));
        productTable.setForeground(darkText);
        productTable.setBackground(white);
        productTable.setSelectionBackground(
            new Color(187, 230, 195)
        );
        productTable.setSelectionForeground(darkText);
        productTable.setGridColor(new Color(225, 235, 225));
        productTable.setShowVerticalLines(false);
        productTable.setFillsViewportHeight(true);
        productTable.setAutoCreateRowSorter(true);

        JTableHeader tableHeader = productTable.getTableHeader();
        tableHeader.setBackground(green);
        tableHeader.setForeground(white);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 13));
        tableHeader.setPreferredSize(new Dimension(100, 34));

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(
            BorderFactory.createLineBorder(
                new Color(210, 230, 215)
            )
        );
        scrollPane.getViewport().setBackground(white);

        JPanel tablePanel = new JPanel(new BorderLayout(0, 8));
        tablePanel.setBackground(lightGreen);

        JLabel tableTitle = new JLabel("Product Stock Records");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 17));
        tableTitle.setForeground(green);

        JLabel note = new JLabel(
            "Low stock warning: quantity is 5 or less."
        );
        note.setForeground(green);
        note.setFont(new Font("Arial", Font.PLAIN, 12));

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(note, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(lightGreen);
        centerPanel.add(leftPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Button actions
        addButton.addActionListener(e -> addProduct());
        searchButton.addActionListener(e -> searchProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        viewButton.addActionListener(e -> loadProducts());
        lowStockButton.addActionListener(e -> showLowStock());

        // Select a row to fill the input fields
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = productTable.getSelectedRow();

                if (row >= 0) {
                    int modelRow = productTable.convertRowIndexToModel(row);

                    idField.setText(
                        tableModel.getValueAt(modelRow, 0).toString()
                    );
                    nameField.setText(
                        tableModel.getValueAt(modelRow, 1).toString()
                    );
                    quantityField.setText(
                        tableModel.getValueAt(modelRow, 2).toString()
                    );
                    priceField.setText(
                        tableModel.getValueAt(modelRow, 3).toString()
                    );
                }
            }
        });
    }

    void addFormField(JPanel panel, String label, JTextField field) {
        JLabel text = new JLabel(label);
        text.setFont(new Font("Arial", Font.BOLD, 13));
        text.setForeground(darkText);
        panel.add(text);
        panel.add(field);
    }

    void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 32));
        field.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(195, 215, 200)
                ),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            )
        );
    }

    JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(white);
        button.setBackground(green);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );
        button.setPreferredSize(new Dimension(150, 38));

        button.setBorder(
            BorderFactory.createEmptyBorder(10, 8, 10, 8)
        );

        return button;
    }

    boolean validateFields() {
        try {
            if (idField.getText().trim().isEmpty()
                    || nameField.getText().trim().isEmpty()
                    || quantityField.getText().trim().isEmpty()
                    || priceField.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Fill all fields!");
                return false;
            }

            int id = Integer.parseInt(idField.getText().trim());
            int quantity = Integer.parseInt(
                quantityField.getText().trim()
            );
            double price = Double.parseDouble(
                priceField.getText().trim()
            );

            if (id <= 0 || quantity < 0 || price < 0
                    || Double.isNaN(price)
                    || Double.isInfinite(price)
                    || price > 99999999.99) {

                JOptionPane.showMessageDialog(
                    this,
                    "Enter a positive ID and valid non-negative quantity and price."
                );
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "ID and quantity must be whole numbers.\n"
                + "Price must be a valid number."
            );
            return false;
        }
    }

    void addProduct() {
        if (con == null || !validateFields()) {
            return;
        }

        String sql =
            "INSERT INTO products "
            + "(product_id, product_name, quantity, price) "
            + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(idField.getText().trim()));
            ps.setString(2, nameField.getText().trim());
            ps.setInt(3, Integer.parseInt(quantityField.getText().trim()));
            ps.setDouble(4, Double.parseDouble(priceField.getText().trim()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                this, "Product added successfully!"
            );

            loadProducts();
            clearFields();

        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    void searchProduct() {
        if (con == null) {
            return;
        }

        if (idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Product ID!");
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText().trim());

            String sql =
                "SELECT product_id, product_name, quantity, price "
                + "FROM products WHERE product_id = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nameField.setText(rs.getString("product_name"));
                        quantityField.setText(
                            String.valueOf(rs.getInt("quantity"))
                        );
                        priceField.setText(
                            String.valueOf(rs.getBigDecimal("price"))
                        );

                        tableModel.setRowCount(0);
                        addRow(rs);

                    } else {
                        JOptionPane.showMessageDialog(
                            this, "Product not found!"
                        );
                    }
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid Product ID!");
        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    void updateProduct() {
        if (con == null || !validateFields()) {
            return;
        }

        String sql =
            "UPDATE products SET product_name = ?, quantity = ?, price = ? "
            + "WHERE product_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nameField.getText().trim());
            ps.setInt(2, Integer.parseInt(quantityField.getText().trim()));
            ps.setDouble(3, Double.parseDouble(priceField.getText().trim()));
            ps.setInt(4, Integer.parseInt(idField.getText().trim()));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(
                    this, "Product updated successfully!"
                );
            } else {
                JOptionPane.showMessageDialog(this, "Product ID not found!");
            }

            loadProducts();
            clearFields();

        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    void deleteProduct() {
        if (con == null) {
            return;
        }

        if (idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Product ID!");
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText().trim());

            int choice = JOptionPane.showConfirmDialog(
                this,
                "Delete product ID " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            String sql = "DELETE FROM products WHERE product_id = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(
                        this, "Product deleted successfully!"
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        this, "Product ID not found!"
                    );
                }
            }

            loadProducts();
            clearFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid Product ID!");
        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    void loadProducts() {
        if (con == null || tableModel == null) {
            return;
        }

        String sql =
            "SELECT product_id, product_name, quantity, price "
            + "FROM products ORDER BY product_id";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            tableModel.setRowCount(0);

            while (rs.next()) {
                addRow(rs);
            }

        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    void showLowStock() {
        if (con == null) {
            return;
        }

        String sql =
            "SELECT product_id, product_name, quantity, price "
            + "FROM products WHERE quantity <= 5 "
            + "ORDER BY quantity";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            tableModel.setRowCount(0);

            while (rs.next()) {
                addRow(rs);
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(
                    this, "No low-stock products found!"
                );
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    tableModel.getRowCount()
                    + " low-stock product(s) found."
                );
            }

        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    void addRow(ResultSet rs) throws SQLException {
        tableModel.addRow(new Object[] {
            rs.getInt("product_id"),
            rs.getString("product_name"),
            rs.getInt("quantity"),
            rs.getBigDecimal("price")
        });
    }

    void clearFields() {
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        productTable.clearSelection();
    }

    void showDatabaseError(SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Database error:\n" + e.getMessage()
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
