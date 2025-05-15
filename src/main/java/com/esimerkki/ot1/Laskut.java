package com.esimerkki.ot1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.Serializable;
import java.util.List;

public class Laskut extends Application implements Serializable {

    private int laskuNumero;
    private double maara;
    private boolean onkoMaksettu;
    private String lisaTiedot;
    private String laskunAsiakas;
    public static Laskut[] tilit = new Laskut[10];
    public static Laskut valittu;

    public static void lataaTiedotTietokannasta() {
        List<Laskut> lista = LaskuDAO.haeKaikkiLaskut();
        tilit = new Laskut[10];

        for (int i = 0; i < lista.size() && i < 10; i++) {
            tilit[i] = lista.get(i);
        }

        if (tilit[0] != null) {
            valittu = tilit[0];
        }
    }

    public static Laskut getValittu() {
        return valittu;
    }

    public static void setValittu(Laskut valittu) {
        Laskut.valittu = valittu;
    }

    public Laskut() {
        this.laskuNumero = 0;
        this.maara = 0.0;
        this.onkoMaksettu = false;
        this.lisaTiedot = "";
    }
    public Laskut(int laskuNumero, double maara, boolean onkoMaksettu, String lisaTiedot, String laskunAsiakas) {
        this.laskuNumero = laskuNumero;
        this.maara = maara;
        this.onkoMaksettu = onkoMaksettu;
        this.lisaTiedot = lisaTiedot;
        this.laskunAsiakas = laskunAsiakas;
    }

    public String getLaskunAsiakas() {
        return laskunAsiakas;
    }

    public void setLaskunAsiakas(String laskunAsiakas) {
        this.laskunAsiakas = laskunAsiakas;
    }

    public double getMaara() {
        return maara;
    }

    public void setMaara(double maara){
        this.maara = maara;
    }

    public boolean getOnkoMaksettu() {
        return onkoMaksettu;
    }

    public void setOnkoMaksettu(boolean onkoMaksettu) {
        this.onkoMaksettu = onkoMaksettu;
    }

    public String getLisaTiedot() {
        return lisaTiedot;
    }

    public void setLisaTiedot(String lisaTiedot) {
        this.lisaTiedot = lisaTiedot;
    }

    public int getLaskuNumero() {
        return laskuNumero;
    }

    public void setLaskuNumero(int laskuNumero) {
        this.laskuNumero = laskuNumero;
    }

    public void paivittaja(Text ylin, Text keskimmainen, Text keskimmaisempi, Text alempi, Text alin) {
        if (valittu != null) {
            ylin.setText("Asiakkaan " + valittu.getLaskunAsiakas() + " tiedot:");
            keskimmainen.setText("Summa: " + String.format("%.2f", valittu.getMaara()) + "€");
            keskimmaisempi.setText("Onko maksettu: " + valittu.getOnkoMaksettu());
            alempi.setText("Asiakasnumero: " + String.format("%06d", valittu.getLaskuNumero()));
            alin.setText("Lisätiedot: " + valittu.getLisaTiedot());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        lataaTiedotTietokannasta();
        Text ylin = new Text();
        Text alempi = new Text();
        Text keskimmainen = new Text();
        Text keskimmaisempi = new Text();
        Text alin = new Text();
        Button muokkaa = new Button("Muokkaa");
        Button poista = new Button("Poista");
        Button takaisin = new Button("Takaisin");
        takaisin.setAlignment(Pos.CENTER_RIGHT);
        HBox hbox2 = new HBox(10, muokkaa, poista);
        hbox2.setAlignment(Pos.CENTER_RIGHT);

        paivittaja(ylin, keskimmainen, keskimmaisempi, alempi, alin);

        ObservableList<Laskut> vaihtoehdot = FXCollections.observableArrayList();
        for (Laskut l : tilit) {
            if (l != null) {
                vaihtoehdot.add(l);
            }
        }

        ListView<Laskut> nakuma = new ListView<>(vaihtoehdot);
        nakuma.setPrefWidth(150);

        nakuma.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Laskut item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getLaskuNumero() + " - " + item.getLaskunAsiakas());
                }
            }
        });

        nakuma.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                valittu = newVal;
                paivittaja(ylin, keskimmainen, keskimmaisempi, alempi, alin);
            }
        });

        poista.setOnAction(actionEvent -> {
            if (valittu != null) {
                LaskuDAO laskuDAO = new LaskuDAO();
                valittu.tyhjenna();
                laskuDAO.paivitaLasku(valittu);

                vaihtoehdot.clear();
                for (Laskut l : tilit) {
                    if (l != null) {
                        vaihtoehdot.add(l);
                    }
                }

                paivittaja(ylin, keskimmainen, keskimmaisempi, alempi, alin);
            }
        });

        takaisin.setOnAction(actionEvent -> {
            AloitusRuutu takasNappi = new AloitusRuutu();
            try {
                takasNappi.start(new Stage());
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            primaryStage.close();
        });

        muokkaa.setOnAction(actionEvent -> {
            LaskutMuokkaus muokkaaNappi = new LaskutMuokkaus();
            try {
                muokkaaNappi.start(new Stage());
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            primaryStage.close();
        });

        VBox vbox = new VBox(5, ylin, keskimmainen, keskimmaisempi, alempi, alin);
        vbox.setPrefSize(300, 200);

        BorderPane pane = new BorderPane();
        pane.setLeft(nakuma);
        pane.setCenter(vbox);
        pane.setBottom(hbox2);
        pane.setRight(takaisin);
        BorderPane.setMargin(hbox2, new Insets(0, 0, 5, 5));
        BorderPane.setMargin(vbox, new Insets(5, 0, 0, 5));
        BorderPane.setMargin(hbox2, new Insets(0, 30, 5, 0));
        BorderPane.setMargin(takaisin, new Insets(5, 5, 0, 0));
        pane.setStyle("-fx-background-color: lightpink;");

        String nappiTyyli = "-fx-background-color: hotpink; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: black;";
        muokkaa.setStyle(nappiTyyli);
        poista.setStyle(nappiTyyli);
        takaisin.setStyle(nappiTyyli);

        ylin.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        keskimmainen.setStyle("-fx-font-size: 14px;");
        keskimmaisempi.setStyle("-fx-font-size: 14px;");
        alempi.setStyle("-fx-font-size: 14px;");
        alin.setStyle("-fx-font-size: 14px;");

        Scene scene = new Scene(pane, 580, 280);
        primaryStage.setTitle("Laskujen tiedot:");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void tyhjenna() {
        valittu.setLaskunAsiakas("Ei tietoja");
        valittu.setLisaTiedot("");
        valittu.setOnkoMaksettu(false);
        valittu.setMaara(0);
    }
}
