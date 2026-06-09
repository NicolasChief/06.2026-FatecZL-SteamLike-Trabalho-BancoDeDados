package edu.curso.view;

import edu.curso.control.AdminUC;
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

    private ObservableList<String> olQuerys = FXCollections.observableArrayList("Total de jogos por gênero, apenas gêneros com mais de 1 jogo", "Média, maior e menor preço dos jogos, excluindo gratuitos", "Saldo dos usuários classificado em faixas", "Jogos lançados nos últimos 10 anos com espaço acima da média", "Total gasto por usuário em compras concluídas", "Quantidade de jogos por desenvolvedora, com nome da desenvolvedora", "Todos os jogos com seus gêneros e desenvolvedoras", "Biblioteca de cada usuário com os jogos que possui", "Histórico de compras detalhado por usuário", "Jogos com publicadora e gênero", "Usuários e seus jogos comprados vs jogos na biblioteca", "Desenvolvedoras que publicam seus próprios jogos");

    private ComboBox<String> cbTipos = new ComboBox<>();
    private ComboBox<String> cbQuerys = new ComboBox<>();

    public void start(Stage stage){

        AdminUC adminUC = new AdminUC();

        // Elementos Pane e Scene

        BorderPane bp = new BorderPane();
        HBox hbTop = new HBox(10);
        VBox vbCenter = new VBox();
        Scene scn = new Scene(bp, 1520, 780);

        // Configuração do ComboBox

        cbTipos.setItems(olTipo);
        cbTipos.setPrefWidth(150);
        cbTipos.setValue("Jogos"); 
        
        cbQuerys.setItems(olQuerys);
        cbQuerys.setPrefWidth(430);
        cbQuerys.setPromptText("Selecione um relatório");

        fConsultar.setPromptText("Pesquisar...");
        fConsultar.setPrefWidth(200);

        // Configuração da TableView

        tvDados.setPrefHeight(700);
        tvDados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        adminUC.atualizarTabela("Jogos", tvDados);

        // Listener para mudar a tabela ao selecionar novo tipo

        cbTipos.setOnAction(e -> {
            String tipoSelecionado = cbTipos.getValue();
            adminUC.atualizarTabela(tipoSelecionado, tvDados);
        });

        bConsultar.setOnAction(e -> {
            String queryDescricao = cbQuerys.getValue();
            if (queryDescricao == null || queryDescricao.isEmpty()) {
                new Alert(AlertType.WARNING, "Selecione uma query antes de consultar.").show();
                return;
            }
            int linhas = adminUC.executarConsultaRelatorio(queryDescricao, tvDados);
            if (linhas >= 0) {
                String mensagem = linhas > 0 ? "Consulta executada com sucesso: " + linhas + " linhas retornadas." : "Consulta executada, nenhum resultado retornado.";
                new Alert(AlertType.INFORMATION, mensagem).show();
            } else {
                new Alert(AlertType.ERROR, "Erro ao executar a consulta. Veja o console para detalhes.").show();
            }
        });

        // Adiciona elementos ao HBox superior

        hbTop.getChildren().addAll(new Label("Tipo:"), cbTipos, bCriar, bAtualizar, bDeletar, new Label("Pesquisar:"), fConsultar, bConsultar, cbQuerys);
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
