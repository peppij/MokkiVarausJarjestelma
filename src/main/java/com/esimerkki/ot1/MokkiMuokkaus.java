package com.esimerkki.ot1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class MokkiMuokkaus extends Application {
    private TextField muokkaaNimi = new TextField();
    private TextField muokkaaAla = new TextField();
    private TextField muokkaalisaominaisuudet = new TextField();
    private CheckBox onkoVarattu = new CheckBox();
    private Button tallenna = new Button("Tallenna");
    private Label mokinNumero = new Label();

    private Runnable paivitysCallback;

    public MokkiMuokkaus() {}

    public MokkiMuokkaus(Runnable callback) {
        this.paivitysCallback = callback;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MokkiTiedot valittu = MokkiTiedot.getValittu();
        GridPane gpPaneeli = new GridPane();
        gpPaneeli.setHgap(5);
        gpPaneeli.setVgap(5);;

        muokkaaNimi.setText(valittu.getMokinNimi());
        muokkaaAla.setText(String.valueOf(valittu.getPintaAla()));
        muokkaalisaominaisuudet.setText(valittu.getLisaominaisuudet());
        onkoVarattu.setSelected(valittu.getOnkoVarattu());
        mokinNumero.setText(String.valueOf(valittu.getMokinNumero()));

        Label moKinNumeroLabel = new Label("Mökin numero:");
        moKinNumeroLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(moKinNumeroLabel, 0, 0);
        gpPaneeli.add(mokinNumero, 1, 0);

        Label nimiLabel = new Label("Nimi:");
        nimiLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(nimiLabel, 0, 1);
        gpPaneeli.add(muokkaaNimi, 1, 1);

        Label pintaAlaLabel = new Label("Pinta-ala:");
        pintaAlaLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(pintaAlaLabel, 0, 2);
        gpPaneeli.add(muokkaaAla, 1, 2);

        Label lisaominaisuudetLabel = new Label("Lisäominaisuudet:");
        lisaominaisuudetLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(lisaominaisuudetLabel, 0, 3);
        gpPaneeli.add(muokkaalisaominaisuudet, 1, 3);

        Label varattuLabel = new Label("Varattu:");
        varattuLabel.setStyle("-fx-font-weight: bold;");
        gpPaneeli.add(varattuLabel, 0, 4);
        gpPaneeli.add(onkoVarattu, 1, 4);

        gpPaneeli.add(tallenna, 0, 6);
        gpPaneeli.setStyle("-fx-background-color: lightpink;");

        String nappiTyyli = "-fx-background-color: hotpink; " +
                "-fx-border-radius: 10; -fx-background-radius: 10; " +
                "-fx-text-fill: black; -fx-font-size: 14px;";
        tallenna.setStyle(nappiTyyli);

        gpPaneeli.setAlignment(Pos.CENTER);
        muokkaaNimi.setAlignment((Pos.BOTTOM_RIGHT));
        muokkaaAla.setAlignment(Pos.BOTTOM_RIGHT);
        muokkaalisaominaisuudet.setAlignment(Pos.BOTTOM_RIGHT);


        tallenna.setOnAction(e -> {
            double pintaAla = Double.parseDouble(muokkaaAla.getText());
            String lisa = muokkaalisaominaisuudet.getText();
            String asiakas = muokkaaNimi.getText();
            boolean varattu = onkoVarattu.isSelected();

            valittu.setPintaAla(pintaAla);
            valittu.setLisaominaisuudet(lisa);
            valittu.setMokinNimi(asiakas);
            valittu.setOnkoVarattu(varattu);

            MokkiTiedotDAO dao = new MokkiTiedotDAO();
            dao.paivitaMokit(valittu);

            if (paivitysCallback != null) {
                paivitysCallback.run();
            }
            MokkiTiedot praytogod = new MokkiTiedot();
            praytogod.start(new Stage());

            primaryStage.close();
        });

        Scene scene = new Scene(gpPaneeli, 320, 340);
        primaryStage.setTitle("Muokkaa mökkitietoja");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}