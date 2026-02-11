/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExoJDBC;

import java.sql.*;

public class ExoJDBC {

    private static final String URL = "jdbc:mysql://localhost:3306/atelier?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL introuvable. V�rifier l'import du .jar.");
            e.printStackTrace();
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            System.out.println("Connexion MySQL OK.");

            stmt.executeUpdate("DROP TABLE IF EXISTS DevData");
            stmt.executeUpdate(
                    "CREATE TABLE DevData (" +
                            "Developpeurs VARCHAR(32) NOT NULL, " +
                            "Jour VARCHAR(16) NOT NULL, " +
                            "NbScripts INT NOT NULL" +
                            ")"
            );

            stmt.executeUpdate("INSERT INTO DevData VALUES ('ALAMI', 'Lundi', 1)");
            stmt.executeUpdate("INSERT INTO DevData VALUES ('WAFI', 'Lundi', 2)");
            stmt.executeUpdate("INSERT INTO DevData VALUES ('SLAMI', 'Mardi', 9)");
            stmt.executeUpdate("INSERT INTO DevData VALUES ('ALAMI', 'Mardi', 3)");
            stmt.executeUpdate("INSERT INTO DevData VALUES ('WAFI', 'Mardi', 4)");
            stmt.executeUpdate("INSERT INTO DevData VALUES ('SLAMI', 'Mercredi', 2)");

            System.out.println("Table cr��e + donn�es ins�r�es.");

            System.out.println("\n--- Max scripts par jour ---");
            try (ResultSet rs = stmt.executeQuery(
                    "SELECT Jour, Developpeurs, MAX(NbScripts) AS MaxScripts " +
                            "FROM DevData GROUP BY Jour"
            )) {
                while (rs.next()) {
                    String jour = rs.getString("Jour");
                    String dev = rs.getString("Developpeurs");
                    int max = rs.getInt("MaxScripts");
                    System.out.println(jour + " | " + dev + " | " + max);
                }
            }

            System.out.println("\n--- Classement des d�veloppeurs (total scripts) ---");
            try (ResultSet rs = stmt.executeQuery(
                    "SELECT Developpeurs, SUM(NbScripts) AS Total " +
                            "FROM DevData GROUP BY Developpeurs ORDER BY Total DESC"
            )) {
                while (rs.next()) {
                    String dev = rs.getString("Developpeurs");
                    int total = rs.getInt("Total");
                    System.out.println(dev + " | " + total);
                }
            }

            System.out.println("\n--- Total scripts semaine ---");
            try (ResultSet rs = stmt.executeQuery("SELECT SUM(NbScripts) AS TotalSemaine FROM DevData")) {
                if (rs.next()) {
                    System.out.println("Total semaine : " + rs.getInt("TotalSemaine"));
                }
            }

            System.out.println("\n--- Total scripts pour un d�veloppeur (PreparedStatement) ---");
            String devRecherche = "ALAMI";

            String sql = "SELECT SUM(NbScripts) AS TotalDev FROM DevData WHERE Developpeurs = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, devRecherche);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int totalDev = rs.getInt("TotalDev");
                        System.out.println("Total pour " + devRecherche + " : " + totalDev);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : v�rifier MySQL (base, user/password, port).");
            e.printStackTrace();
        }
    }
}