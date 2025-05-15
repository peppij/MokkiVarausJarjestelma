package com.esimerkki.ot1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsiakasTiedotDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/mokkijarjestelma";
    private static final String USER = "devuser";
    private static final String PASSWORD = "";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<AsiakasTiedot> haeKaikkiAsiakkaat() {
        List<AsiakasTiedot> asiakas = new ArrayList<>();
        String sql = "SELECT * FROM asiakas";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                asiakas.add(new AsiakasTiedot(
                        rs.getInt("asiakasnumero"),
                        rs.getString("puhelinnumero"),
                        rs.getBoolean("yritysasiakas"),
                        rs.getString("sahkoposti"),
                        rs.getString("asiakasnimi")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return asiakas;
    }

    public static void paivitaAsiakkaat(AsiakasTiedot asiakas) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mokkijarjestelma", "devuser", "")) {
            String sql = "UPDATE asiakas SET puhelinnumero=?, yritysasiakas=?, sahkoposti=?, asiakasnimi=? WHERE asiakasnumero=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, asiakas.getPuhelinnumero());
            stmt.setBoolean(2, asiakas.getYritysAsiakas());
            stmt.setString(3, asiakas.getSahkoposti());
            stmt.setString(4, asiakas.getAsiakkaanNimi());
            stmt.setInt(5, asiakas.getAsiakasNumero());

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            if (rowsAffected > 0) {
                System.out.println("Asiakas päivitetty tietokantaan: " + asiakas.getAsiakasNumero());
            } else {
                System.out.println("Asiakasta ei löydetty päivittämistä varten: " + asiakas.getAsiakasNumero());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}