package com.mycompany.oracletest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class OracleTest {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:oracle:thin:@localhost:1521/xe";
        String username = "sys as sysdba";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) 
        {
            if (connection != null) {
                System.out.println("Connected!");

                for (int i = 0; i < 100; i++) 
                {
                    insertRandomBook(connection);
                }

                System.out.println("Random records inserted");
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private static void insertRandomBook(Connection connection) throws SQLException 
    {
        String insertSQL = "INSERT INTO BOOK (Id, Name, ISBN) VALUES (BOOK_SEQ.NEXTVAL, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) 
        {
            Random random = new Random();
            String name = "Book " + random.nextInt(1000);
            String isbn = "ISBN" + random.nextInt(1000);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, isbn);
            preparedStatement.executeUpdate();
        }
    }
}
