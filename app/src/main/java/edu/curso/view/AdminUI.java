package edu.curso.view;

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

        atualizarTabela("Jogos");

        // Listener para mudar a tabela ao selecionar novo tipo

        cbTipos.setOnAction(e -> {
            String tipoSelecionado = cbTipos.getValue();
            atualizarTabela(tipoSelecionado);
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

    private void atualizarTabela(String tipo) {
        tvDados.getColumns().clear();
        tvDados.setItems(null);

        switch (tipo) {
            case "Jogos":
                criarTabelaJogos();
                break;
            case "Usuarios":
                criarTabelaUsuarios();
                break;
            case "Pedidos":
                criarTabelaPedidos();
                break;
            case "Desenvolvedores":
                criarTabelaDesenvolvedores();
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void criarTabelaJogos() {
        TableColumn<Jogo, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Jogo, String> colDesenvolvedor = new TableColumn<>("Desenvolvedora");
        colDesenvolvedor.setCellValueFactory(new PropertyValueFactory<>("desenvolvedora"));

        TableColumn<Jogo, String> colPublicadora = new TableColumn<>("Publicadora");
        colPublicadora.setCellValueFactory(new PropertyValueFactory<>("publicadora"));

        TableColumn<Jogo, Double> colPreco = new TableColumn<>("Preço");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<Jogo, Double> colEspaco = new TableColumn<>("Espaço (GB)");
        colEspaco.setCellValueFactory(new PropertyValueFactory<>("espacoArmazenamento"));

        tvDados.getColumns().addAll(colNome, colDesenvolvedor, colPublicadora, colPreco, colEspaco);

    }

    @SuppressWarnings("unchecked")
    private void criarTabelaUsuarios() {
        TableColumn<Usuario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Usuario, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Usuario, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        TableColumn<Usuario, Double> colSaldo = new TableColumn<>("Saldo");
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoConta"));

        tvDados.getColumns().addAll(colNome, colEmail, colTelefone, colSaldo);

    }

    @SuppressWarnings("unchecked")
    private void criarTabelaPedidos() {
        TableColumn<Pedido, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusPedido"));

        TableColumn<Pedido, Double> colValor = new TableColumn<>("Valor Total");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        tvDados.getColumns().addAll(colStatus, colValor);

    }

    @SuppressWarnings("unchecked")
    private void criarTabelaDesenvolvedores() {
        TableColumn<Desenvolvedora, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Desenvolvedora, String> colCNPJ = new TableColumn<>("CNPJ/CPF");
        colCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpjcpf"));

        tvDados.getColumns().addAll(colNome, colCNPJ);

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
