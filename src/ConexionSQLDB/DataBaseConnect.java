/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionSQLDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class DataBaseConnect {
    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String myDB = "jdbc:oracle:thin:@132.68.1.20:1521:PRDSID";
            String usuario = "system";
            String password = "oracle";
            Connection conn = DriverManager.getConnection(myDB, usuario, password);
            return conn;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
