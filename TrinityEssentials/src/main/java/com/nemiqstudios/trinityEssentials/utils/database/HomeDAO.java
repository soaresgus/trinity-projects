package com.nemiqstudios.trinityEssentials.utils.database;

import com.nemiqstudios.trinityEssentials.utils.home.Home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeDAO {
    private final DatabaseManager dbManager;

    public HomeDAO(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void insertHome(String playerUUID, String playerUsername, String homeName, boolean isPublic, String worldName, double x, double y, double z, float pitch, float yaw) {
        String sql = "INSERT INTO essentials_homes (playerUUID, playerUsername, homeName, isPublic, worldName, x, y, z, pitch, yaw) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerUUID);
            stmt.setString(2, playerUsername);
            stmt.setString(3, homeName);
            stmt.setBoolean(4, isPublic);
            stmt.setString(5, worldName);
            stmt.setDouble(6, x);
            stmt.setDouble(7, y);
            stmt.setDouble(8, z);
            stmt.setFloat(9, pitch);
            stmt.setFloat(10, yaw);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Home> getHomesByPlayerUUID(String playerUUID) {
        String sql = "SELECT playerUUID, playerUsername, homeName, isPublic, worldName, x, y, z, pitch, yaw FROM essentials_homes WHERE playerUUID = ?";
        List<Home> homes = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerUUID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    homes.add(new Home(
                            rs.getString("playerUUID"),
                            rs.getString("playerUsername"),
                            rs.getString("homeName"),
                            rs.getBoolean("isPublic"),
                            rs.getString("worldName"),
                            rs.getDouble("x"),
                            rs.getDouble("y"),
                            rs.getDouble("z"),
                            rs.getFloat("pitch"),
                            rs.getFloat("yaw")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homes;
    }

    public List<Home> getHomesByPlayerUsername(String playerUsername) {
        String sql = "SELECT playerUUID, playerUsername, homeName, isPublic, worldName, x, y, z, pitch, yaw FROM essentials_homes WHERE playerUsername = ?";
        List<Home> homes = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    homes.add(new Home(
                            rs.getString("playerUUID"),
                            rs.getString("playerUsername"),
                            rs.getString("homeName"),
                            rs.getBoolean("isPublic"),
                            rs.getString("worldName"),
                            rs.getDouble("x"),
                            rs.getDouble("y"),
                            rs.getDouble("z"),
                            rs.getFloat("pitch"),
                            rs.getFloat("yaw")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homes;
    }

    public void deleteHomeByPlayerUUID(String playerUUID, String homeName) {
        String sql = "DELETE FROM essentials_homes WHERE playerUUID = ? AND homeName = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerUUID);
            stmt.setString(2, homeName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeHomePublicStatus(String playerUUID, String homeName, boolean publicStatus) {
        String sql = "UPDATE essentials_homes SET isPublic = ? WHERE playerUUID = ? AND homeName = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, publicStatus);
            stmt.setString(2, playerUUID);
            stmt.setString(3, homeName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
