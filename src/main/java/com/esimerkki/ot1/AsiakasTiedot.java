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

public class AsiakasTiedot extends Application implements Serializable {

    private int asiakasNumero;
    private String puhelinnumero;
    private boolean yritysAsiakas;
    private String sahkoposti;
    private String asiakkaanNimi;
    public static AsiakasTiedot[] tilit = new AsiakasTiedot[10];
    public static AsiakasTiedot valittu;

    public static void lataaTiedotAsiakas(){
        List<AsiakasTiedot> lista = AsiakasTiedotDAO.haeKaikkiAsiakkaat();
        tilit = new AsiakasTiedot[10];

        for (int i = 0; i < lista.size() && i < 10; i++){
            tilit[i] = lista.get(i);
        }

        if (tilit[0] != null) {
            valittu = tilit[0];
        }
    }

    public AsiakasTiedot() {
        this.asiakasNumero = 0;
        this.puhelinnumero = "0";
        this.yritysAsiakas = false;
        this.sahkoposti = "";
    }
    public AsiakasTiedot(int asiakasNumero, String puhelinnumero, boolean yritysAsiakas, String sahkoposti, String asiakkaanNimi) {
        this.asiakasNumero = asiakasNumero;
        this.puhelinnumero = puhelinnumero;
        this.yritysAsiakas = yritysAsiakas;
        this.sahkoposti = sahkoposti;
        this.asiakkaanNimi = asiakkaanNimi;
    }

    public static AsiakasTiedot getValittu() {
        return valittu;
    }

    public static void setValittu(AsiakasTiedot valittu) {
        AsiakasTiedot.valittu = valittu;
    }

    public String getAsiakkaanNimi() {
        return asiakkaanNimi;
    }

    public void setAsiakkaanNimi(String asiakkaanNimi) {
        this.asiakkaanNimi = asiakkaanNimi;
    }

    public String getPuhelinnumero() {
        return puhelinnumero;
    }

    public void setPuhelinnumero(String puhelinnumero) {
        this.puhelinnumero = puhelinnumero;
    }

    public boolean getYritysAsiakas() {
        return yritysAsiakas;
    }

    public void setYritysAsiakas(boolean yritysAsiakas) {
        this.yritysAsiakas = yritysAsiakas;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

    public int getAsiakasNumero() {
        return asiakasNumero;
    }

    public void setAsiakasNumero(int asiakasNumero) {
        this.asiakasNumero = asiakasNumero;
    }

    public void paivittaja(Text ylin, Text keskimmainen, Text keskimmaisempi, Text alempi, Text alin) {
        if (valittu != null) {
            ylin.setText("Asiakkaan " + valittu.getAsiakkaanNimi() + " tiedot:");
            keskimmainen.setText("Sähköpostiosoite: " + valittu.getSahkoposti());
            keskimmaisempi.setText("Puhelinnumero: " + valittu.getPuhelinnumero());
            alempi.setText("Yritysasiakas: " + valittu.getYritysAsiakas());
            alin.setText("Asiakasnumero: " + String.format("%06d", valittu.getAsiakasNumero()));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        lataaTiedotAsiakas();
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

        ObservableList<AsiakasTiedot> vaihtoehdot = FXCollections.observableArrayList();
        for (AsiakasTiedot l : tilit) {
            if (l != null) {
                vaihtoehdot.add(l);
            }
        }

        ListView<AsiakasTiedot> nakuma = new ListView<>(vaihtoehdot);
        nakuma.setPrefWidth(150);

        nakuma.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(AsiakasTiedot item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getAsiakasNumero() + " - " + item.getAsiakkaanNimi());
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
              AsiakasTiedotDAO asiakasDAO = new AsiakasTiedotDAO();
              valittu.tyhjenna();
              AsiakasTiedotDAO.paivitaAsiakkaat(valittu);

              vaihtoehdot.clear();
              for (AsiakasTiedot l : tilit) {
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
            AsiakasMuokkaus muokkaaNappi = new AsiakasMuokkaus();
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
        primaryStage.setTitle("Mökkien tiedot:");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void tyhjenna() {
        valittu.setAsiakkaanNimi("Ei tietoja");
        valittu.setSahkoposti("");
        valittu.setYritysAsiakas(false);
        valittu.setPuhelinnumero("0");
    }
}