package edu.curso.view;

import edu.curso.control.AdminUC;
import edu.curso.model.Desenvolvedora;
import edu.curso.model.Jogo;
import edu.curso.model.Pedido;
import edu.curso.model.Usuario;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminUI extends Application {

    private Button bCriar = new Button("Criar");
    private Button bAtualizar = new Button("Atualizar");
    private Button bDeletar = new Button("Deletar");
    private Button bConsultar = new Button("Consultar");

    private TextField fConsultar = new TextField();

    @SuppressWarnings("rawtypes")
    private TableView tvDados = new TableView<>();

    private ObservableList<String> olTipo = FXCollections.observableArrayList("Jogos", "Usuarios", "Pedidos", "Desenvolvedores");

    private ComboBox<String> cbTipos = new ComboBox<>();

    public void start(Stage stage){

        AdminUC AdminUC = new AdminUC();

        // Elementos Pane e Scene

        BorderPane bp = new BorderPane();
        HBox hbTop = new HBox(10);
        VBox vbCenter = new VBox();
        Scene scn = new Scene(bp, 1520, 780);

        // Configuração do ComboBox

        cbTipos.setItems(olTipo);
        cbTipos.setPrefWidth(150);
        cbTipos.setValue("Jogos"); 
        
        fConsultar.setPromptText("Pesquisar...");
        fConsultar.setPrefWidth(200);

        // Configuração da TableView

        tvDados.setPrefHeight(700);
        tvDados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // Preenche a TableView inicial

        AdminUC.atualizarTabela("Jogos", tvDados);

        // Listener para mudar a tabela ao selecionar novo tipo

        cbTipos.setOnAction(e -> {
            String tipoSelecionado = cbTipos.getValue();
            AdminUC.atualizarTabela(tipoSelecionado, tvDados);
        });

        // Adiciona elementos ao HBox superior

        hbTop.getChildren().addAll(
            new Label("Tipo:"),
            cbTipos,
            bCriar, 
            bAtualizar, 
            bDeletar,
            new Label("Pesquisar:"),
            fConsultar, 
            bConsultar
        );
        hbTop.setPadding(new Insets(10));
        hbTop.setAlignment(Pos.CENTER_LEFT);
        hbTop.setStyle("-fx-border-color: #cccccc; -fx-border-width: 0 0 1 0;");

        // Adiciona a TableView no centro

        vbCenter.getChildren().add(tvDados);
        vbCenter.setPadding(new Insets(10));

        // Define layout do BorderPane

        bp.setTop(hbTop);
        bp.setCenter(vbCenter);

        stage.setScene(scn);
        stage.setTitle("Administrador");
        stage.show();
    }

    public static void mostrarAdmin(Stage stage) {
        try {
            AdminUI admin = new AdminUI();
            admin.start(stage);
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao abrir a tela de administrador.").show();
        }
    }

}
