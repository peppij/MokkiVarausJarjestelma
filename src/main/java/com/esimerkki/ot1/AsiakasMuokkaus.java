package com.esimerkki.ot1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AsiakasMuokkaus extends Application {
    private TextField muokkaaPuhelinnumero = new TextField();
    private TextField muokkaaSahkoposti = new TextField();
    private TextField muokkaaNimi = new TextField();
    private CheckBox onkoyritysasiakas = new CheckBox();
    private Button tallenna = new Button("Tallenna");
    private int asiakasNumero;

    private Runnable paivitysCallback;

    public AsiakasMuokkaus() {}

    public AsiakasMuokkaus(Runnable callback) {
        this.paivitysCallback = callback;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AsiakasTiedot valittu = AsiakasTiedot.getValittu();
        GridPane gpPaneeli = new GridPane();
        gpPaneeli.setHgap(5);
        gpPaneeli.setVgap(5);

        asiakasNumero = valittu.getAsiakasNumero();
        muokkaaPuhelinnumero.setText(valittu.getPuhelinnumero());
        muokkaaSahkoposti.setText(String.valueOf(valittu.getSahkoposti()));
        muokkaaNimi.setText(valittu.getAsiakkaanNimi());
        onkoyritysasiakas.setSelected(valittu.getYritysAsiakas());

        Label asiakasnumero1 = new Label("");
        asiakasnumero1.setText(String.format("%06d", valittu.getAsiakasNumero()));

        Label asiakasnumeroLabel = new Label("Asiakasnumero:");
        asiakasnumeroLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(asiakasnumeroLabel, 0, 0);
        gpPaneeli.add(asiakasnumero1, 1, 0);

        Label nimiLabel = new Label("Nimi:");
        nimiLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(nimiLabel, 0, 1);
        gpPaneeli.add(muokkaaNimi, 1, 1);

        Label sahkopostiLabel = new Label("Sähköposti:");
        sahkopostiLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(sahkopostiLabel, 0, 2);
        gpPaneeli.add(muokkaaSahkoposti, 1, 2);

        Label puhelinnumeroLabel = new Label("Puhelinnumero:");
        puhelinnumeroLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(puhelinnumeroLabel, 0, 3);
        gpPaneeli.add(muokkaaPuhelinnumero, 1, 3);

        Label yritysasiakasLabel = new Label("Yritysasiakas:");
        yritysasiakasLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(yritysasiakasLabel, 0, 4);
        gpPaneeli.add(onkoyritysasiakas, 1, 4);

        gpPaneeli.add(tallenna, 0, 6);

        gpPaneeli.setStyle("-fx-background-color: lightpink;");

        String nappiTyyli = "-fx-background-color: hotpink; " +
                "-fx-border-radius: 10; -fx-background-radius: 10; " +
                "-fx-text-fill: black; -fx-font-size: 14px;";
        tallenna.setStyle(nappiTyyli);

        gpPaneeli.setAlignment(Pos.CENTER);
        muokkaaPuhelinnumero.setAlignment((Pos.BOTTOM_RIGHT));
        muokkaaSahkoposti.setAlignment(Pos.BOTTOM_RIGHT);
        muokkaaNimi.setAlignment(Pos.BOTTOM_RIGHT);

        tallenna.setOnAction(e -> {
            String sahkoposti = muokkaaSahkoposti.getText();
            String nimi = muokkaaNimi.getText();
            String puhnro = muokkaaPuhelinnumero.getText();
            boolean yritys = onkoyritysasiakas.isSelected();

            valittu.setAsiakkaanNimi(nimi);
            valittu.setPuhelinnumero(puhnro);
            valittu.setSahkoposti(sahkoposti);
            valittu.setYritysAsiakas(yritys);

            AsiakasTiedotDAO dao = new AsiakasTiedotDAO();
            dao.paivitaAsiakkaat(valittu);

            if (paivitysCallback != null) {
                paivitysCallback.run();
            }

            AsiakasTiedot asiakas = new AsiakasTiedot();
            asiakas.start(new Stage());

            primaryStage.close();
        });

        Scene scene = new Scene(gpPaneeli, 320, 340);
        primaryStage.setTitle("Muokkaa asiakastietoja");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
