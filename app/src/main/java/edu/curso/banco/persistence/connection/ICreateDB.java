package edu.curso.banco.persistence.connection;

import java.sql.SQLException;

public interface ICreateDB {
    void createDatabase() throws SQLException, ClassNotFoundException;
}
