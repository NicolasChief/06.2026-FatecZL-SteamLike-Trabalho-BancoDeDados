package edu.curso.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastroTipoUI extends Application {

    @Override
    public void start(Stage stage) {
        // Elementos
        Label titulo = new Label("Selecione o tipo de cadastro:");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button bUsuario = new Button("Usuário");
        Button bDesenvolvedor = new Button("Desenvolvedor");
        Button bCancelar = new Button("Cancelar");

        // Estilo dos botões
        bUsuario.setPrefWidth(200);
        bDesenvolvedor.setPrefWidth(200);
        bCancelar.setPrefWidth(200);

        bUsuario.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        bDesenvolvedor.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        bCancelar.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // Container
        VBox vb = new VBox();
        vb.getChildren().addAll(titulo, bUsuario, bDesenvolvedor, bCancelar);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        vb.setPadding(new Insets(40));

        Scene scene = new Scene(vb, 500, 400);

        // Ações
        bUsuario.setOnAction(event -> {
            stage.close();
            new CadastrarUsuarioUI().start(new Stage());
        });

        bDesenvolvedor.setOnAction(event -> {
            stage.close();
            new CadastrarDesenvolvedorUI().start(new Stage());
        });

        bCancelar.setOnAction(event -> stage.close());

        // Inicializar
        stage.setTitle("Cadastro");
        stage.setScene(scene);
        stage.show();
    }

}
