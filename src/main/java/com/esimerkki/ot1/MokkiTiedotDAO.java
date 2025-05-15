package com.esimerkki.ot1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MokkiTiedotDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/mokkijarjestelma";
    private static final String USER = "devuser";
    private static final String PASSWORD = "";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<MokkiTiedot> haeKaikkiMokit() {
        List<MokkiTiedot> mokit = new ArrayList<>();
        String sql = "SELECT * FROM mokit";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                mokit.add(new MokkiTiedot(
                        rs.getInt("mokin_Numero"),
                        rs.getDouble("pinta_ala"),
                        rs.getBoolean("onko_varattu"),
                        rs.getString("lisaominaisuudet"),
                        rs.getString("mokin_nimi")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mokit;
    }

    public static void paivitaMokit(MokkiTiedot mokit) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mokkijarjestelma", "devuser", "")) {
            String sql = "UPDATE mokit SET pinta_ala=?, onko_varattu=?, lisaominaisuudet=?, mokin_nimi=? WHERE mokin_numero=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, mokit.getPintaAla());
            stmt.setBoolean(2, mokit.getOnkoVarattu());
            stmt.setString(3, mokit.getLisaominaisuudet());
            stmt.setString(4, mokit.getMokinNimi());
            stmt.setInt(5, mokit.getMokinNumero());

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            if (rowsAffected > 0) {
                System.out.println("Lasku päivitetty tietokantaan: " + mokit.getMokinNumero());
            } else {
                System.out.println("Laskua ei löydetty päivittämistä varten: " + mokit.getMokinNumero());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}