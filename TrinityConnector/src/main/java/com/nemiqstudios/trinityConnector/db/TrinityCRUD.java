package com.nemiqstudios.trinityconnector.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrinityCRUD {

    private final TrinityConnector connector;

    public TrinityCRUD(TrinityConnector connector) {
        this.connector = connector;
    }

    public int insert(String sql, Object... params) throws SQLException {
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParams(stmt, params);
            return stmt.executeUpdate();
        }
    }

    public int update(String sql, Object... params) throws SQLException {
        return insert(sql, params); // mesmo comportamento
    }

    public int delete(String sql, Object... params) throws SQLException {
        return insert(sql, params); // mesmo comportamento
    }

    public <T> List<T> query(String sql, ResultSetMapper<T> mapper, Object... params) throws SQLException {
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParams(stmt, params);
            ResultSet rs = stmt.executeQuery();

            List<T> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
            return results;
        }
    }

    private void setParams(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }
}
