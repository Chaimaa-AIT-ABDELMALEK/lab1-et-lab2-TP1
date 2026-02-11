mysql> create database sir;
Query OK, 1 row affected (0.13 sec)

mysql> CREATE TABLE `etudiant` (
    ->   `id` int(11) NOT NULL,
    ->   `nom` varchar(50) NOT NULL,
    ->   `prenom` varchar(50) NOT NULL,
    ->   `filiere` int(11) NOT NULL
    -> ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
ERROR 1046 (3D000): No database selected
mysql> CREATE DATABASE IF NOT EXISTS atelier;
Query OK, 1 row affected (0.17 sec)

mysql> SHOW DATABASES;
+--------------------+
| Database           |
+--------------------+
| atelier            |
| information_schema |
| laravel            |
| mysql              |
| performance_schema |
| project_name       |
| sir                |
| sys                |
+--------------------+
8 rows in set (0.17 sec)