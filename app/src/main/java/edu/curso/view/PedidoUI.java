package edu.curso.view;

import edu.curso.model.Jogo;
import edu.curso.model.Usuario;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PedidoUI extends Application {

    @Override
    public void start(Stage stage){
        Jogo jogo = new Jogo(null, null, 0, 0, null, null, false, null, null);
        Usuario user = new Usuario("Cliente", null, "cliente@teste.com", null, null, 0);
        mostrarPedido(stage, jogo, user);
    }

    public static void mostrarPedido(Stage stage, Jogo jogo, Usuario user) {
        if (jogo == null) {
            jogo = new Jogo(null, null, 0, 0, null, null, false, null, null);
        }
        if (user == null) {
            user = new Usuario("Cliente", null, "cliente@teste.com", null, null, 0);
        }

        Label tNome = new Label(jogo.getNome() != null ? jogo.getNome() : "");
        Label tDesc = new Label(jogo.getDescricaoJogo() != null ? jogo.getDescricaoJogo() : "");
        Label tPreco = new Label(String.format("%.2f", jogo.getPreco()));
        Label tSaldo = new Label(String.format("%.2f", user.getSaldoConta()));

        Button bComprar = new Button("Realizar Compra");
        Button bVoltar = new Button("Cancelar Compra");

        bVoltar.setOnAction(event -> stage.close());

        BorderPane bp = new BorderPane();
        VBox vbOrganizador = new VBox(20);
        HBox hbValores = new HBox(20);
        HBox hbBotoes = new HBox(20);

        hbValores.getChildren().addAll(tPreco, tSaldo);
        hbBotoes.getChildren().addAll(bVoltar, bComprar);
        vbOrganizador.getChildren().addAll(tNome, tDesc, hbValores, hbBotoes);

        vbOrganizador.setAlignment(Pos.CENTER);
        hbValores.setAlignment(Pos.CENTER);
        hbBotoes.setAlignment(Pos.CENTER);

        bp.setRight(vbOrganizador);

        Scene sc = new Scene(bp, 1520, 780);
        stage.setScene(sc);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
