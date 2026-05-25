package edu.curso.view;

import edu.curso.model.Jogo;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuscaUI extends Application {

    private TextField fBusca = new TextField();

    private Button bBusca = new Button("🔍");

    private Label tBiblioteca = new Label("Biblioteca de Jogos auauauauauauau");

    private TableView<Jogo> tvCatalago = new TableView<>();

    public void start(Stage stage) {

        // Elementos Pane e Scene
        GridPane gp = new GridPane();

        BorderPane bp = new BorderPane();

        VBox vbM = new VBox();

        VBox vbC = new VBox();

        HBox hb = new HBox();
        hb.setAlignment(Pos.TOP_RIGHT);

        ScrollPane sp = new ScrollPane(vbM);

        Scene sc = new Scene(gp, 1520, 780);

        // Table View
        TableColumn<Jogo, String> colNome = new TableColumn<>("Nome do Jogo");

        // Tamanho


        // Adiciona ao Pane        
        vbM.getChildren().add(tBiblioteca);
        hb.getChildren().addAll(fBusca, bBusca);
        vbC.getChildren().addAll(hb, tvCatalago);
        gp.getChildren().addAll(bp, vbC);

        // Lista da Biblioteca
        for (int i = 1; i <= 50; i++) {
            vbM.getChildren().add(new Button());
        }

        // Alinhamento GRID
        sp.setFitToHeight(true);

        hb.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(hb, Pos.CENTER);

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
