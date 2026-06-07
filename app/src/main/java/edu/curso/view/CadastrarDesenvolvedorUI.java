package edu.curso.view;

import edu.curso.control.CadastrarUC;
import edu.curso.model.Desenvolvedora;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastrarDesenvolvedorUI extends Application {

    private TextField fNome = new TextField();
    private TextField fCNPJ = new TextField();
    private TextField fEmail = new TextField();
    private TextField fSenha = new TextField();
    private TextField fTel = new TextField();

    private Label tNome = new Label("Nome da Desenvolvedora: ");
    private Label tCNPJ = new Label("CNPJ: ");
    private Label tEmail = new Label("Email: ");
    private Label tSenha = new Label("Senha: ");
    private Label tTel = new Label("Telefone: ");

    private Button bConfirmar = new Button("Confirmar");
    private Button bCancelar = new Button("Cancelar");

    @Override
    public void start(Stage stage) {

        CadastrarUC cadastrarUC = new CadastrarUC();

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
        hbDT.getChildren().addAll(tCNPJ, fCNPJ, tTel, fTel);
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
        bCancelar.setOnAction(event -> stage.close());

        bConfirmar.setOnAction(event -> {
            try {
                cadastrarUC.cadastrarDesenvolvedor(fNome.getText(), fCNPJ.getText(), fEmail.getText(), fSenha.getText(), fTel.getText());
                new Alert(AlertType.INFORMATION, "Desenvolvedor cadastrado com sucesso.").show();
                stage.close();
            } catch (IllegalArgumentException e) {
                new Alert(AlertType.WARNING, e.getMessage()).show();
            } catch (RuntimeException e) {
                new Alert(AlertType.ERROR, "Erro ao cadastrar desenvolvedor: " + e.getMessage()).show();
            }
        });

        // Inicializar
        stage.setTitle("Cadastro de Desenvolvedor");
        stage.setScene(sc);
        stage.show();
    }

}
