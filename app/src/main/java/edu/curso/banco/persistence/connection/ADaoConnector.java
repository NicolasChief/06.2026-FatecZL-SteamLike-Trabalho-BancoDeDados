package edu.curso.banco.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ADaoConnector {

    public abstract Connection getConnection() throws SQLException, ClassNotFoundException;
}
