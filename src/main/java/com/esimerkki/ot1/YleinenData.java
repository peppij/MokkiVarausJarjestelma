package com.esimerkki.ot1;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YleinenData extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label otsikko = new Label("Varaustiedot:");
        otsikko.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button takaisin = new Button("Takaisin");
        takaisin.setOnAction(actionEvent -> {
            AloitusRuutu takasNappi = new AloitusRuutu();
            try {
                takasNappi.start(new Stage());
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            primaryStage.close();
        });

        BorderPane ylapaneeli= new BorderPane();
        ylapaneeli.setCenter(otsikko);
        ylapaneeli.setRight(takaisin);
        ylapaneeli.setPadding(new Insets(10, 20, 10, 70));

        Label vapaa = new Label("Vapaita mökkejä: ");
        vapaa.setStyle("-fx-font-size: 16px;");

        Label varattu = new Label("Varattuja mökkejä: ");
        varattu.setStyle("-fx-font-size: 16px;");

        int vapaita = 0;
        int varattuja = 0;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mokkijarjestelma", "devuser", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT onko_varattu FROM mokit");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                if (rs.getBoolean("onko_varattu")) {
                    varattuja++;
                } else {
                    vapaita++;
                }
            }

            vapaa.setText("Vapaita mökkejä: " + vapaita);
            varattu.setText("Varattuja mökkejä: " + varattuja);

        } catch (SQLException e) {
            e.printStackTrace();
            vapaa.setText("Virhe haettaessa tietoja.");
            varattu.setText("Virhe haettaessa tietoja.");
        }

        VBox keskipaneeli = new VBox(20, vapaa, varattu);
        keskipaneeli.setAlignment(Pos.CENTER);

        BorderPane paneeli = new BorderPane();
        paneeli.setTop(ylapaneeli);
        paneeli.setCenter(keskipaneeli);
        paneeli.setStyle("-fx-background-color: lightpink;");

        String nappiTyyli = "-fx-background-color: hotpink; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: black;";
        takaisin.setStyle(nappiTyyli);

        Scene scene = new Scene(paneeli, 450, 350);
        primaryStage.setTitle("Yleinen data");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

