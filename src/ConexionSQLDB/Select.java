/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionSQLDB;

import Clases.Privilegio;
import Clases.Rol;
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
public class Select {
    public Usuario SelectLastCreatedUser(){
        Usuario user = new Usuario();
        try {
            Connection conn = DataBaseConnect.getConnection();
            String sql = "SELECT * FROM (SELECT USERNAME FROM all_users ORDER BY created DESC) WHERE rownum = 1";
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
    
    public List<Privilegio> SelectPrivSis(String name){
        List<Privilegio> SisPrivilegios = new ArrayList();
        try {
            Connection conn = DataBaseConnect.getConnection();
            String sql = "SELECT * FROM DBA_SYS_PRIVS WHERE GRANTEE = '" + name + "'";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String Nombre = rs.getString("PRIVILEGE");
                Privilegio priv = new Privilegio();
                priv.setNombre(Nombre);
                priv.setTipo(true);
                SisPrivilegios.add(priv);
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            System.out.println("SQL: Error en la consulta a DBA_SYS_PRIVS.");
        }
        return SisPrivilegios;
    }
    
    public List<Privilegio> SelectPrivObj(String name){
        List<Privilegio> ObjPrivilegios = new ArrayList();
        try {
            Connection conn = DataBaseConnect.getConnection();
            String sql = "SELECT * FROM DBA_TAB_PRIVS WHERE GRANTEE = '" + name + "'";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String Nombre = rs.getString("PRIVILEGE");
                String Objeto = rs.getString("TABLE_NAME");
                Privilegio priv = new Privilegio();
                priv.setNombre(Nombre);
                priv.setObjeto(Objeto);
                priv.setTipo(false);
                ObjPrivilegios.add(priv);
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            System.out.println("SQL: Error en la consulta a DBA_TAB_PRIVS.");
        }
        return ObjPrivilegios;
    }
    
    public List<Rol> SelectRoles(String name){
        List<Rol> Roles = new ArrayList();
        try {
            Connection conn = DataBaseConnect.getConnection();
            String sql = "SELECT * FROM DBA_ROLE_PRIVS WHERE GRANTEE = '" + name + "'";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String Nombre = rs.getString("GRANTED_ROLE");
                Rol rol = new Rol();
                rol.setNombre(Nombre);
                Roles.add(rol);
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            System.out.println("SQL: Error en la consulta a DBA_ROLE_PRIVS.");
        }
        return Roles;
    }
}