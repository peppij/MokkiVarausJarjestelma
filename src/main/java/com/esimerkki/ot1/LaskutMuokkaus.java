package com.esimerkki.ot1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LaskutMuokkaus extends Application {
    private TextField muokkaaMaara = new TextField();
    private TextField laskunAsiakas = new TextField();
    private TextField lisaTiedot = new TextField();
    private CheckBox onkoMaksettu = new CheckBox();
    private Button tallenna = new Button("Tallenna");

    private Runnable paivitysCallback;

    public LaskutMuokkaus() {}

    public LaskutMuokkaus(Runnable callback) {
        this.paivitysCallback = callback;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Laskut valittu = Laskut.getValittu();
        GridPane gpPaneeli = new GridPane();
        gpPaneeli.setHgap(5);
        gpPaneeli.setVgap(5);

        muokkaaMaara.setText(String.valueOf(valittu.getMaara()));
        laskunAsiakas.setText(String.valueOf(valittu.getLaskunAsiakas()));
        lisaTiedot.setText(valittu.getLisaTiedot());
        onkoMaksettu.setSelected(valittu.getOnkoMaksettu());
        Label laskunumero1 = new Label("");
        laskunumero1.setText(String.format("%06d", valittu.getLaskuNumero()));

        Label laskuNumeroLabel = new Label("Lasku numero:");
        laskuNumeroLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(laskuNumeroLabel, 0, 0);
        gpPaneeli.add(laskunumero1, 1, 0);

        Label maaraLabel = new Label("Määrä");
        maaraLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(maaraLabel, 0, 2);
        gpPaneeli.add(muokkaaMaara, 1, 2);

        Label asiakasLabel = new Label("Asiakas:");
        asiakasLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(asiakasLabel, 0, 1);
        gpPaneeli.add(laskunAsiakas, 1, 1);

        Label lisatiedotLabel = new Label("Lisätiedot:");
        lisatiedotLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(lisatiedotLabel, 0, 3);
        gpPaneeli.add(lisaTiedot, 1, 3);

        Label maksettuLabel = new Label("Maksettu:");
        maksettuLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(maksettuLabel, 0, 4);
        gpPaneeli.add(onkoMaksettu, 1, 4);

        gpPaneeli.add(tallenna, 0, 6);

        gpPaneeli.setStyle("-fx-background-color: lightpink;");

        String nappiTyyli = "-fx-background-color: hotpink; " +
                "-fx-border-radius: 10; -fx-background-radius: 10; " +
                "-fx-text-fill: black; -fx-font-size: 14px;";
        tallenna.setStyle(nappiTyyli);

        gpPaneeli.setAlignment(Pos.CENTER);
        muokkaaMaara.setAlignment((Pos.BOTTOM_RIGHT));
        laskunAsiakas.setAlignment(Pos.BOTTOM_RIGHT);
        lisaTiedot.setAlignment(Pos.BOTTOM_RIGHT);

        tallenna.setOnAction(e -> {
            double maara = Double.parseDouble(muokkaaMaara.getText());
            String lisa = lisaTiedot.getText();
            String asiakas = laskunAsiakas.getText();
            boolean maksettu = onkoMaksettu.isSelected();

            valittu.setMaara(maara);
            valittu.setLisaTiedot(lisa);
            valittu.setLaskunAsiakas(asiakas);
            valittu.setOnkoMaksettu(maksettu);

            LaskuDAO dao = new LaskuDAO();
            dao.paivitaLasku(valittu);

            if (paivitysCallback != null) {
                paivitysCallback.run();
            }
            Laskut lasku = new Laskut();
            lasku.start(new Stage());

            primaryStage.close();
        });

        Scene scene = new Scene(gpPaneeli, 320, 340);
        primaryStage.setTitle("Muokkaa asiakastietoja");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}