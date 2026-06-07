package edu.curso.view;

import edu.curso.model.Jogo;
import edu.curso.control.BuscaUC;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuscaUI extends Application {

    private TextField fBusca = new TextField();

    private Button bAdicionar = new Button("+");
    private Button bBusca = new Button("🔍");
    private Button bJogo = new Button();

    private Label tBiblioteca = new Label("Biblioteca de Jogos");

    private TableView<Jogo> tvCatalago = new TableView<>();

    private BuscaUC buscaUC = new BuscaUC();

    private ObservableList<Jogo> jogosObservaveis = FXCollections.observableArrayList();

    public void start(Stage stage) {

        buscaUC.adicionarDadosDemo();

        // Elementos Pane e Scene

        BorderPane bp = new BorderPane();

        VBox vbM = new VBox();
        vbM.setAlignment(Pos.TOP_CENTER);
        vbM.setFillWidth(true);
        VBox vbC = new VBox();

        HBox hb = new HBox();
        HBox hbS = new HBox();

        ScrollPane sp = new ScrollPane(vbM);
        sp.setFitToWidth(true);

        Scene sc = new Scene(bp, 1520, 780);

        // Table View

        TableColumn<Jogo, String> colNome = new TableColumn<>("Nome do Jogo");
        colNome.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getNome())
        );
        TableColumn<Jogo, Double> colPreco = new TableColumn<>("Preço do Jogo");
        colPreco.setCellValueFactory(
            itemData -> new ReadOnlyDoubleWrapper(itemData.getValue().getPreco()).asObject()
        );
        TableColumn<Jogo, String> colStatus = new TableColumn<>("Status de Aquisição");
        colStatus.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getStatusAquicicao() ? "Adquirido" : "Não adquirido")
        );
        TableColumn<Jogo, String> colPub = new TableColumn<>("Publicadora");
        colPub.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getPublicadora())
        );
        TableColumn<Jogo, String> colDes = new TableColumn<>("Desenvolvedora");
        colDes.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getDesenvolvedora())
        );

        // Tamanho

        tvCatalago.prefWidthProperty().bind(vbC.widthProperty());
        tvCatalago.prefHeightProperty().bind(vbC.heightProperty());

        vbC.prefWidthProperty().bind(bp.widthProperty().subtract(sp.getPrefWidth()));
        vbC.prefHeightProperty().bind(bp.heightProperty());

        fBusca.setPrefWidth(600);

        sp.setPrefWidth(250);

        colNome.setPrefWidth(400);
        colPreco.setPrefWidth(170);
        colStatus.setPrefWidth(150);
        colPub.setPrefWidth(275);
        colDes.setPrefWidth(275);

        // Margem

        HBox.setMargin(bBusca, new Insets(20, 0, 20, 0));

        //Adiciona ao Table

        tvCatalago.getColumns().add(colNome);
        tvCatalago.getColumns().add(colPreco);
        tvCatalago.getColumns().add(colStatus);
        tvCatalago.getColumns().add(colPub);
        tvCatalago.getColumns().add(colDes);
        tvCatalago.setRowFactory(table -> {
            TableRow<Jogo> linha = new TableRow<>();
            linha.setOnMouseClicked(event -> {
                if (!linha.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    buscaUC.abrirPedido(new Stage(), linha.getItem());
                }
            });
            return linha;
        });
        tvCatalago.setItems(jogosObservaveis);
        buscaUC.atualizarTabela(fBusca.getText(), jogosObservaveis);

        // Adiciona Imagem

        Image imgSteam = new Image(getClass().getResourceAsStream("/img/SteamLogo.png"));
        ImageView imageView = new ImageView(imgSteam);
        imageView.setFitHeight(204);
        imageView.setFitWidth(204);
        imageView.setPreserveRatio(true);

        hbS.setSpacing(50);
        hb.setAlignment(Pos.CENTER_LEFT);

        // Verifica se é Desenvolvedor e mostra botão adicionar

        boolean isDesenvolvedor = buscaUC.isDesenvolvedor();
        if (isDesenvolvedor) {
            bAdicionar.setPrefSize(50, 50);
            bAdicionar.setOnAction(event -> {
                new PublicarUI().start(new Stage());
            });
            hb.getChildren().addAll(fBusca, bBusca, bAdicionar);
        } else {
            hb.getChildren().addAll(fBusca, bBusca);
        }

        // Adiciona ao Pane   

        tBiblioteca.setMaxWidth(Double.MAX_VALUE);
        tBiblioteca.setAlignment(Pos.CENTER);
        vbM.getChildren().add(tBiblioteca);
        hbS.getChildren().addAll(imageView, hb); 
        vbC.getChildren().addAll(hbS, tvCatalago);

        bp.setLeft(sp);
        bp.setRight(vbC);

        VBox.setVgrow(tvCatalago, Priority.ALWAYS);

        // Lista da Biblioteca

        for (Jogo jogo : buscaUC.listarTodos()) {
            if (jogo.getStatusAquicicao()) {
                Button jogoBtn = new Button(jogo.getNome());
                jogoBtn.setMaxWidth(Double.MAX_VALUE);
                jogoBtn.setOnAction((e) -> new Alert(AlertType.ERROR, "Nossos Serviços Encontram-se Indisponíveis").show());
                vbM.getChildren().add(jogoBtn);
            }
        }

        // Alinhamento

        sp.setFitToHeight(true);

        hb.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(hb, Pos.CENTER);

        hbS.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(hbS, Pos.CENTER);

        vbC.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(vbC, Pos.CENTER);

        bp.setLeft(sp);
        bp.setRight(vbC);

        // Ações

        fBusca.setPromptText("Digite o nome do jogo");
        fBusca.setOnAction((e) -> buscaUC.atualizarTabela(fBusca.getText(), jogosObservaveis));
        bBusca.setOnAction((e) -> buscaUC.atualizarTabela(fBusca.getText(), jogosObservaveis));
        bJogo.setOnAction((e) -> new Alert(AlertType.ERROR, "Nossos Serviços Encontram-se Indisponíveis"));

        // Inicializar

        stage.setScene(sc);
        stage.show();

    }

    public void setBuscaUC(BuscaUC buscaUC) {
        this.buscaUC = buscaUC;
    }

}
