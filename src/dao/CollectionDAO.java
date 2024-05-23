package dao;

import model.Collection;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionDAO {
    public List<Collection> getAllCollections() {
        List<Collection> collections = new ArrayList<>();
        String sql = "SELECT * FROM collections";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Collection collection = new Collection(
                        rs.getInt("collection_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDate("acquisition_date"),
                        rs.getString("status"),
                        rs.getString("image_url")
                );
                collections.add(collection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collections;
    }

    public void addCollection(Collection collection) {
        String sql = "INSERT INTO collections (name, description, category, acquisition_date, status, image_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collection.getName());
            stmt.setString(2, collection.getDescription());
            stmt.setString(3, collection.getCategory());
            stmt.setDate(4, new java.sql.Date(collection.getAcquisitionDate().getTime()));
            stmt.setString(5, truncateStatus(collection.getStatus()));
            stmt.setString(6, collection.getImageUrl());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCollection(Collection collection) {
        String sql = "UPDATE collections SET name = ?, description = ?, category = ?, acquisition_date = ?, status = ?, image_url = ? WHERE collection_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collection.getName());
            stmt.setString(2, collection.getDescription());
            stmt.setString(3, collection.getCategory());
            stmt.setDate(4, new java.sql.Date(collection.getAcquisitionDate().getTime()));
            stmt.setString(5, truncateStatus(collection.getStatus()));
            stmt.setString(6, collection.getImageUrl());
            stmt.setInt(7, collection.getCollectionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCollection(int collectionId) {
        String sql = "DELETE FROM collections WHERE collection_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collectionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Collection> searchCollections(String keyword) {
        List<Collection> collections = new ArrayList<>();
        String sql = "SELECT * FROM collections WHERE name LIKE ? OR description LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Collection collection = new Collection(
                        rs.getInt("collection_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDate("acquisition_date"),
                        rs.getString("status"),
                        rs.getString("image_url")
                );
                collections.add(collection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collections;
    }

    private String truncateStatus(String status) {
        if (status.length() > 10) {
            return status.substring(0, 10);
        }
        return status;
    }
}