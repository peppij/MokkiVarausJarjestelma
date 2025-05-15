package com.esimerkki.ot1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AloitusRuutu extends Application {

    @Override
    public void start(Stage alkuIkkuna) {

        String nappiTyyli = "-fx-background-color: hotpink; " +
                "-fx-text-fill: black; " +
                "-fx-background-radius: 15; " +
                "-fx-padding: 8 20 8 20;";

        Button aloitaNappi = new Button("Mökit");
        aloitaNappi.setStyle(nappiTyyli);
        aloitaNappi.setOnAction(e -> {
            MokkiTiedot nappi1 = new MokkiTiedot();
            try {
                nappi1.start(new Stage());
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            alkuIkkuna.close();
        });

        Button asiakasNappi = new Button("Asiakkaat");
        asiakasNappi.setStyle(nappiTyyli);
        asiakasNappi.setOnAction(e -> {
            AsiakasTiedot nappi2 = new AsiakasTiedot();
            try {
                nappi2.start(new Stage());
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            alkuIkkuna.close();
        });

        Button laskutNappi = new Button("Laskut");
        laskutNappi.setStyle(nappiTyyli);
        laskutNappi.setOnAction(e -> {
            Laskut nappi3 = new Laskut();
            try {
                nappi3.start(new Stage());
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            alkuIkkuna.close();
        });

        Button dataNappi = new Button("Yleinen data");
        dataNappi.setStyle(nappiTyyli);
        dataNappi.setOnAction(e -> {
            YleinenData nappi4 = new YleinenData();
            try {
                nappi4.start(new Stage());
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            alkuIkkuna.close();
        });

        Label laapeli = new Label("Tervetuloa mökinvarausjärjestelmään!");
        laapeli.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button suljeNappi = new Button("Kirjaudu ulos");
        suljeNappi.setStyle(nappiTyyli);
        suljeNappi.setOnAction(e -> alkuIkkuna.close());

        HBox ylaNapit = new HBox(20, aloitaNappi, asiakasNappi);
        ylaNapit.setStyle("-fx-alignment: center;");

        HBox alaNapit = new HBox(20, laskutNappi, dataNappi, suljeNappi);
        alaNapit.setStyle("-fx-alignment: center;");

        Image mokkikuva = new Image(getClass().getResource("/kuva/mokki.png").toExternalForm());
        ImageView mokkiView = new ImageView(mokkikuva);
        mokkiView.setPreserveRatio(true);
        mokkiView.setFitWidth(300);
        mokkiView.setFitHeight(300);

        VBox sisalto = new VBox(20, laapeli, ylaNapit, mokkiView, alaNapit);
        sisalto.setStyle("-fx-alignment: center; -fx-padding: 20;");
        sisalto.setStyle("-fx-alignment: center; -fx-background-color: lightpink;");

        StackPane paneeli = new StackPane(sisalto);

        Scene aloitusKehys = new Scene(paneeli, 400, 500);
        alkuIkkuna.setScene(aloitusKehys);
        alkuIkkuna.setTitle("Mökinvarausjärjestelmä");
        alkuIkkuna.show();
    }
}
