package com.esimerkki.ot1;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.Serializable;
import java.util.List;

public class MokkiTiedot extends Application implements Serializable {

    public int mokinNumero;
    public double pintaAla;
    public boolean onkoVarattu;
    public String lisaominaisuudet;
    public String mokinNimi;
    public static MokkiTiedot[] tilit = new MokkiTiedot[10];
    public static MokkiTiedot valittu;

    public static void lataaTiedotMokki() {
        List<MokkiTiedot> lista = MokkiTiedotDAO.haeKaikkiMokit();
        tilit = new MokkiTiedot[10];

        for (int i = 0; i < lista.size() && i < 10; i++) {
            tilit[i] = lista.get(i);
        }

        if (tilit[0] != null) {
            valittu = tilit[0];
        }
    }

    public static MokkiTiedot getValittu() {
        return valittu;
    }

    public static void setValittu(MokkiTiedot valittu) {
        MokkiTiedot.valittu = valittu;
    }

    public MokkiTiedot() {
        this.mokinNumero = 0;
        this.pintaAla = 0;
        this.onkoVarattu = false;
        this.lisaominaisuudet = "";
    }
    public MokkiTiedot(int mokinNumero, double pintaAla, boolean onkoVarattu, String lisaominaisuudet, String mokinNimi) {
        this.mokinNumero = mokinNumero;
        this.pintaAla = pintaAla;
        this.onkoVarattu = onkoVarattu;
        this.lisaominaisuudet = lisaominaisuudet;
        this.mokinNimi = mokinNimi;
    }

    public String getMokinNimi() {
        return mokinNimi;
    }

    public void setMokinNimi(String mokinNimi) {
        this.mokinNimi = mokinNimi;
    }

    public double getPintaAla() {
        return pintaAla;
    }

    public void setPintaAla(double pintaAla) {
        this.pintaAla = pintaAla;
    }

    public boolean getOnkoVarattu() {
        return onkoVarattu;
    }

    public void setOnkoVarattu(boolean onkoVarattu) {
        this.onkoVarattu = onkoVarattu;
    }

    public String getLisaominaisuudet() {
        return lisaominaisuudet;
    }

    public void setLisaominaisuudet(String lisaominaisuudet) {
        this.lisaominaisuudet = lisaominaisuudet;
    }

    public int getMokinNumero() {
        return mokinNumero;
    }

    public void setMokinNumero(int mokinNumero) {
        this.mokinNumero = mokinNumero;
    }

    public void paivittaja(Text ylin, Text keskimmainen, Text keskimmaisempi, Text alempi) {
        if (valittu != null) {
            ylin.setText("Mökin " + valittu.getMokinNimi() + " tiedot:");
            keskimmainen.setText("Onko varattu: " + valittu.getOnkoVarattu());
            keskimmaisempi.setText("Pinta-ala: " + valittu.getPintaAla());
            alempi.setText("Lisäominaisuudet: " + valittu.getLisaominaisuudet());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        lataaTiedotMokki();
        Text ylin = new Text();
        Text alempi = new Text();
        Text keskimmainen = new Text();
        Text keskimmaisempi = new Text();
        Button muokkaa = new Button("Muokkaa");
        Button poista = new Button("Poista");
        Button takaisin = new Button("Takaisin");
        takaisin.setAlignment(Pos.CENTER_RIGHT);
        HBox hbox2 = new HBox(10, muokkaa, poista);
        hbox2.setAlignment(Pos.CENTER_RIGHT);

        paivittaja(ylin, keskimmainen, keskimmaisempi, alempi);

        ObservableList<String> vaihtoehdot = FXCollections.observableArrayList();
        for (MokkiTiedot l : tilit) {
            if (l != null) {
                vaihtoehdot.add(l.getMokinNimi());
            }
        }

        ListView<String> nakuma = new ListView<>(vaihtoehdot);
        nakuma.setPrefWidth(150);

        nakuma.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            for (MokkiTiedot mokki : tilit) {
                if (mokki != null && mokki.getMokinNimi().equals(newValue)) {
                    valittu = mokki;
                }
            }
            paivittaja(ylin, keskimmainen, keskimmaisempi, alempi);
        });

        poista.setOnAction(actionEvent -> {
            if (valittu != null) {
                MokkiTiedotDAO mokkiDAO = new MokkiTiedotDAO();
                valittu.tyhjenna();
                MokkiTiedotDAO.paivitaMokit(valittu);

                // Päivitä lista käyttöliittymässä
                vaihtoehdot.clear();
                for (MokkiTiedot l : tilit) {
                    if (l != null) {
                        vaihtoehdot.add(l.getMokinNimi());
                    }
                }

                paivittaja(ylin, keskimmainen, keskimmaisempi, alempi);
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
            MokkiMuokkaus muokkaaNappi = new MokkiMuokkaus();
            try {
                muokkaaNappi.start(new Stage());
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            primaryStage.close();
        });

        VBox vbox = new VBox(5, ylin, keskimmainen, keskimmaisempi, alempi);
        vbox.setPrefSize(300, 200);

        BorderPane pane = new BorderPane();
        pane.setLeft(nakuma);
        pane.setCenter(vbox);
        pane.setBottom(hbox2);
        pane.setRight(takaisin);
        pane.setStyle("-fx-background-color: lightpink;");
        BorderPane.setMargin(hbox2, new Insets(0, 0, 5, 5));
        BorderPane.setMargin(vbox, new Insets(5, 0, 0, 5));
        BorderPane.setMargin(hbox2, new Insets(0, 30, 5, 0));
        BorderPane.setMargin(takaisin, new Insets(5, 5, 0, 0));

        String nappiTyyli = "-fx-background-color: hotpink; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: black;";
        muokkaa.setStyle(nappiTyyli);
        poista.setStyle(nappiTyyli);
        takaisin.setStyle(nappiTyyli);

        ylin.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        keskimmainen.setStyle("-fx-font-size: 14px;");
        keskimmaisempi.setStyle("-fx-font-size: 14px;");
        alempi.setStyle("-fx-font-size: 14px;");

        Scene scene = new Scene(pane, 580, 280);
        primaryStage.setTitle("Mökkien tiedot:");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    void tyhjenna() {
        valittu.setMokinNimi("Ei tietoja");
        valittu.setLisaominaisuudet("");
        valittu.setOnkoVarattu(false);
        valittu.setPintaAla(0);
    }
}
