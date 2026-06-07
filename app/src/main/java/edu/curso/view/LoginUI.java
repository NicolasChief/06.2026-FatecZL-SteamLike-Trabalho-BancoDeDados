package edu.curso.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import edu.curso.control.BuscaUC;
import edu.curso.control.LoginUC;
import edu.curso.model.Usuario;
import edu.curso.model.Desenvolvedora;
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

public class LoginUI extends Application {
    
    private TextField fNome = new TextField();
    private TextField fSenha = new TextField();

    private Image imgSteam = new Image(getClass().getResourceAsStream("/img/SteamLogo.png"));

    private Label tNome = new Label("Usuário: ");
    private Label tSenha = new Label("Senha: ");
    private Label tCadastrar = new Label("Primeira Vez?");

    private Button bEntrar = new Button("Acessar");

    private LoginUC loginUC = new LoginUC();

    public void start(Stage stage){

        // Elementos Pane e Scene
        BorderPane bp = new BorderPane();

        ImageView imageView = new ImageView(imgSteam);

        HBox hbN = new HBox(20);
        hbN.setAlignment(Pos.CENTER);
        hbN.setPrefWidth(600);
        hbN.setMinWidth(600);

        HBox hbS = new HBox(20);
        hbS.setAlignment(Pos.CENTER);
        hbS.setPrefWidth(600);
        hbS.setMinWidth(600);

        HBox hbO = new HBox(40);
        hbO.setAlignment(Pos.CENTER);
        hbO.setPrefWidth(600);
        hbO.setMinWidth(600);

        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        
        Scene sc = new Scene(bp, 1520, 780);

        // Tamanho
        fNome.setPrefSize(500, 50);
        fSenha.setPrefSize(500, 50);

        bEntrar.setPrefSize(250, 50);
        tCadastrar.setPrefSize(250, 50);
        tCadastrar.setAlignment(Pos.CENTER);

        // Adiciona Imagem
        imageView.setFitHeight(404);
        imageView.setFitWidth(404);
        imageView.setPreserveRatio(true);

        // Adiciona ao Pane
        hbN.getChildren().addAll(tNome, fNome);
        hbS.getChildren().addAll(tSenha, fSenha);
        hbO.getChildren().addAll(bEntrar, tCadastrar);

        vb.getChildren().addAll(imageView, hbN, hbS, hbO);

        bp.setCenter(vb);
        BorderPane.setAlignment(vb, Pos.CENTER);
        BorderPane.setAlignment(hbO, null);

        // Alinhamento
        hbO.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);

        // Ações
        bEntrar.setOnAction(event -> {
            String nome = fNome.getText().trim();
            String senha = fSenha.getText();
            
            // Verifica se é admin
            if (nome.equalsIgnoreCase("admin") && senha.equals("admin")) {
                AdminUI.mostrarAdmin(new Stage());
                return;
            }
            
            // Tenta login como Usuário primeiro
            String resultado = loginUC.logar(nome, senha);

            switch (resultado) {
                case "LOGIN_OK":
                    Usuario usuario = loginUC.autenticar(nome, senha);
                    if (usuario != null) {
                        BuscaUC.mostrarBusca(new Stage(), usuario);
                        stage.close();
                    } else {
                        new Alert(AlertType.ERROR, "Erro interno ao autenticar usuário.").show();
                    }
                    break;
                case "SENHA_INCORRETA":
                    new Alert(AlertType.WARNING, "Senha incorreta. Tente novamente.").show();
                    break;
                case "USUARIO_NAO_EXISTE":
                    // Se não encontrar como usuário, tenta como desenvolvedor
                    // TODO: Implementar login de desenvolvedor no banco de dados
                    Desenvolvedora dev = new Desenvolvedora(nome, ""); // CNPJ padrão
                    BuscaUC.mostrarBusca(new Stage(), dev);
                    stage.close();
                    break;
                case "ERRO_BD":
                    new Alert(AlertType.ERROR, "Erro ao acessar o banco de dados. Verifique a conexão.").show();
                    break;
                default:
                    new Alert(AlertType.ERROR, "Erro desconhecido de login.").show();
            }
        });

        tCadastrar.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                try {
                    new CadastroTipoUI().start(new Stage());
                } catch (Exception e) {
                    new Alert(AlertType.ERROR, "Não foi possível abrir a tela de cadastro.").show();
                }
            }
        });

        //Inicia
        stage.setScene(sc);
        stage.show();

    }

}
