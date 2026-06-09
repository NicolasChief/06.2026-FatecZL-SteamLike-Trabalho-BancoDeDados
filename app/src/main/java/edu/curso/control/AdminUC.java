package edu.curso.control;

import edu.curso.model.Desenvolvedora;
import edu.curso.model.Jogo;
import edu.curso.model.Pedido;
import edu.curso.model.Usuario;
import edu.curso.banco.JogoDAOImpl;
import edu.curso.banco.UsuarioDAOImpl;
import edu.curso.banco.DesenvolvedorDAOImpl;
import edu.curso.banco.PedidoDAOImpl;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
    public int executarConsultaRelatorio(String descricaoQuery, TableView tvDados) {
        System.out.println("AdminUC: executarConsultaRelatorio chamada com query='" + descricaoQuery + "'");
        String sql = getSqlRelatorio(descricaoQuery);
        if (sql == null || sql.isEmpty()) {
            System.out.println("AdminUC: SQL para a query selecionada não foi encontrada.");
            return -1;
        }

        System.out.println("AdminUC: SQL executado: " + sql);
        TableView<ObservableList<Object>> table = (TableView<ObservableList<Object>>) tvDados;
        table.getColumns().clear();
        table.setPlaceholder(new javafx.scene.control.Label("Executando consulta..."));
        if (table.getItems() != null) {
            table.getItems().clear();
        }

        int count = 0;
        try (Connection con = getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                final int colIndex = i - 1;
                TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(meta.getColumnLabel(i));
                column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get(colIndex)));
                table.getColumns().add(column);
            }

            ObservableList<ObservableList<Object>> rows = FXCollections.observableArrayList();
            while (rs.next()) {
                ObservableList<Object> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                rows.add(row);
                count++;
            }

            table.setItems(rows);
            table.refresh();
            table.setPlaceholder(new javafx.scene.control.Label("Nenhum resultado para esta query."));
            System.out.println("AdminUC: consulta '" + descricaoQuery + "' retornou " + count + " linhas.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao executar consulta de relatório: " + e.getMessage());
            e.printStackTrace();
            table.setPlaceholder(new javafx.scene.control.Label("Erro na consulta: veja o console."));
            return -1;
        }
        return count;
    }

    private String getSqlRelatorio(String descricaoQuery) {
        switch (descricaoQuery) {
            case "Total de jogos por gênero, apenas gêneros com mais de 1 jogo":
                return "SELECT g.Nome AS genero, COUNT(gj.JogoID) AS totalJogos "
                     + "FROM Genero g "
                     + "INNER JOIN Genero_Jogo gj ON g.ID = gj.GeneroID "
                     + "GROUP BY g.Nome "
                     + "HAVING COUNT(gj.JogoID) > 1";
            case "Média, maior e menor preço dos jogos, excluindo gratuitos":
                return "SELECT AVG(preco) AS precoMedio, MAX(preco) AS precoMaximo, MIN(preco) AS precoMinimo "
                     + "FROM Jogo "
                     + "WHERE preco > 0";
            case "Saldo dos usuários classificado em faixas":
                return "SELECT nome, saldo, "
                     + "CASE WHEN saldo >= 700 THEN 'Alto' WHEN saldo >= 300 THEN 'Médio' ELSE 'Baixo' END AS faixaSaldo "
                     + "FROM Usuario "
                     + "ORDER BY saldo DESC";
            case "Jogos lançados nos últimos 10 anos com espaço acima da média":
                return "SELECT Nome, dataLancamento, espacoArmazenamento "
                     + "FROM Jogo "
                     + "WHERE dataLancamento >= DATEADD(YEAR, -10, GETDATE()) "
                     + "AND espacoArmazenamento > (SELECT AVG(espacoArmazenamento) FROM Jogo) "
                     + "ORDER BY espacoArmazenamento DESC";
            case "Total gasto por usuário em compras concluídas":
                return "SELECT Usuariocod, COUNT(ID) AS totalCompras, SUM(valorTotal) AS totalGasto, AVG(valorTotal) AS ticketMedio "
                     + "FROM Compra "
                     + "WHERE statusPedido = 'Concluído' "
                     + "GROUP BY Usuariocod "
                     + "ORDER BY totalGasto DESC";
            case "Quantidade de jogos por desenvolvedora, com nome da desenvolvedora":
                return "SELECT d.Nome AS desenvolvedora, COUNT(dj.JogoID) AS jogosDesenvolvidos "
                     + "FROM Desenvolvedora d "
                     + "INNER JOIN Desenvolvedora_Jogo dj ON d.ID = dj.DesenvolvedoraID "
                     + "GROUP BY d.Nome "
                     + "ORDER BY jogosDesenvolvidos DESC";
            case "Todos os jogos com seus gêneros e desenvolvedoras":
                return "SELECT j.Nome AS jogo, g.Nome AS genero, d.Nome AS desenvolvedora "
                     + "FROM Jogo j "
                     + "INNER JOIN Genero_Jogo gj ON j.ID = gj.JogoID "
                     + "INNER JOIN Genero g ON g.ID = gj.GeneroID "
                     + "INNER JOIN Desenvolvedora_Jogo dj ON j.ID = dj.JogoID "
                     + "INNER JOIN Desenvolvedora d ON d.ID = dj.DesenvolvedoraID "
                     + "ORDER BY j.Nome";
            case "Biblioteca de cada usuário com os jogos que possui":
                return "SELECT u.Nome AS usuario, j.Nome AS jogo, jb.dataAdicao "
                     + "FROM Usuario u "
                     + "INNER JOIN Biblioteca b ON u.cod = b.Usuariocod "
                     + "INNER JOIN Jogo_Biblioteca jb ON b.ID = jb.BibliotecaID "
                     + "INNER JOIN Jogo j ON j.ID = jb.JogoID "
                     + "ORDER BY u.Nome, jb.dataAdicao";
            case "Histórico de compras detalhado por usuário":
                return "SELECT u.Nome AS usuario, c.dataCompra, j.Nome AS jogo, ic.quantidade, ic.precoUni, c.statusPedido "
                     + "FROM Usuario u "
                     + "INNER JOIN Compra c ON u.cod = c.Usuariocod "
                     + "INNER JOIN ItemCompra ic ON c.ID = ic.CompraID "
                     + "INNER JOIN Jogo j ON j.ID = ic.JogoID "
                     + "ORDER BY u.Nome, c.dataCompra";
            case "Jogos com publicadora e gênero":
                return "SELECT j.Nome AS jogo, j.preco, p.Nome AS publicadora, g.Nome AS genero "
                     + "FROM Jogo j "
                     + "LEFT JOIN Publicadora_Jogo pj ON j.ID = pj.JogoID "
                     + "LEFT JOIN Publicadora p ON p.ID = pj.PublicadoraID "
                     + "LEFT JOIN Genero_Jogo gj ON j.ID = gj.JogoID "
                     + "LEFT JOIN Genero g ON g.ID = gj.GeneroID "
                     + "ORDER BY j.Nome";
            case "Usuários e seus jogos comprados vs jogos na biblioteca":
                return "SELECT u.Nome AS usuario, j.Nome AS jogo, "
                     + "CASE WHEN jb.JogoID IS NOT NULL THEN 'Sim' ELSE 'Não' END AS naBiblioteca, c.dataCompra "
                     + "FROM Usuario u "
                     + "INNER JOIN Compra c ON u.cod = c.Usuariocod "
                     + "INNER JOIN ItemCompra ic ON c.ID = ic.CompraID "
                     + "INNER JOIN Jogo j ON j.ID = ic.JogoID "
                     + "LEFT JOIN Biblioteca b ON u.cod = b.Usuariocod "
                     + "LEFT JOIN Jogo_Biblioteca jb ON b.ID = jb.BibliotecaID AND jb.JogoID = j.ID "
                     + "ORDER BY u.Nome, j.Nome";
            case "Desenvolvedoras que publicam seus próprios jogos":
                return "SELECT d.Nome AS desenvolvedora, j.Nome AS jogo, p.Nome AS publicadora "
                     + "FROM Desenvolvedora d "
                     + "INNER JOIN Desenvolvedora_Jogo dj ON d.ID = dj.DesenvolvedoraID "
                     + "INNER JOIN Jogo j ON j.ID = dj.JogoID "
                     + "INNER JOIN Publicadora_Jogo pj ON j.ID = pj.JogoID "
                     + "INNER JOIN Publicadora p ON p.ID = pj.PublicadoraID "
                     + "WHERE d.Nome = p.Nome";
            default:
                return null;
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        String[] dbUrls = {
            "jdbc:sqlserver://NOTEBOFFO:51075;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
            "jdbc:sqlserver://localhost:1433;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
            "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
            "jdbc:sqlserver://localhost;instanceName=EXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true"
        };

        String user = "Admin";
        String pass = "12345678";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        for (String url : dbUrls) {
            try {
                return DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                System.out.println("Falha na conexão: " + url);
            }
        }
        throw new SQLException("Não foi possível conectar a nenhuma instância SQL Server para relatórios.");
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
