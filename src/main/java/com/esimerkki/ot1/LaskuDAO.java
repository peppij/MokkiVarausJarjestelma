package com.esimerkki.ot1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaskuDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/mokkijarjestelma";
    private static final String USER = "devuser";
    private static final String PASSWORD = "";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<Laskut> haeKaikkiLaskut() {
        List<Laskut> laskut = new ArrayList<>();
        String sql = "SELECT * FROM lasku";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                laskut.add(new Laskut(
                        rs.getInt("laskunumero"),
                        rs.getDouble("summa"),
                        rs.getBoolean("onko_maksettu"),
                        rs.getString("lisatiedot"),
                        rs.getString("laskun_asiakas")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return laskut;
    }

    public static void paivitaLasku(Laskut lasku) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mokkijarjestelma", "devuser", "")) {
            String sql = "UPDATE lasku SET summa=?, onko_maksettu=?, lisatiedot=?, laskun_asiakas=? WHERE laskunumero=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Asetetaan tyhjennetyt tiedot tietokannan päivitykseen
            stmt.setDouble(1, lasku.getMaara());  // Määrä (nolla)
            stmt.setBoolean(2, lasku.getOnkoMaksettu());  // Maksettu (false)
            stmt.setString(3, lasku.getLisaTiedot());  // Lisätiedot (tyhjä)
            stmt.setString(4, lasku.getLaskunAsiakas());  // Asiakas ("Ei tietoja")
            stmt.setInt(5, lasku.getLaskuNumero());  // Laskunumero (0)

            // Suoritetaan SQL-kysely
            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            if (rowsAffected > 0) {
                System.out.println("Lasku päivitetty tietokantaan: " + lasku.getLaskuNumero());
            } else {
                System.out.println("Laskua ei löydetty päivittämistä varten: " + lasku.getLaskuNumero());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}