package edu.curso.view;

import edu.curso.model.Jogo;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuscaUI extends Application {

    private TextField fBusca = new TextField();

    private Button bBusca = new Button("🔍");

    private Label tBiblioteca = new Label("Biblioteca de Jogos");

    private TableView<Jogo> tvCatalago = new TableView<>();

    public void start(Stage stage) {

        // Elementos Pane e Scene
        Jogo jogo = new Jogo(STYLESHEET_CASPIAN, null, 0, 0, STYLESHEET_CASPIAN, STYLESHEET_CASPIAN, false, STYLESHEET_MODENA, STYLESHEET_CASPIAN);

        BorderPane bp = new BorderPane();

        VBox vbM = new VBox();

        VBox vbC = new VBox();

        HBox hb = new HBox();

        ScrollPane sp = new ScrollPane(vbM);

        Scene sc = new Scene(bp, 1520, 780);

        // Table View
        TableColumn<Jogo, String> colNome = new TableColumn<>("Nome do Jogo");
        colNome.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getNome())
        );
        TableColumn<Jogo, Double> colPreco = new TableColumn<>("Preço do Jogo");
        colPreco.setCellValueFactory(
            itemData -> new ReadOnlyDoubleWrapper(itemData.getValue().getPreco()).asObject()
        );
        TableColumn<Jogo, String> colStatus = new TableColumn<>("Status de Aquisição");
        colStatus.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getStatusAquicicao() ? "Adquirido" : "Não adquirido")
        );
        TableColumn<Jogo, String> colPub = new TableColumn<>("Publicadora");
        colPub.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getPublicadora())
        );
        TableColumn<Jogo, String> colDes = new TableColumn<>("Desenvolvedora");
        colDes.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getDesenvolvedora())
        );

        // Tamanho

        tvCatalago.prefWidthProperty().bind(vbC.widthProperty());
        tvCatalago.prefHeightProperty().bind(vbC.heightProperty());

        vbC.prefWidthProperty().bind(bp.widthProperty().subtract(sp.getPrefWidth()));
        vbC.prefHeightProperty().bind(bp.heightProperty());

        fBusca.setPrefWidth(600);

        sp.setPrefWidth(250);

        colNome.setPrefWidth(400);
        colPreco.setPrefWidth(170);
        colStatus.setPrefWidth(150);
        colPub.setPrefWidth(275);
        colDes.setPrefWidth(275);

        // Margem
        HBox.setMargin(bBusca, new Insets(20, 0, 20, 0));

        //Adiciona ao Table

        tvCatalago.getColumns().add(colNome);
        tvCatalago.getColumns().add(colPreco);
        tvCatalago.getColumns().add(colStatus);
        tvCatalago.getColumns().add(colPub);
        tvCatalago.getColumns().add(colDes);

        // Adiciona ao Pane        
        vbM.getChildren().add(tBiblioteca);
        hb.getChildren().addAll(fBusca, bBusca);
        vbC.getChildren().addAll(hb, tvCatalago);

        bp.setLeft(sp);
        bp.setRight(vbC);

        VBox.setVgrow(tvCatalago, Priority.ALWAYS);

        // Lista da Biblioteca
        for (int i = 1; i <= 50; i++) {
            vbM.getChildren().add(new Button(jogo.getNome()));
        }

        // Alinhamento
        sp.setFitToHeight(true);

        hb.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(hb, Pos.CENTER);

        vbC.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(vbC, Pos.CENTER);

        bp.setLeft(sp);
        bp.setRight(vbC);

        //Inicia
        stage.setScene(sc);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(BuscaUI.class, args);
    }

}
