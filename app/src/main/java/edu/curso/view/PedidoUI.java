package edu.curso.view;

import java.util.Date;

import edu.curso.banco.UsuarioDAOImpl;
import edu.curso.model.Jogo;
import edu.curso.model.JogoAdquirido;
import edu.curso.model.Usuario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    public static void mostrarPedido(Stage stage, Jogo jogoParam, Usuario userParam) {
        Jogo jogo = jogoParam;
        Usuario user = userParam;
        
        if (jogo == null) {
            jogo = new Jogo(null, null, 0, 0, null, null, false, null, null);
        }
        if (user == null) {
            user = new Usuario("Cliente", null, "cliente@teste.com", null, null, 0);
        }
        
        final Jogo jogoFinal = jogo;
        final Usuario userFinal = user;

        Label tTitulo = new Label("Detalhes da Compra");
        tTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label tNomeLabel = new Label("Jogo: ");
        tNomeLabel.setStyle("-fx-font-weight: bold;");
        Label tNome = new Label(jogoFinal.getNome() != null ? jogoFinal.getNome() : "N/A");

        Label tDescLabel = new Label("Descrição: ");
        tDescLabel.setStyle("-fx-font-weight: bold;");
        Label tDesc = new Label(jogoFinal.getDescricaoJogo() != null ? jogoFinal.getDescricaoJogo() : "N/A");
        tDesc.setWrapText(true);
        tDesc.setMaxWidth(400);

        Label tPrecoLabel = new Label("Preço do Jogo: R$ ");
        tPrecoLabel.setStyle("-fx-font-weight: bold;");
        Label tPreco = new Label(String.format("%.2f", jogoFinal.getPreco()));

        Label tSaldoLabel = new Label("Seu Saldo: R$ ");
        tSaldoLabel.setStyle("-fx-font-weight: bold;");
        Label tSaldo = new Label(String.format("%.2f", userFinal.getSaldoConta()));

        Button bComprar = new Button("Realizar Compra");
        Button bVoltar = new Button("Cancelar Compra");

        bComprar.setPrefWidth(150);
        bVoltar.setPrefWidth(150);

        bVoltar.setOnAction(event -> stage.close());

        bComprar.setOnAction(event -> {
            double saldoUsuario = userFinal.getSaldoConta();
            double precoJogo = jogoFinal.getPreco();

            if (saldoUsuario < precoJogo) {
                new Alert(AlertType.WARNING, 
                    "Saldo insuficiente!\n\nPreço do jogo: R$ " + String.format("%.2f", precoJogo) +
                    "\nSeu saldo: R$ " + String.format("%.2f", saldoUsuario) +
                    "\nDiferença: R$ " + String.format("%.2f", precoJogo - saldoUsuario)).show();
                return;
            }

            try {
                double novoSaldo = saldoUsuario - precoJogo;
                userFinal.setSaldoConta(novoSaldo);

                new UsuarioDAOImpl().atualizarSaldo(userFinal.getNome(), novoSaldo);

                @SuppressWarnings("unused")
                JogoAdquirido jogoAdquirido = new JogoAdquirido(new Date(), jogoFinal);

                new Alert(AlertType.INFORMATION, 
                    "Compra realizada com sucesso!\n\n" +
                    "Jogo: " + jogoFinal.getNome() +
                    "\nValor pago: R$ " + String.format("%.2f", precoJogo) +
                    "\nNovo saldo: R$ " + String.format("%.2f", novoSaldo)).show();

                stage.close();
            } catch (RuntimeException e) {
                new Alert(AlertType.ERROR, 
                    "Erro ao processar a compra: " + e.getMessage()).show();
            }
        });

        BorderPane bp = new BorderPane();
        VBox vbOrganizador = new VBox(15);
        VBox vbInfo = new VBox(10);
        HBox hbPreco = new HBox(5);
        HBox hbSaldo = new HBox(5);
        HBox hbBotoes = new HBox(20);

        hbPreco.getChildren().addAll(tPrecoLabel, tPreco);
        hbSaldo.getChildren().addAll(tSaldoLabel, tSaldo);
        vbInfo.getChildren().addAll(tNomeLabel, tNome, tDescLabel, tDesc, hbPreco, hbSaldo);
        hbBotoes.getChildren().addAll(bVoltar, bComprar);
        vbOrganizador.getChildren().addAll(tTitulo, vbInfo, hbBotoes);

        vbOrganizador.setAlignment(Pos.TOP_CENTER);
        vbInfo.setAlignment(Pos.TOP_LEFT);
        hbPreco.setAlignment(Pos.CENTER_LEFT);
        hbSaldo.setAlignment(Pos.CENTER_LEFT);
        hbBotoes.setAlignment(Pos.CENTER);
        vbOrganizador.setPadding(new Insets(20));

        bp.setCenter(vbOrganizador);

        Scene sc = new Scene(bp, 1520, 780);
        stage.setTitle("Detalhes do Pedido");
        stage.setScene(sc);
        stage.show();
    }

}
