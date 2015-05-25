/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Marla Aragão
 */
public class DBConnection {

    private static Connection connection;
    private static final String serverName = "localhost";
    private static final String driverName = "com.mysql.jdbc.Driver";
    private static final String banco = "unismart";
    private static final String url = "jdbc:mysql://" + serverName;
    private static final String username = "root";
    private static final String password = ""; 

    
    public static Connection getInstance() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;

    }

    public static String status = "Não conectou...";

    private DBConnection() {

        try {

            Class.forName(driverName); 
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                
                try {
                    connection = DriverManager.getConnection(url + "/" + banco, username, password);
                    status = ("STATUS--->Conectado com sucesso!");
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e instanceof MySQLSyntaxErrorException) {
                        Statement s = connection.createStatement();

                        try {
                            s.executeUpdate("CREATE DATABASE unismart");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        connection = DriverManager.getConnection(url + "/" + banco, username, password);
                        status = ("STATUS--->Conectado com sucesso!");
                    }
                }                
            } else {
                status = ("STATUS--->Não foi possivel realizar conexão");
            }
        } catch (ClassNotFoundException e) { 
            System.out.println("O driver expecificado nao foi encontrado.");
        } catch (SQLException e) {  
            e.printStackTrace();
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
        }
    } 

    public static String statusConection() {
        return status;
    } 

    public static boolean close() {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    } 

    public static java.sql.Connection restartConnection() {
        close();
        return getInstance();
    }
}

