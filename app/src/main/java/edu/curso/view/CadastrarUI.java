package edu.curso.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import edu.curso.control.LoginUC;
import edu.curso.model.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastrarUI extends Application {

    private TextField fNome = new TextField();
    private TextField fData = new TextField();
    private TextField fEmail = new TextField();
    private TextField fSenha = new TextField();
    private TextField fTel = new TextField();

    private Label tNome = new Label("Digite seu Username: ");
    private Label tData = new Label("Insira sua data de nascimento: ");
    private Label tEmail = new Label("Insira seu email: ");
    private Label tSenha = new Label("Insira sua senha: ");
    private Label tTel = new Label("Insira seu telefone: ");

    private Button bConfirmar = new Button("Confirmar");
    private Button bCancelar = new Button("Cancelar");

    public void start(Stage stage){

        // Elementos Pane e Scene

        BorderPane bp = new BorderPane();

        HBox hbB = new HBox();
        HBox hbNS = new HBox();
        HBox hbDT = new HBox();
        HBox hbE = new HBox();

        VBox vb = new VBox();

        Scene sc = new Scene(bp, 1520, 780);

        // Adiciona ao Pane
        hbNS.getChildren().addAll(tNome, fNome, tSenha, fSenha);
        hbDT.getChildren().addAll(tData, fData, tTel, fTel);
        hbE.getChildren().addAll(tEmail, fEmail);
        hbB.getChildren().addAll(bCancelar, bConfirmar);

        vb.getChildren().addAll(hbNS, hbDT, hbE, hbB);

        bp.setCenter(vb);

        // Tamanho
        fEmail.setPrefWidth(500);

        // Alinhamento
        hbNS.setAlignment(Pos.CENTER);
        hbDT.setAlignment(Pos.CENTER);
        hbE.setAlignment(Pos.CENTER);
        hbB.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);

        hbNS.setSpacing(20);
        hbDT.setSpacing(20);
        hbE.setSpacing(20);
        hbB.setSpacing(20);
        vb.setSpacing(20);
        bp.setPadding(new Insets(40));

        // Ações

        //Inicia
        stage.setScene(sc);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(CadastrarUI.class, args);
    }

    
}
