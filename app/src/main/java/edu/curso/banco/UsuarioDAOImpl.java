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
    private static final String DB_JDBC_URI = "jdbc:sqlserver://localhost:1433;databaseName=Jogo;encrypt=false;trustServerCertificate=true";
    private static final String DB_USER = "Admin";
    private static final String DB_PASS = "12345678";
    private Connection con;

    public UsuarioDAOImpl() {
        System.out.println("Usuario DAO criado - com database MSSQL");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(DB_JDBC_URI, DB_USER, DB_PASS);
            System.out.println("Conexao foi feita com sucesso");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver MSSQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }
    }

    @Override
    public void cadastrar(Usuario usuario) {
        try {
            String nextIdSql = "SELECT ISNULL(MAX(cod), 0) + 1 FROM Usuario";
            PreparedStatement nextIdStm = con.prepareStatement(nextIdSql);
            ResultSet rs = nextIdStm.executeQuery();
            long id = 1;
            if (rs.next()) {
                id = rs.getLong(1);
            }
            rs.close();
            nextIdStm.close();

            String sql = "INSERT INTO Usuario (cod, Nome, datanasc, email, senha, telefone) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, id);
            stm.setString(2, usuario.getNome());
            stm.setDate(3, new java.sql.Date(usuario.getDataNasc().getTime()));
            stm.setString(4, usuario.getEmail());
            stm.setString(5, usuario.getSenha());
            stm.setString(6, usuario.getTelefone());
            stm.executeUpdate();
            stm.close();
            System.out.println("Comando executado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuario");
            e.printStackTrace();
        }
    }

    @Override
    public List<Usuario> consultarPorNome(String nome) {
        List<Usuario> lista = new ArrayList<>();
        try {
            String sql = "SELECT Nome, datanasc, email, senha, telefone FROM Usuario WHERE Nome LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String usuarioNome = rs.getString("Nome");
                Date dataNasc = rs.getDate("datanasc");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                String telefone = rs.getString("telefone");

                Usuario usuario = new Usuario(usuarioNome, dataNasc, email, senha, telefone, 0.0);
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
}
