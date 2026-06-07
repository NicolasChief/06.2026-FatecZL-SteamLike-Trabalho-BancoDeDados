package edu.curso.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.curso.model.Jogo;

public class JogoDAOImpl implements JogoDAO {
    private static final String[] DB_URLS = {
        "jdbc:sqlserver://NOTEBOFFO:51075;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost:1433;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost;instanceName=EXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true"
    };

    private static final String DB_USER = "Admin";
    private static final String DB_PASS = "12345678";

    private Connection con;

    public JogoDAOImpl() {
        System.out.println("Usuario DAO criado - com database MSSQL");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            for (String url : DB_URLS) {
                try {
                    System.out.println("Tentando conectar em: " + url);

                    con = DriverManager.getConnection(
                        url,
                        DB_USER,
                        DB_PASS
                    );

                    System.out.println("Conexão realizada com sucesso!");
                    System.out.println("URL utilizada: " + url);

                    break;
                } catch (SQLException e) {
                    System.out.println("Falha na conexão: " + url);
                }
            }

            if (con == null) {
                throw new RuntimeException(
                    "Não foi possível conectar a nenhuma instância SQL Server."
                );
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Driver JDBC do SQL Server não encontrado",
                e
            );
        }
    }

    @Override
    public void cadastrar(Jogo jogo) {
        try {
            String nextIdSql = "SELECT ISNULL(MAX(ID), 0) + 1 FROM Jogo";
            PreparedStatement nextIdStm = con.prepareStatement(nextIdSql);
            ResultSet rs = nextIdStm.executeQuery();
            long id = 1;
            if (rs.next()) {
                id = rs.getLong(1);
            }
            rs.close();
            nextIdStm.close();

            String sql = "INSERT INTO Jogo (ID, Nome, dataLancamento, preco, espacoArmazenamento, descricaojogo, descricaoRequisitos) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, id);
            stm.setString(2, jogo.getNome());
            stm.setDate(3, new java.sql.Date(jogo.getDataLancamento().getTime()));
            stm.setDouble(4, jogo.getPreco());
            stm.setDouble(5, jogo.getEspacoArmazenamento());
            stm.setString(6, jogo.getDescricaoJogo());
            stm.setString(7, jogo.getDescricaoSpecs());
            stm.executeUpdate();
            stm.close();
            System.out.println("Comando executado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar jogo");
            e.printStackTrace();
        }
    }

    @Override
    public List<Jogo> consultarPorNome(String nome) {
        List<Jogo> lista = new ArrayList<>();
        try {
            String sql = "SELECT Nome, dataLancamento, preco, espacoArmazenamento, descricaojogo, descricaoRequisitos FROM Jogo WHERE Nome LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String jogoNome = rs.getString("Nome");
                Date dataLancamento = rs.getDate("dataLancamento");
                double preco = rs.getDouble("preco");
                double espacoArmazenamento = rs.getDouble("espacoArmazenamento");
                String descricaoJogo = rs.getString("descricaojogo");
                String descricaoSpecs = rs.getString("descricaoRequisitos");

                Jogo jogo = new Jogo(jogoNome, dataLancamento, preco, espacoArmazenamento, descricaoJogo, descricaoSpecs, false, null, null);
                lista.add(jogo);
            }
            rs.close();
            stm.close();
            System.out.println("Comando executado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao consultar jogos");
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void atualizar(long id, Jogo jogo) {
        try {
            String sql = "UPDATE Jogo SET Nome = ?, dataLancamento = ?, preco = ?, espacoArmazenamento = ?, descricaojogo = ?, descricaoRequisitos = ? WHERE ID = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, jogo.getNome());
            stm.setDate(2, new java.sql.Date(jogo.getDataLancamento().getTime()));
            stm.setDouble(3, jogo.getPreco());
            stm.setDouble(4, jogo.getEspacoArmazenamento());
            stm.setString(5, jogo.getDescricaoJogo());
            stm.setString(6, jogo.getDescricaoSpecs());
            stm.setLong(7, id);
            stm.executeUpdate();
            stm.close();
            System.out.println("Jogo atualizado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar jogo");
            e.printStackTrace();
        }
    }

    @Override
    public void apagar(long id) {
        try {
            String sql = "DELETE FROM Jogo WHERE ID = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, id);
            stm.executeUpdate();
            stm.close();
            System.out.println("Jogo apagado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao apagar jogo");
            e.printStackTrace();
        }
    }

    // Remove jogos pelo nome (usado pela camada de controle quando não há id disponível)
    public void apagarPorNome(String nome) {
        try {
            String sql = "DELETE FROM Jogo WHERE Nome = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, nome);
            int rows = stm.executeUpdate();
            stm.close();
            System.out.println("Jogo(s) apagado(s) por nome. Linhas afetadas: " + rows);
        } catch (SQLException e) {
            System.out.println("Erro ao apagar jogo por nome");
            e.printStackTrace();
        }
    }

    // Atualiza jogo(s) por nome (usado pela camada de controle quando não há id disponível)
    public void atualizarPorNome(String nome, Jogo jogo) {
        try {
            String sql = "UPDATE Jogo SET Nome = ?, dataLancamento = ?, preco = ?, espacoArmazenamento = ?, descricaojogo = ?, descricaoRequisitos = ? WHERE Nome = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, jogo.getNome());
            stm.setDate(2, new java.sql.Date(jogo.getDataLancamento().getTime()));
            stm.setDouble(3, jogo.getPreco());
            stm.setDouble(4, jogo.getEspacoArmazenamento());
            stm.setString(5, jogo.getDescricaoJogo());
            stm.setString(6, jogo.getDescricaoSpecs());
            stm.setString(7, nome);
            int rows = stm.executeUpdate();
            stm.close();
            System.out.println("Jogo(s) atualizado(s) por nome. Linhas afetadas: " + rows);
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar jogo por nome");
            e.printStackTrace();
        }
    }
}
