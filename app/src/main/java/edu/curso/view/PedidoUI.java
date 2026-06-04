package edu.curso.view;

import edu.curso.model.Jogo;
import edu.curso.model.Usuario;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PedidoUI extends Application {
    
    private Button bComprar = new Button("Realizar Compra");

    private Button bVoltar = new Button("Cancelar Compra");

    private Label tNome = new Label();

    private Label tDesc = new Label();

    private Label tPreco = new Label();

    private Label tSaldo = new Label();

    public void start(Stage stage){

        Jogo jogo = new Jogo(null, null, 0, 0, null, null, false, null, null);

        Usuario user = new Usuario(STYLESHEET_CASPIAN, null, STYLESHEET_CASPIAN, STYLESHEET_MODENA, STYLESHEET_CASPIAN, 0);

        BorderPane bp = new BorderPane();

        VBox vbOrganizador = new VBox();

        HBox hbBotoes = new HBox();

        HBox hbValores = new HBox();

        Scene sc = new Scene(bp, 1520, 780); 

        // Renomear Textos e Precos
        tNome.setText(jogo.getNome());

        tPreco.setText(String.format("%.2f", jogo.getPreco()));

        tSaldo.setText(String.format("%.2f", user.getSaldoConta()));

        // Tamanho

        // Margem

        // Adiciona ao Pane
        hbBotoes.getChildren().addAll(bVoltar, bComprar);
        hbValores.getChildren().addAll(tPreco, tSaldo);

        vbOrganizador.getChildren().addAll(tNome, tDesc, hbValores, hbBotoes);

//        bp.setLeft(CapaJogo);
        bp.setRight(vbOrganizador);

        // Alinhamento
        hbValores.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(hbValores, Pos.CENTER);

        vbOrganizador.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(vbOrganizador, Pos.CENTER);

        // Inicia
        stage.setScene(sc);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
