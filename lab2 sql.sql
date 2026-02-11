mysql> USE atelier;
Database changed
mysql> DROP TABLE IF EXISTS machime;
Query OK, 0 rows affected, 1 warning (0.03 sec)

mysql> DROP TABLE IF EXISTS employe;
Query OK, 0 rows affected, 1 warning (0.00 sec)

mysql> CREATE TABLE employe (
    -> id INT AUTO_INCREMENT PRIMARY KEY,
    -> nom VARCHAR(50) NOT NULL,
    -> poste VARCHAR(50) NOT NULL
    -> ) ENGINE=InnoDB;
Query OK, 0 rows affected (0.14 sec)

mysql> CREATE TABLE machine(
    -> id INT AUTO_INCREMENT PRIMARY KEY,
    -> nom VARCHAR(50) NOT NULL,
    -> type VARCHAR(50) NOT NULL,
    -> employe_id INT NOT NULL,
    -> FOREIGN KEY(employe_id) REFERENCES employe(id)
    -> ON DELETE CASCADE ON UPDATE CASCADE
    -> ) ENGINE=InnoDB;
Query OK, 0 rows affected (0.09 sec)

mysql> SHOW TABLES;
+-------------------+
| Tables_in_atelier |
+-------------------+
| devdata           |
| employe           |
| machine           |
+-------------------+
3 rows in set (0.10 sec)