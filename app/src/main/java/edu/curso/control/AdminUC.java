package edu.curso.control;

import edu.curso.model.Desenvolvedora;
import edu.curso.model.Jogo;
import edu.curso.model.Pedido;
import edu.curso.model.Usuario;
import edu.curso.banco.JogoDAOImpl;
import edu.curso.banco.UsuarioDAOImpl;
import edu.curso.banco.DesenvolvedorDAOImpl;
import edu.curso.banco.PedidoDAOImpl;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.util.List;

public class AdminUC {

        public void atualizarTabela(String tipo, TableView tvDados) {
        tvDados.getColumns().clear();
        tvDados.setItems(null);

        switch (tipo) {
            case "Jogos":
                criarTabelaJogos(tvDados);
                break;
            case "Usuarios":
                criarTabelaUsuarios(tvDados);
                break;
            case "Pedidos":
                criarTabelaPedidos(tvDados);
                break;
            case "Desenvolvedores":
                criarTabelaDesenvolvedores(tvDados);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void criarTabelaJogos(TableView tvDados) {
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
        
        TableColumn<Jogo, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricaoJogo"));

        tvDados.getColumns().addAll(colNome, colDesenvolvedor, colPublicadora, colPreco, colEspaco, colDescricao);

        // preencher dados a partir do banco
        List<Jogo> jogos = new JogoDAOImpl().consultarPorNome("");
        tvDados.setItems(FXCollections.observableArrayList(jogos));

    }

    @SuppressWarnings("unchecked")
    private void criarTabelaUsuarios(TableView tvDados) {
        TableColumn<Usuario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        TableColumn<Usuario, java.util.Date> colDataNasc = new TableColumn<>("Data de Nascimento");
        colDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));

        TableColumn<Usuario, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Usuario, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        TableColumn<Usuario, Double> colSaldo = new TableColumn<>("Saldo");
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoConta"));

        tvDados.getColumns().addAll(colNome, colDataNasc, colEmail, colTelefone, colSaldo);

        // preencher dados a partir do banco
        List<Usuario> usuarios = new UsuarioDAOImpl().consultarPorNome("");
        tvDados.setItems(FXCollections.observableArrayList(usuarios));

    }

    @SuppressWarnings("unchecked")
    private void criarTabelaPedidos(TableView tvDados) {
        TableColumn<Pedido, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusPedido"));

        TableColumn<Pedido, Double> colValor = new TableColumn<>("Valor Total");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        
        TableColumn<Pedido, java.util.Date> colDataCompra = new TableColumn<>("Data da Compra");
        colDataCompra.setCellValueFactory(new PropertyValueFactory<>("dataCompra"));

        tvDados.getColumns().addAll(colStatus, colValor, colDataCompra);

        // preencher dados a partir do banco
        List<Pedido> pedidos = new PedidoDAOImpl().consultar();
        tvDados.setItems(FXCollections.observableArrayList(pedidos));

    }

    @SuppressWarnings("unchecked")
    private void criarTabelaDesenvolvedores(TableView tvDados) {
        TableColumn<Desenvolvedora, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Desenvolvedora, String> colCNPJ = new TableColumn<>("CNPJ/CPF");
        colCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpjcpf"));

        tvDados.getColumns().addAll(colNome, colCNPJ);

        // preencher dados a partir do banco
        List<Desenvolvedora> devs = new DesenvolvedorDAOImpl().consultarPorNome("");
        System.out.println("AdminUC: desenvolvedores recuperados = " + devs.size());
        tvDados.setItems(FXCollections.observableArrayList(devs));

    }

}
