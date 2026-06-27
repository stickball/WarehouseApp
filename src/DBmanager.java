import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBmanager {
    private static final String URL = "jdbc:mysql://localhost:3306/warehouse_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //προσθήκη προϊόντος
    public void addProduct(String name, int quantity, double price) {
        String query = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
            System.out.println("Το προϊόν προστέθηκε επιτυχώς!");

            } catch (SQLException e) {
                System.out.println("Σφάλμα κατά την προσθήκη: " + e.getMessage());
        }
    }

    //προβολή προϊόντων
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά την ανάγνωση: " + e.getMessage());
        }
        return products;
    }

    //ενημέρωση προϊόντος
    public void updateProduct(int id, String name, int quantity, double price) {
        String query = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Το προϊόν ενημερώθηκε επιτυχώς!");
        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά την ενημέρωση: " + e.getMessage());
        }
    }

    //διαγραφή προϊόντος
    public void deleteProduct(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Το προϊόν διαγράφηκε επιτυχώς!");

        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά την διαγραφή: " + e.getMessage());
        }
    }

    // εύρεση προϊόντος με το ID (για τις παλιές τιμές στο Update)
    public Product getProductById(int id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product (
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Σφαλμα κατά την εύρεση προϊόντος: " + e.getMessage());
        }
        return null;
    }
}
