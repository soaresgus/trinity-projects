package com.nemiqstudios.trinityEssentials.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static Connection connection;
    private final String url;
    private final String user;
    private final String password;

    public DatabaseManager(String host, int port, String database, String user, String password) {
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
        this.user = user;
        this.password = password;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão com MySQL estabelecida!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connect();
        }
        return connection;
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão encerrada!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createEssentialsHomesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `essentials_homes` ("
                + "`id` INT NOT NULL AUTO_INCREMENT, "
                + "`playerUUID` VARCHAR(36) NOT NULL, "
                + "`playerUsername` VARCHAR(50) NOT NULL, "
                + "`homeName` VARCHAR(50) NOT NULL, "
                + "`isPublic` BOOLEAN NOT NULL, "
                + "`worldName` VARCHAR(50) NOT NULL, "
                + "`x` DOUBLE NOT NULL, "
                + "`y` DOUBLE NOT NULL, "
                + "`z` DOUBLE NOT NULL, "
                + "`pitch` FLOAT NOT NULL, "
                + "`yaw` FLOAT NOT NULL, "
                + "PRIMARY KEY (`id`)"
                + ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'essentials-homes' verificada/criada!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
