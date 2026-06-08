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
import edu.curso.model.Desenvolvedora;

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

    public static List<Jogo> jogos = new ArrayList<>();

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
        if (con == null) {
            System.out.println("ERRO: Conexão é nula!");
            return;
        }
        
        try {
            System.out.println("=== Iniciando cadastro de jogo ===");
            System.out.println("Nome: " + jogo.getNome());
            System.out.println("Preço: " + jogo.getPreco());
            System.out.println("Espaço: " + jogo.getEspacoArmazenamento());
            System.out.println("Conexão ativa: " + (con != null && !con.isClosed()));
            
            // SQL sem o ID - deixar o banco gerar com IDENTITY
            String sql = "INSERT INTO Jogo (Nome, dataLancamento, preco, espacoArmazenamento, descricaojogo, descricaoRequisitos) VALUES (?, ?, ?, ?, ?, ?)";
            System.out.println("SQL: " + sql);
            
            PreparedStatement stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, jogo.getNome());
            stm.setDate(2, new java.sql.Date(jogo.getDataLancamento().getTime()));
            stm.setDouble(3, jogo.getPreco());
            stm.setDouble(4, jogo.getEspacoArmazenamento());
            stm.setString(5, jogo.getDescricaoJogo());
            stm.setString(6, jogo.getDescricaoSpecs());
            
            System.out.println("Executando insert...");
            int rowsAffected = stm.executeUpdate();
            
            // Obter o ID gerado
            ResultSet generatedKeys = stm.getGeneratedKeys();
            long generatedId = -1;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getLong(1);
            }
            generatedKeys.close();
            stm.close();
            
            System.out.println("Linhas inseridas: " + rowsAffected);
            System.out.println("ID gerado: " + generatedId);
            System.out.println("Jogo cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar jogo");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro geral ao cadastrar jogo");
            e.printStackTrace();
        }
    }

    public void vincularDesenvolvedora(String nomeJogo, Desenvolvedora desenvolvedora) {
        try {
            System.out.println("=== Vinculando jogo à desenvolvedora ===");
            System.out.println("Nome Jogo: " + nomeJogo);
            System.out.println("Dev CNPJ: " + desenvolvedora.getCnpjcpf());
            System.out.println("Dev Nome: " + desenvolvedora.getNome());
            
            // Get jogo ID by name
            String sqlGetJogoId = "SELECT ID FROM Jogo WHERE Nome = ?";
            PreparedStatement stmGetId = con.prepareStatement(sqlGetJogoId);
            stmGetId.setString(1, nomeJogo);
            ResultSet rsId = stmGetId.executeQuery();
            long jogoId = -1;
            if (rsId.next()) {
                jogoId = rsId.getLong(1);
            }
            rsId.close();
            stmGetId.close();
            System.out.println("Jogo ID encontrado: " + jogoId);

            // Get developer ID by CNPJ/CPF (or name as fallback)
            String sqlGetDevId = "SELECT ID FROM Desenvolvedora WHERE CNPJ = ? OR Nome = ?";
            PreparedStatement stmGetDevId = con.prepareStatement(sqlGetDevId);
            stmGetDevId.setString(1, desenvolvedora.getCnpjcpf());
            stmGetDevId.setString(2, desenvolvedora.getNome());
            ResultSet rsDevId = stmGetDevId.executeQuery();
            long devId = -1;
            if (rsDevId.next()) {
                devId = rsDevId.getLong(1);
            }
            rsDevId.close();
            stmGetDevId.close();
            System.out.println("Dev ID encontrado: " + devId);

            if (jogoId > 0 && devId > 0) {
                // Insert into Desenvolvedora_Jogo
                String sql = "INSERT INTO Desenvolvedora_Jogo (DesenvolvedoraID, JogoID) VALUES (?, ?)";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setLong(1, devId);
                stm.setLong(2, jogoId);
                int rowsAffected = stm.executeUpdate();
                stm.close();
                System.out.println("Linhas inseridas em Desenvolvedora_Jogo: " + rowsAffected);
                System.out.println("Jogo vinculado à desenvolvedora com sucesso");
            } else {
                System.out.println("Não foi possível vincular: jogoId=" + jogoId + ", devId=" + devId);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao vincular jogo à desenvolvedora");
            e.printStackTrace();
        }
    }

    @Override
    public List<Jogo> consultarPorNome(String nome) {
        List<Jogo> lista = new ArrayList<>();
        try {
            System.out.println("=== Consultando jogos por nome: '" + nome + "' ===");
            
            String sql = "SELECT j.ID, j.Nome, j.dataLancamento, j.preco, j.espacoArmazenamento, j.descricaojogo, j.descricaoRequisitos, " +
                    "ISNULL(d.Nome, '') AS Desenvolvedora, ISNULL(p.Nome, '') AS Publicadora " +
                    "FROM Jogo j " +
                    "LEFT JOIN Desenvolvedora_Jogo dj ON j.ID = dj.JogoID " +
                    "LEFT JOIN Desenvolvedora d ON dj.DesenvolvedoraID = d.ID " +
                    "LEFT JOIN Publicadora_Jogo pj ON j.ID = pj.JogoID " +
                    "LEFT JOIN Publicadora p ON pj.PublicadoraID = p.ID " +
                    "WHERE j.Nome LIKE ?";
            
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
                String jogoNome = rs.getString("Nome");
                Date dataLancamento = rs.getDate("dataLancamento");
                double preco = rs.getDouble("preco");
                double espacoArmazenamento = rs.getDouble("espacoArmazenamento");
                String descricaoJogo = rs.getString("descricaojogo");
                String descricaoSpecs = rs.getString("descricaoRequisitos");
                String desenvolvedora = rs.getString("Desenvolvedora");
                String publicadora = rs.getString("Publicadora");

                System.out.println("Jogo " + count + ": " + jogoNome + " | Dev: " + desenvolvedora + " | Pub: " + publicadora);

                Jogo jogo = new Jogo(jogoNome, dataLancamento, preco, espacoArmazenamento, descricaoJogo, descricaoSpecs, false, 
                        publicadora != null && !publicadora.isEmpty() ? publicadora : "", 
                        desenvolvedora != null && !desenvolvedora.isEmpty() ? desenvolvedora : "");
                lista.add(jogo);
            }
            rs.close();
            stm.close();
            System.out.println("Total de jogos encontrados: " + count);
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
    
    public void registrarCompra(int usuarioID) {
        try {
            String sql = "INSERT INTO Compra (usuarioCod, statusPedido, dataCompra) VALUES (?, 'CONCLUIDO', GETDATE())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, usuarioID);
            int linhasInseridas = ps.executeUpdate();
            System.out.println("Compra registrada para usuário " + usuarioID + ". Linhas inseridas: " + linhasInseridas);
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erro ao registrar compra");
            e.printStackTrace();
        }
    }
}
