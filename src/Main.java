import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DBmanager db = new DBmanager();
        boolean running = true;

        System.out.println("--- Διαχείρηση Αποθήκης ---");

        while(running) {
            System.out.println("\n Επίλεξε ενέργεια:");
            System.out.println("1 -> Προσθήκη Προϊόντος");
            System.out.println("2 -> Προβολή Προϊόντων");
            System.out.println("3 -> Ενημέρωση Προϊόντος");
            System.out.println("4 -> Διαγραφή Προϊόντος");
            System.out.println("5 -> Έξοδος");
            System.out.print("Επιλογή: ");

            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    System.out.println("\n[+] Ετοιμασία για προσθήκη..");

                    System.out.print("Όνομα προϊόντος: ");
                    String name = scanner.nextLine();

                    System.out.print("Ποσότητα: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    System.out.print("Τιμή: ");
                    double price = Double.parseDouble(scanner.nextLine());

                    db.addProduct(name, quantity, price);
                    break;

                case "2":
                    System.out.println("\n[*] Φόρτωση δεδομένων αποθήκης..");

                    List<Product> products = db.getAllProducts();
                    for (Product p : products) {
                        System.out.println("ID: " + p.getId() +
                                " | Όνομα: " + p.getName() +
                                " | Ποσότητα: " + p.getQuantity() +
                                " | Τιμή: " + p.getPrice() + "€");
                    }
                    break;

                case "3":
                    System.out.println("\n[!] Ετοιμασία για ενημέρωση προϊόντων..");
                    System.out.print("Δώσε το ID/id του προϊόντος για ενημέρωση: ");
                    int updateId = Integer.parseInt(scanner.nextLine());

                    Product oldProduct = db.getProductById(updateId);

                    if (oldProduct == null) {
                        System.out.println("Το προϊόν με ID/id " + updateId + " δεν βρέθηκε.");
                    } else {
                        System.out.println("Αφήστε κενό (Enter) για να κρατήσετε την παλιά τιμή.");

                        System.out.print("Νέο Όνομα (Παλιό: " + oldProduct.getName() + "): ");
                        String newName = scanner.nextLine();
                        if (newName.trim().isEmpty()){
                            newName = oldProduct.getName();
                        }

                        System.out.print("Νέα Ποσότητα (Παλιά: " + oldProduct.getQuantity() + "): ");
                        String newQtyStr = scanner.nextLine();
                        int newQty;
                        if (newQtyStr.trim().isEmpty()) {
                            newQty = oldProduct.getQuantity();
                        } else {
                            newQty = Integer.parseInt(newQtyStr);
                        }

                        System.out.print("Νέα Τιμή (Παλιά: " + oldProduct.getPrice() + "): ");
                        String newPriceStr = scanner.nextLine();
                        double newPrice;
                        if (newPriceStr.trim().isEmpty()) {
                            newPrice = oldProduct.getPrice();
                        } else {
                            newPrice = Double.parseDouble(newPriceStr);
                        }

                        db.updateProduct(updateId, newName, newQty, newPrice);
                    }
                    break;

                case "4":
                    System.out.println("\n[-] Ετοιμασία για διαγραφή..");
                    System.out.print("Δώστε το ID/id του προϊόντος για διαγραφή: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    db.deleteProduct(deleteId);
                    break;

                case "5":
                    running = false;
                    System.out.println("Κλείσιμο εφαρμογής.");
                    break;

                default:
                    System.out.println("Λάθος επιλογή. Παρακαλώ δοκιμάστε ξανά.");
                    break;
            }
        }
        scanner.close();
    }
}