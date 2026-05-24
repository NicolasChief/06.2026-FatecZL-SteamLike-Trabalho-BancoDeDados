package edu.curso.view;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuscaUI extends Application {

    private TextField fBusca = new TextField();

    private Button bBusca = new Button("Símbolo Lupa");

    private Label tTeste = new Label("Teste");

    public void start(Stage stage) {

        // Elementos Pane e Scene
        GridPane gp = new GridPane();

        BorderPane bp = new BorderPane();

        VBox vb = new VBox();

        HBox hb = new HBox();

        Scene sc = new Scene(gp, 1520, 780);

        // Tamanho


        // Adiciona ao Pane
        hb.getChildren().addAll(fBusca, bBusca);
        vb.getChildren().addAll(tTeste);
        gp.getChildren().addAll(bp);

        // Alinhamento GRID

        BorderPane.setAlignment(hb, Pos.CENTER);

        bp.setLeft(vb);
        bp.setRight(hb);

        //Inicia
        stage.setScene(sc);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(BuscaUI.class, args);
    }

}
