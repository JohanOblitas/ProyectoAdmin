/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionSQLDB;

import Clases.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Prueba {
    public Usuario SelectUsuario(){
        Usuario user = new Usuario();
        try {
            Connection conn = DataBaseConnect.getConnection();
            String sql = "SELECT * FROM ( SELECT USERNAME FROM all_users ORDER BY created DESC) WHERE rownum = 1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String Nombre = rs.getString("USERNAME");
                user.setNombre(Nombre);
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            System.out.println("SQL: Error.");
        }
        return user;
    }
}