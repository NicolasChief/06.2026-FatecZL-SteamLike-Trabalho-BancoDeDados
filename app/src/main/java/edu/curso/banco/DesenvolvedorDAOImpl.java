package edu.curso.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.curso.model.Desenvolvedora;

public class DesenvolvedorDAOImpl implements DesenvolvedorDAO  {

    private static final String[] DB_URLS = {
        "jdbc:sqlserver://NOTEBOFFO:51075;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost:1433;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost;instanceName=EXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true"
    };

    private static final String DB_USER = "Admin";
    private static final String DB_PASS = "12345678";

    private Connection con;

    public DesenvolvedorDAOImpl() {
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
    public void cadastrar(
        Desenvolvedora desenvolvedora
    ) {

        try {

            String nextIdSql =
                "SELECT ISNULL(MAX(ID),0)+1 FROM Desenvolvedora";

            PreparedStatement nextIdStm =
                con.prepareStatement(nextIdSql);

            ResultSet rs =
                nextIdStm.executeQuery();

            int id = 1;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            rs.close();
            nextIdStm.close();

            String sql =
                """
                INSERT INTO Desenvolvedora
                (
                    ID,
                    Nome,
                    CNPJ,
                    Email,
                    Senha,
                    Telefone
                )
                VALUES
                (
                    ?, ?, ?, ?, ?, ?
                )
                """;

            PreparedStatement stm =
                con.prepareStatement(sql);

            stm.setInt(1, id);
            stm.setString(2, desenvolvedora.getNome());
            stm.setString(3, desenvolvedora.getCnpjcpf());
            stm.setString(4, desenvolvedora.getEmail());
            stm.setString(5, desenvolvedora.getSenha());
            stm.setString(6, desenvolvedora.getTelefone());

            stm.executeUpdate();

            stm.close();

            System.out.println(
                "Desenvolvedora cadastrada."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Override
    public List<Desenvolvedora> consultarPorNome(
        String nome
    ) {

        List<Desenvolvedora> lista =
            new ArrayList<>();

        try {

            String sql =
                """
                SELECT *
                FROM Desenvolvedora
                WHERE Nome LIKE ?
                """;

            PreparedStatement stm =
                con.prepareStatement(sql);

            stm.setString(
                1,
                "%" + nome + "%"
            );

            ResultSet rs =
                stm.executeQuery();

            while (rs.next()) {

                Desenvolvedora d =
                    new Desenvolvedora();

                d.setNome(
                    rs.getString("Nome")
                );

                d.setCnpjcpf(
                    rs.getString("CNPJ")
                );

                lista.add(d);
            }

            rs.close();
            stm.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void atualizar(
        long id,
        Desenvolvedora desenvolvedora
    ) {

        try {

            String sql =
                """
                UPDATE Desenvolvedora
                SET
                    Nome = ?,
                    CNPJ = ?,
                    Email = ?,
                    Senha = ?,
                    Telefone = ?
                WHERE ID = ?
                """;

            PreparedStatement stm =
                con.prepareStatement(sql);

            stm.setString(
                1,
                desenvolvedora.getNome()
            );

            stm.setString(
                2,
                desenvolvedora.getCnpjcpf()
            );

            stm.setString(
                3,
                desenvolvedora.getEmail()
            );

            stm.setString(
                4,
                desenvolvedora.getSenha()
            );

            stm.setString(
                5,
                desenvolvedora.getTelefone()
            );

            stm.setLong(6, id);

            stm.executeUpdate();

            stm.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void apagar(long id) {

        try {

            String sql =
                "DELETE FROM Desenvolvedora WHERE ID = ?";

            PreparedStatement stm =
                con.prepareStatement(sql);

            stm.setLong(1, id);

            stm.executeUpdate();

            stm.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


}
