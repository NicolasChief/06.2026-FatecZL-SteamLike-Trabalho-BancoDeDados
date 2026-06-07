package edu.curso.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PublicarUI extends Application {

    private TextField fNome = new TextField();
    private TextField fPreco = new TextField();
    private TextField fEspaco = new TextField();
    private TextField fDescricao = new TextField();
    private TextField fRequisitos = new TextField();

    private Label tNome = new Label("Nome do Jogo: ");
    private Label tPreco = new Label("Preço Sugerido: ");
    private Label tEspaco = new Label("Espaço em GB: ");
    private Label tDescricao = new Label("Descrição: ");
    private Label tRequisitos = new Label("Requisitos Médio: ");

    private Button bPublicar = new Button("Publicar");
    private Button bCancelar = new Button("Cancelar");

    private Image imgPH = new Image(getClass().getResourceAsStream("/img/PlaceHolderJogo.png"));

    public void start(Stage stage){

        // Elementos Pane

        BorderPane bp = new BorderPane();

        HBox hbNP = new HBox();
        HBox hbD = new HBox();       
        HBox hbR = new HBox();        
        HBox hbB = new HBox();

        VBox vb = new VBox();
        VBox vbI = new VBox();

        Scene scn = new Scene(bp, 1520, 780);

        // Tamanho

        bCancelar.setPrefSize(150, 50);
        bPublicar.setPrefSize(150, 50);

        fDescricao.setPrefSize(400, 200);

        // Margem

        hbB.setSpacing(20);
        hbNP.setSpacing(10);
        hbD.setSpacing(10);
        hbR.setSpacing(10);

        vb.setSpacing(20);

        // Adiciona ao Pane
        
        hbNP.getChildren().addAll(tNome, fNome, tPreco, fPreco);
        hbD.getChildren().addAll(tDescricao, fDescricao);
        hbR.getChildren().addAll(tRequisitos, fRequisitos, tEspaco, fEspaco);
        hbB.getChildren().addAll(bCancelar, bPublicar);
        vb.getChildren().addAll(hbNP, hbD, hbR, hbB);

        // Imagem  
/*
        Image imgPH = new Image(getClass().getResourceAsStream("C:\\Users\\nicol\\Desktop\\Projetos\\TrabalhoBancoDeDados\\app\\src\\main\\resources\\img\\SteamLogo.png"));
        ImageView imageView = new ImageView(imgPH);
        imageView.setFitHeight(204);
        imageView.setFitWidth(204);
        imageView.setPreserveRatio(true);  */

        // Alinhamento

        vb.setAlignment(Pos.CENTER);

        HBox rightContainer = new HBox();
        rightContainer.prefWidthProperty().bind(scn.widthProperty().divide(2));
        rightContainer.prefHeightProperty().bind(scn.heightProperty());
        rightContainer.setAlignment(Pos.CENTER);
        rightContainer.getChildren().add(vb);
        bp.setRight(rightContainer);

        hbNP.setAlignment(Pos.CENTER);
        hbD.setAlignment(Pos.CENTER);
        hbR.setAlignment(Pos.CENTER);
        hbB.setAlignment(Pos.CENTER);
 
        BorderPane.setAlignment(rightContainer, Pos.CENTER); /* 

        HBox leftContainer = new HBox();
        leftContainer.prefWidthProperty().bind(scn.widthProperty().divide(2));
        leftContainer.prefHeightProperty().bind(scn.heightProperty());
        leftContainer.setAlignment(Pos.CENTER);
        leftContainer.getChildren().add(vbI);
        bp.setLeft(leftContainer);

        BorderPane.setAlignment(leftContainer, Pos.CENTER); */

        // Inicializar

        stage.setScene(scn);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
