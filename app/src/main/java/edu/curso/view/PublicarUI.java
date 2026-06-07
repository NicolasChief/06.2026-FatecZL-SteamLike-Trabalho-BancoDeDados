package edu.curso.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.URL;
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

    public void start(Stage stage){

        // Elementos Pane

        BorderPane bp = new BorderPane();

        HBox hbNP = new HBox();
        HBox hbD = new HBox();       
        HBox hbR = new HBox();        
        HBox hbB = new HBox();

        VBox vb = new VBox();

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

        // Alinhamento

        vb.setAlignment(Pos.CENTER);

        HBox rightContainer = new HBox();
        rightContainer.prefWidthProperty().bind(scn.widthProperty().divide(2));
        rightContainer.prefHeightProperty().bind(scn.heightProperty());
        rightContainer.setAlignment(Pos.CENTER);
        rightContainer.getChildren().add(vb);
        bp.setRight(rightContainer);

        // Left side: show PH.png
        VBox leftContainer = new VBox();
        leftContainer.setAlignment(Pos.CENTER);
        leftContainer.prefWidthProperty().bind(scn.widthProperty().divide(2));
        leftContainer.prefHeightProperty().bind(scn.heightProperty());

        ImageView imgView = new ImageView();
        Image img = null;
        try {
            URL imgUrl = getClass().getResource("/img/PH.png");
            if (imgUrl != null) {
                img = new Image(imgUrl.toExternalForm());
                System.out.println("Loaded PH.png from classpath: " + imgUrl);
            } else {
                // try common local paths for running from IDE
                File f1 = new File("app/src/main/resources/img/PH.png");
                File f2 = new File("src/main/resources/img/PH.png");
                if (f1.exists()) {
                    img = new Image(f1.toURI().toString());
                    System.out.println("Loaded PH.png from file: " + f1.getAbsolutePath());
                } else if (f2.exists()) {
                    img = new Image(f2.toURI().toString());
                    System.out.println("Loaded PH.png from file: " + f2.getAbsolutePath());
                } else {
                    System.out.println("PH.png not found in classpath or expected resource paths.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (img != null) {
            imgView.setImage(img);
        } else {
            // show placeholder text if image not found
            System.out.println("Imagem PH.png não carregada — verifique o caminho.");
        }
        imgView.setPreserveRatio(true);
        imgView.setFitWidth(500);
        imgView.setFitHeight(680);
        leftContainer.getChildren().add(imgView);

        bp.setLeft(leftContainer);

        hbNP.setAlignment(Pos.CENTER);
        hbD.setAlignment(Pos.CENTER);
        hbR.setAlignment(Pos.CENTER);
        hbB.setAlignment(Pos.CENTER);
 
        BorderPane.setAlignment(rightContainer, Pos.CENTER);  

        // Inicializar

        stage.setScene(scn);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
