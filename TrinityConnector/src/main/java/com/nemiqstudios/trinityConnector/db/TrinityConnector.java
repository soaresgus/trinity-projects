package com.nemiqstudios.trinityconnector.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TrinityConnector {

    private final DataSource dataSource;

    public TrinityConnector(DatabaseConfig config) {
        this.dataSource = config.getDataSource();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
