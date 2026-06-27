# WarehouseApp 📦

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)

Ένα απλό πρόγραμμα σε Java (κονσόλα) για διαχείριση αποθήκης. Το έφτιαξα για να εξασκηθώ στη σύνδεση Java με MySQL (JDBC) και να δω πώς δουλεύει ένα CRUD σύστημα στην πράξη.

### ⚙️ Λειτουργίες
* **Προσθήκη** νέου προϊόντος.
* **Προβολή** όλων των προϊόντων από τη βάση.
* **Ενημέρωση (Update)** στοιχείων (Όνομα, Ποσότητα, Τιμή).
* **Διαγραφή** προϊόντος με βάση το ID.

### 🚀 Πώς να το τρέξετε
1. Ανοίξτε το WampServer (ή XAMPP).
2. Φτιάξτε μια βάση δεδομένων με όνομα `warehouse_db` στο phpMyAdmin.
3. Τρέξτε το παρακάτω SQL για να δημιουργηθεί ο πίνακας:
   ```sql
   CREATE TABLE products (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(255) NOT NULL,
       quantity INT NOT NULL,
       price DOUBLE NOT NULL
   );
4. Ανοίξτε το project στο IDE σας και τρέξτε το Main.java (τα αρχεία του MySQL Connector είναι ήδη μέσα στον φάκελο lib).
