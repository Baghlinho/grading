package org.example.dao;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceTest {

    @Test
    void getConnection() {
        try(Connection connection = DataSource.getConnection()) {
            assertTrue(connection.isValid(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}