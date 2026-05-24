package edu.curso.view;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastrarUI extends Application {
    
    private TextField fNome = new TextField();
    private TextField fSenha = new TextField();

    private Label tNome = new Label("Usuário: ");
    private Label tSenha = new Label("Senha: ");

    private Button bEntrar = new Button("Acessar");
    private Button bCadastrar = new Button("Primeira Vez?");

    public void start(Stage stage){

        // Elementos Pane e Scene
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(40);

        HBox hbN = new HBox(20);
        hbN.setAlignment(Pos.CENTER);

        HBox hbS = new HBox(20);
        hbS.setAlignment(Pos.CENTER);

        HBox hbO = new HBox(40);

        VBox vb = new VBox(20);
        
        Scene sc = new Scene(gp, 1520, 780);

        // Tamanho
        fNome.setPrefSize(500, 50);
        fSenha.setPrefSize(500, 50);

        bEntrar.setPrefSize(250, 50);
        bCadastrar.setPrefSize(250, 50);

        // Adiciona ao Pane
        hbN.getChildren().addAll(tNome, fNome);
        hbS.getChildren().addAll(tSenha, fSenha);
        hbO.getChildren().addAll(bEntrar, bCadastrar);

        vb.getChildren().addAll(hbN, hbS, hbO);

        gp.add(vb, 0, 0);

        // Alinhamento GRID
        GridPane.setHalignment(vb, HPos.CENTER);
        GridPane.setValignment(vb, VPos.CENTER);

        //Inicia
        stage.setScene(sc);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(CadastrarUI.class, args);
    }

}
