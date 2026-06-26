import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("--- Διαχείρηση Αποθήκης ---");

        String dbUrl = "jdbc:mysql://localhost:3306/warehouse_db";
        String dbUser = "root";
        String dbPass = "";

        java.sql.Connection connection = null;

        try {
            connection = java.sql.DriverManager.getConnection(dbUrl, dbUser, dbPass);
            System.out.println("{DB} Συνδέθηκε επιτυχώς!");
        } catch (Exception e) {
            System.out.println("{DB} Σφάλμα σύνδεσης! Error:");
            e.printStackTrace();
            return;
        }

        while(isRunning) {
            System.out.println("\n Τι θέλεις να κάνεις;");
            System.out.println("1 -> Προσθήκη Προϊόντος");
            System.out.println("2 -> Προβολή Αποθήκης");
            System.out.println("3 -> Διαγραφή Προϊόντος");
            System.out.println("4 -> Αλλαγή Στοιχεία Προϊόντος");
            System.out.println("5 -> Έξοδος");
            System.out.print("Δώσε επιλογή: ");

            int choice = input.nextInt();

            switch(choice) {
                case 1:
                    System.out.println("\n[+] Ετοιμασία για προσθήκη..");

                    input.nextLine();

                    System.out.print("Δώσε όνομα προϊόντος: ");
                    String newName = input.nextLine();

                    System.out.print("Δώσε ποσότητα: ");
                    int newQty = input.nextInt();

                    System.out.print("Δώσε τιμή: ");
                    double newPrice = input.nextDouble();

                    try {
                        String insertQuery = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";
                        java.sql.PreparedStatement pstmt = connection.prepareStatement(insertQuery);

                        pstmt.setString(1, newName);
                        pstmt.setInt(2, newQty);
                        pstmt.setDouble(3, newPrice);

                        int rowsAdded = pstmt.executeUpdate();
                        if (rowsAdded > 0) {
                            System.out.println("Το προϊόν '" + newName + "' αποθηκεύτηκε στη βάση!");
                        }

                    } catch (Exception e) {
                        System.out.println("Σφάλμα κατά την αποθήκευση: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("\n[*] Φόρτωση δεδομένων αποθήκης..");
                    try {
                        java.sql.Statement statement = connection.createStatement();

                        String query = "SELECT * FROM products";

                        java.sql.ResultSet resultSet = statement.executeQuery(query);

                        System.out.println("ID | Όνομα Προϊόντος | Ποσότητα | Τιμή");
                        System.out.println("----------------------------------------------");

                        while(resultSet.next()){
                            int id = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            int qty = resultSet.getInt("quantity");
                            double price = resultSet.getDouble("price");

                            System.out.println(id + " | " + name + " | " + qty + " τεμ. | " + price + "€");
                        }
                    } catch (Exception e){
                        System.out.println("Πρόβλημα κατά την ανάγνωση: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("\n[-] Ετοιμασία για διαγραφή..");
                    System.out.print("Δώσε το ID/id του προϊόντος που θες να διαγράψεις: ");
                    int idToDelete = input.nextInt();

                    try {
                        String deleteQuery = "DELETE FROM products WHERE id = ?";
                        java.sql.PreparedStatement pstmtDelete = connection.prepareStatement(deleteQuery);

                        pstmtDelete.setInt(1, idToDelete);

                        int rowsDeleted = pstmtDelete.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println("Το προϊόν με ID/id " + idToDelete + " διαγράφηκε!");
                        } else {
                            System.out.println("Δεν βρέθηκε προϊόν με αυτό το ID/id.");
                        }
                    } catch (Exception e) {
                        System.out.println("Σφαλμα κατά τη διαγραφή: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("\n[!] Ετοιμασία για αλλαγή στοιχείων..");
                    System.out.print("Δώσε το ID/id του προϊόντος που θέλεις να αλλάξεις τα στοιχεία: ");
                    int idToUpdate = input.nextInt();

                    input.nextLine();

                    try {
                        String selectQuery = "SELECT * FROM products WHERE id = ?";
                        java.sql.PreparedStatement pstmtSelect = connection.prepareStatement(selectQuery);
                        pstmtSelect.setInt(1, idToUpdate);
                        java.sql.ResultSet rs = pstmtSelect.executeQuery();

                        if (rs.next()) {
                            String oldName = rs.getString("name");
                            int oldQty = rs.getInt("quantity");
                            double oldPrice = rs.getDouble("price");

                            System.out.println("-> Βρέθηκε: " + oldName + " | " + oldQty + " τεμ. | " + oldPrice + "€");
                            System.out.println("(Πάτησε ENTER αν θέλεις να κρατήσεις την παλιά τιμή)\n");

                            System.out.print("Νέο όνομα [" + oldName + "]: ");
                            String changeName = input.nextLine();
                            if (changeName.trim().isEmpty()) {
                                changeName = oldName;
                            }

                            System.out.print("Νέα ποσότητα [" + oldQty + "]: ");
                            String qtyInput = input.nextLine();
                            int changeQty;
                            if (qtyInput.trim().isEmpty()){
                                changeQty = oldQty;
                            } else {
                                changeQty = Integer.parseInt(qtyInput);
                            }

                            System.out.print("Νέα τιμή [" + oldPrice + "]: ");
                            String priceInput = input.nextLine();
                            double changePrice;
                            if (priceInput.trim().isEmpty()){
                                changePrice = oldPrice;
                            } else {
                                changePrice = Double.parseDouble(priceInput);
                            }

                            String updateQuery = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?";
                            java.sql.PreparedStatement pstmtUpdate = connection.prepareStatement(updateQuery);
                            pstmtUpdate.setString(1, changeName);
                            pstmtUpdate.setInt(2, changeQty);
                            pstmtUpdate.setDouble(3, changePrice);
                            pstmtUpdate.setInt(4, idToUpdate);

                            pstmtUpdate.executeUpdate();
                            System.out.println("Οι αλλαγές έγιναν με επιτυχία!");
                        } else {
                            System.out.println("Δεν βρέθηκε προϊόν με αυτό το ID/id.");
                        }
                    } catch (Exception e) {
                        System.out.println("Σφάλμα κατά την αλλαγή: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Κλείσιμο εφαρμογής.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Λάθος επιλογή. Διάλεξε από 1 εώς 5.");
                    break;
            }
        }
        input.close();
    }
}