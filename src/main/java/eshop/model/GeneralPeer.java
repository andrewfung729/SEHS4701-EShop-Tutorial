package eshop.model;

import eshop.beans.Bean;
import eshop.beans.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneralPeer {

    public static <T extends Bean> List<T> generalQuery(DataManager dataManager, Class<T> entityClass, String sql, String... params) {
        List<T> entities = new ArrayList<>();
        Connection connection = dataManager.getConnection();

        if (connection == null) {
            return entities;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            if (params.length != 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setString(i + 1, params[i]);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    T entity = entityClass.getDeclaredConstructor().newInstance();
                    populateEntity(entity, rs);
                    System.out.println("Entity: " + entity);
                    entities.add(entity);
                }
            } catch (Exception e) {
                System.out.println("Could not get " + entityClass.getName() + ":" + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Could not query for " + entityClass.getName() + ":" + e.getMessage());
        }
        dataManager.putConnection(connection);
        return entities;
    }

    private static <T extends Bean> void populateEntity(T bean, ResultSet rs) throws SQLException {

        if (bean instanceof Book) {
            Book book = (Book) bean;
            book.setId(rs.getString("book_id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setPrice(rs.getDouble("price"));
        }
    }
}


