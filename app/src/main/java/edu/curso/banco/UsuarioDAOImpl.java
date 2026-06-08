package edu.curso.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.curso.model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {
    private static final String[] DB_URLS = {
        "jdbc:sqlserver://NOTEBOFFO:51075;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost:1433;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost;instanceName=EXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true"
    };

    private static final String DB_USER = "Admin";
    private static final String DB_PASS = "12345678";

    private Connection con;

    public UsuarioDAOImpl() {
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
    public void cadastrar(Usuario usuario) {
        if (con == null) {
            throw new RuntimeException("Sem conexão com o banco de dados. Verifique driver, servidor e credenciais.");
        }
        try {
            String sql = "INSERT INTO Usuario (Nome, datanasc, email, senha, telefone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, usuario.getNome());
            stm.setDate(2, new java.sql.Date(usuario.getDataNasc().getTime()));
            stm.setString(3, usuario.getEmail());
            stm.setString(4, usuario.getSenha());
            String tel = usuario.getTelefone();
            if (tel == null || tel.trim().isEmpty()) {
                stm.setNull(5, java.sql.Types.VARCHAR);
                System.out.println("Inserindo telefone como NULL (campo vazio no formulário)");
            } else {
                stm.setString(5, tel);
            }

            int rows = stm.executeUpdate();
            ResultSet gen = stm.getGeneratedKeys();
            if (gen.next()) {
                usuario.setCod(gen.getInt(1));
            }
            gen.close();
            stm.close();

            if (rows <= 0) {
                throw new RuntimeException("Nenhuma linha inserida ao cadastrar usuário");
            }

            usuario.setSaldoConta(500.0);
            System.out.println("Comando executado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuario");
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> consultarPorNome(String nome) {
        List<Usuario> lista = new ArrayList<>();
        try {
            String sql = "SELECT cod, Nome, datanasc, email, senha, telefone FROM Usuario WHERE Nome LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int cod = rs.getInt("cod");
                String usuarioNome = rs.getString("Nome");
                Date dataNasc = rs.getDate("datanasc");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                String telefone = rs.getString("telefone");
                double saldo = 500.0;
                Usuario usuario = new Usuario(cod, usuarioNome, dataNasc, email, senha, telefone, saldo);
                lista.add(usuario);
            }
            rs.close();
            stm.close();
            System.out.println("Comando executado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao consultar usuarios");
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void atualizar(long id, Usuario usuario) {
        try {
            String sql = "UPDATE Usuario SET Nome = ?, datanasc = ?, email = ?, senha = ?, telefone = ? WHERE cod = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, usuario.getNome());
            stm.setDate(2, new java.sql.Date(usuario.getDataNasc().getTime()));
            stm.setString(3, usuario.getEmail());
            stm.setString(4, usuario.getSenha());
            stm.setString(5, usuario.getTelefone());
            stm.setLong(6, id);
            stm.executeUpdate();
            stm.close();
            System.out.println("Usuario atualizado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuario");
            e.printStackTrace();
        }
    }

    @Override
    public void apagar(long id) {
        try {
            String sql = "DELETE FROM Usuario WHERE cod = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, id);
            stm.executeUpdate();
            stm.close();
            System.out.println("Usuario apagado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao apagar usuario");
            e.printStackTrace();
        }
    }

    public void atualizarSaldo(String nomeUsuario, double novoSaldo) {
        try {
            String sql = "UPDATE Usuario SET saldo = ? WHERE Nome = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setDouble(1, novoSaldo);
            stm.setString(2, nomeUsuario);
            int linhasAfetadas = stm.executeUpdate();
            stm.close();
            if (linhasAfetadas > 0) {
                System.out.println("Saldo do usuário atualizado com sucesso. Novo saldo: " + novoSaldo);
            } else {
                System.out.println("Usuário não encontrado para atualizar saldo");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar saldo do usuário");
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar saldo: " + e.getMessage(), e);
        }
    }
}
