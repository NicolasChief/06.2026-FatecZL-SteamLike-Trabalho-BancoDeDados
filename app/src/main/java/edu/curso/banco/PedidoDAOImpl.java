package edu.curso.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.curso.model.Pedido;

public class PedidoDAOImpl  implements PedidoDAO {

private static final String[] DB_URLS = {
        "jdbc:sqlserver://NOTEBOFFO:51075;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost:1433;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true",
        "jdbc:sqlserver://localhost;instanceName=EXPRESS;databaseName=Jogo;encrypt=false;trustServerCertificate=true"
    };

    private static final String DB_USER = "Admin";
    private static final String DB_PASS = "12345678";

    private Connection con;

    public PedidoDAOImpl() {
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
public void cadastrar(Pedido pedido) {

    try {

        String nextIdSql =
            "SELECT ISNULL(MAX(ID),0)+1 FROM Compra";

        PreparedStatement nextIdStm =
            con.prepareStatement(nextIdSql);

        ResultSet rs =
            nextIdStm.executeQuery();

        int id = 1;

        if(rs.next()) {
            id = rs.getInt(1);
        }

        rs.close();
        nextIdStm.close();

        String sql =
            """
            INSERT INTO Compra
            (
                ID,
                dataCompra,
                statusPedido,
                valorTotal
            )
            VALUES
            (
                ?, ?, ?, ?
            )
            """;

        PreparedStatement stm =
            con.prepareStatement(sql);

        stm.setInt(1, id);

        stm.setDate(
            2,
            new java.sql.Date(
                System.currentTimeMillis()
            )
        );

        stm.setString(
            3,
            pedido.getStatusPedido()
        );

        stm.setDouble(
            4,
            pedido.getValorTotal()
        );

        stm.executeUpdate();

        stm.close();

        System.out.println(
            "Pedido cadastrado."
        );

    } catch(SQLException e) {

        e.printStackTrace();
    }
}

@Override
public List<Pedido> consultar() {

    List<Pedido> lista =
        new ArrayList<>();

    try {

        String sql =
            "SELECT * FROM Compra";

        PreparedStatement stm =
            con.prepareStatement(sql);

        ResultSet rs =
            stm.executeQuery();

        while(rs.next()) {

            Pedido pedido =
                new Pedido(
                    rs.getString(
                        "statusPedido"
                    )
                );

            pedido.setValorTotal(
                rs.getDouble(
                    "valorTotal"
                )
            );
            
            pedido.setDataCompra(
                rs.getDate(
                    "dataCompra"
                )
            );

            lista.add(pedido);
        }

        rs.close();
        stm.close();

    } catch(SQLException e) {

        e.printStackTrace();
    }

    return lista;
}

@Override
public void apagar (long id) {

    try {

        String sql =
            "DELETE FROM Compra WHERE ID = ?";

        PreparedStatement stm =
            con.prepareStatement(sql);

        stm.setLong(1, id);

        stm.executeUpdate();

        stm.close();

    } catch(SQLException e) {

        e.printStackTrace();
    }
}

@Override
public void atualizar (long id, Pedido pedido)  {

    try {

        String sql =
            """
            UPDATE Compra
            SET
                statusPedido = ?,
                valorTotal = ?
            WHERE ID = ?
            """;

        PreparedStatement stm =
            con.prepareStatement(sql);

        stm.setString(
            1,
            pedido.getStatusPedido()
        );

        stm.setDouble(
            2,
            pedido.getValorTotal()
        );

        stm.setLong(
            3,
            id
        );

        stm.executeUpdate();

        stm.close();

    } catch(SQLException e) {

        e.printStackTrace();
    }
}

}
