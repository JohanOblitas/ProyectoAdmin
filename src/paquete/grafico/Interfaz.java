package paquete.grafico;

import Clases.Privilegio;
import Clases.Rol;
import Clases.Usuario;
import ConexionSQLDB.Select;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class Interfaz extends javax.swing.JFrame {
    Usuario user = new Usuario();
    List<Privilegio> listaPrivilegios;
    Select SelectDB = new Select();

    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        initComponents();
        listaPrivilegios = new ArrayList();
    }
    
    public void LimpiarTabla(int Tabla){
        DefaultTableModel tb;
        if(Tabla == 1){
            tb = (DefaultTableModel) tblTabla1.getModel();
        }else{
            tb = (DefaultTableModel) tblTabla2.getModel();
        }
        for(int i = tb.getRowCount() - 1; i >= 0; i--){
            tb.removeRow(i);
        }
    }
    
    public void SeleccionarDatos1() {
        LimpiarTabla(1);
        DefaultTableModel tb = (DefaultTableModel) tblTabla1.getModel();
        for(Privilegio priv: listaPrivilegios){
            if(priv.isTipo()){
                tb.addRow(new Object[]{priv.getNombre()});
            }else{
                tb.addRow(new Object[]{priv.getNombre() + " (" + priv.getObjeto() + ")"});
            }
        }
    }
    
    public void SeleccionarDatos2(Set<String> s) {
        LimpiarTabla(2);
        DefaultTableModel tb = (DefaultTableModel) tblTabla2.getModel();
        for(String priv: s){
            tb.addRow(new Object[]{priv});
        }
    }
    
    public void ListarPrivilegiosSis() {
        Privilegio priv = new Privilegio();
        priv.setNombre(ListaPrivSis.getSelectedValue());
        priv.setTipo(true);
        listaPrivilegios.add(priv);
        //System.out.println(priv.getNombre());
    }
    
    public void ListarPrivilegiosObj(String objeto) {
        Privilegio priv = new Privilegio();
        priv.setNombre(ListaPrivObj.getSelectedValue());
        priv.setTipo(false);
        priv.setObjeto(objeto);
        listaPrivilegios.add(priv);
        //System.out.println(priv.getNombre());
    }
    
    public void GenerarScript1(){
        if(tUsername.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Especificar nombre de usuario / rol.", "Error", JOptionPane.INFORMATION_MESSAGE);
        }else{
            String cad = "";
            if(tRolename.getText().equalsIgnoreCase("")){
                // Si no se especifica un rol:
                for(Privilegio priv: listaPrivilegios){
                    if(priv.isTipo()){
                        // Si el privilegio es de sistema:
                        cad = cad + "GRANT " + priv.getNombre() + " TO " + tUsername.getText().toUpperCase() + ";\n";
                    }else{
                        // Si el privilegio es de objeto:
                        cad = cad + "GRANT " + priv.getNombre() + " ON " + priv.getObjeto().toUpperCase() + " TO " + tUsername.getText().toUpperCase() + ";\n";
                    }
                }
            }else{
                // Si se especifica un rol:
                cad = cad + "CREATE ROLE " + tRolename.getText().toUpperCase() + ";\n";
                for(Privilegio priv: listaPrivilegios){
                    if(priv.isTipo()){
                        // Si el privilegio es de sistema:
                        cad = cad + "GRANT " + priv.getNombre() + " TO " + tRolename.getText().toUpperCase() + ";\n";
                    }else{
                        // Si el privilegio es de objeto:
                        cad = cad + "GRANT " + priv.getNombre() + " ON " + priv.getObjeto().toUpperCase() + " TO " + tRolename.getText().toUpperCase() + ";\n";
                    }
                }
                cad = cad + "GRANT " + tRolename.getText().toUpperCase() + " TO " + tUsername.getText().toUpperCase() + ";";
            }
            tScript1.setText(cad);
        }
    }
    
    public List<Rol> AllRoles(Rol tree, int num, List<Rol> listaRoles){
        if(tree == null || tree.getSub() == null){
            return listaRoles;
        }
        for(Rol n : tree.getSub()){
            Rol role = new Rol();
            role.setNombre(n.getNombre());
            listaRoles.add(role);
            n.setSub(SelectDB.SelectRoles(n.getNombre()));
            AllRoles(n, num + 1, listaRoles);
        }
        return listaRoles;
    }
    public List<Rol> callRec(Rol tree){
        tree.setSub(SelectDB.SelectRoles(tree.getNombre()));
        List<Rol> listaRoles = new ArrayList();
        return AllRoles(tree, 1, listaRoles);
    }
    
    public List<String> AllPrivileges(String usuario){
        Rol Usuario = new Rol();
        Usuario.setNombre(usuario);
        List<Rol> listaRoles = callRec(Usuario);
        List<String> AllPrivileges = new ArrayList();
        
        SelectDB.SelectPrivSis(usuario).forEach(priv -> {
            AllPrivileges.add(priv.getNombre());
        });
        SelectDB.SelectPrivObj(usuario).forEach(priv -> {
            AllPrivileges.add(priv.getNombre() + " ON " + priv.getObjeto());
        });
        for(Rol role: listaRoles){
            SelectDB.SelectPrivSis(role.getNombre()).forEach(priv -> {
                AllPrivileges.add(priv.getNombre());
            });
            SelectDB.SelectPrivObj(role.getNombre()).forEach(priv -> {
                AllPrivileges.add(priv.getNombre() + " ON " + priv.getObjeto());
            });
        }
        return AllPrivileges;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Encabezado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tScript1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        ListaPrivSis = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        ListaPrivObj = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        tRolename = new javax.swing.JTextField();
        tUsername = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTabla1 = new javax.swing.JTable();
        bAgregarPrivSis = new javax.swing.JButton();
        bAgregarPrivObj = new javax.swing.JButton();
        bLimpiar1 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblTabla2 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        tUsername2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        Encabezado.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Encabezado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Encabezado.setText("Privilegios y Auditoría - Grupo 9");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nombre de usuario / rol");

        jButton1.setText("Generar Script");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tScript1.setEditable(false);
        tScript1.setColumns(20);
        tScript1.setRows(5);
        jScrollPane1.setViewportView(tScript1);

        jLabel2.setText("Sección 1");

        jLabel3.setText("Sección 2");

        jButton2.setText("Mostrar privilegios");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Sección 3");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jButton4.setText("Generar script");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Privilegios de Sistema");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Privilegios de Objetos");

        ListaPrivSis.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "ADMINISTER ANY SQL TUNING SET", "ADMINISTER DATABASE TRIGGER", "ADMINISTER KEY MANAGEMENT", "ADMINISTER RESOURCE MANAGER", "ADMINISTER SQL MANAGEMENT OBJECT", "ADMINISTER SQL TUNING SET", "ADVISOR", "ALTER ANY ANALYTIC VIEW", "ALTER ANY ASSEMBLY", "ALTER ANY ATTRIBUTE DIMENSION", "ALTER ANY CLUSTER", "ALTER ANY CUBE", "ALTER ANY CUBE BUILD PROCESS", "ALTER ANY CUBE DIMENSION", "ALTER ANY DIMENSION", "ALTER ANY EDITION", "ALTER ANY EVALUATION CONTEXT", "ALTER ANY HIERARCHY", "ALTER ANY INDEX", "ALTER ANY INDEXTYPE", "ALTER ANY LIBRARY", "ALTER ANY MATERIALIZED VIEW", "ALTER ANY MEASURE FOLDER", "ALTER ANY MINING MODEL", "ALTER ANY OPERATOR", "ALTER ANY OUTLINE", "ALTER ANY PROCEDURE", "ALTER ANY ROLE", "ALTER ANY RULE", "ALTER ANY RULE SET", "ALTER ANY SEQUENCE", "ALTER ANY SQL PROFILE", "ALTER ANY SQL TRANSLATION PROFILE", "ALTER ANY TABLE", "ALTER ANY TRIGGER", "ALTER ANY TYPE", "ALTER DATABASE", "ALTER DATABASE LINK", "ALTER LOCKDOWN PROFILE", "ALTER PROFILE", "ALTER PUBLIC DATABASE LINK", "ALTER RESOURCE COST", "ALTER ROLLBACK SEGMENT", "ALTER SESSION", "ALTER SYSTEM", "ALTER TABLESPACE", "ALTER USER", "ANALYZE ANY", "ANALYZE ANY DICTIONARY", "AUDIT ANY", "AUDIT SYSTEM", "BACKUP ANY TABLE", "BECOME USER", "CHANGE NOTIFICATION", "COMMENT ANY MINING MODEL", "COMMENT ANY TABLE", "CREATE ANALYTIC VIEW", "CREATE ANY ANALYTIC VIEW", "CREATE ANY ASSEMBLY", "CREATE ANY ATTRIBUTE DIMENSION", "CREATE ANY CLUSTER", "CREATE ANY CONTEXT", "CREATE ANY CREDENTIAL", "CREATE ANY CUBE", "CREATE ANY CUBE BUILD PROCESS", "CREATE ANY CUBE DIMENSION", "CREATE ANY DIMENSION", "CREATE ANY DIRECTORY", "CREATE ANY EDITION", "CREATE ANY EVALUATION CONTEXT", "CREATE ANY HIERARCHY", "CREATE ANY INDEX", "CREATE ANY INDEXTYPE", "CREATE ANY JOB", "CREATE ANY LIBRARY", "CREATE ANY MATERIALIZED VIEW", "CREATE ANY MEASURE FOLDER", "CREATE ANY MINING MODEL", "CREATE ANY OPERATOR", "CREATE ANY OUTLINE", "CREATE ANY PROCEDURE", "CREATE ANY RULE", "CREATE ANY RULE SET", "CREATE ANY SEQUENCE", "CREATE ANY SQL PROFILE", "CREATE ANY SQL TRANSLATION PROFILE", "CREATE ANY SYNONYM", "CREATE ANY TABLE", "CREATE ANY TRIGGER", "CREATE ANY TYPE", "CREATE ANY VIEW", "CREATE ASSEMBLY", "CREATE ATTRIBUTE DIMENSION", "CREATE CLUSTER", "CREATE CREDENTIAL", "CREATE CUBE", "CREATE CUBE BUILD PROCESS", "CREATE CUBE DIMENSION", "CREATE DATABASE LINK", "CREATE DIMENSION", "CREATE EVALUATION CONTEXT", "CREATE EXTERNAL JOB", "CREATE HIERARCHY", "CREATE INDEXTYPE", "CREATE JOB", "CREATE LIBRARY", "CREATE LOCKDOWN PROFILE", "CREATE MATERIALIZED VIEW", "CREATE MEASURE FOLDER", "CREATE MINING MODEL", "CREATE OPERATOR", "CREATE PLUGGABLE DATABASE", "CREATE PROCEDURE", "CREATE PROFILE", "CREATE PUBLIC DATABASE LINK", "CREATE PUBLIC SYNONYM", "CREATE ROLE", "CREATE ROLLBACK SEGMENT", "CREATE RULE", "CREATE RULE SET", "CREATE SEQUENCE", "CREATE SESSION", "CREATE SQL TRANSLATION PROFILE", "CREATE SYNONYM", "CREATE TABLE", "CREATE TABLESPACE", "CREATE TRIGGER", "CREATE TYPE", "CREATE USER", "CREATE VIEW", "DEBUG ANY PROCEDURE", "DEBUG CONNECT ANY", "DEBUG CONNECT SESSION", "DELETE ANY CUBE DIMENSION", "DELETE ANY MEASURE FOLDER", "DELETE ANY TABLE", "DEQUEUE ANY QUEUE", "DROP ANY ANALYTIC VIEW", "DROP ANY ASSEMBLY", "DROP ANY ATTRIBUTE DIMENSION", "DROP ANY CLUSTER", "DROP ANY CONTEXT", "DROP ANY CUBE", "DROP ANY CUBE BUILD PROCESS", "DROP ANY CUBE DIMENSION", "DROP ANY DIMENSION", "DROP ANY DIRECTORY", "DROP ANY EDITION", "DROP ANY EVALUATION CONTEXT", "DROP ANY HIERARCHY", "DROP ANY INDEX", "DROP ANY INDEXTYPE", "DROP ANY LIBRARY", "DROP ANY MATERIALIZED VIEW", "DROP ANY MEASURE FOLDER", "DROP ANY MINING MODEL", "DROP ANY OPERATOR", "DROP ANY OUTLINE", "DROP ANY PROCEDURE", "DROP ANY ROLE", "DROP ANY RULE", "DROP ANY RULE SET", "DROP ANY SEQUENCE", "DROP ANY SQL PROFILE", "DROP ANY SQL TRANSLATION PROFILE", "DROP ANY SYNONYM", "DROP ANY TABLE", "DROP ANY TRIGGER", "DROP ANY TYPE", "DROP ANY VIEW", "DROP LOCKDOWN PROFILE", "DROP PROFILE", "DROP PUBLIC DATABASE LINK", "DROP PUBLIC SYNONYM", "DROP ROLLBACK SEGMENT", "DROP TABLESPACE", "DROP USER", "EM EXPRESS CONNECT", "ENQUEUE ANY QUEUE", "EXECUTE ANY ASSEMBLY", "EXECUTE ANY CLASS", "EXECUTE ANY EVALUATION CONTEXT", "EXECUTE ANY INDEXTYPE", "EXECUTE ANY LIBRARY", "EXECUTE ANY OPERATOR", "EXECUTE ANY PROCEDURE", "EXECUTE ANY PROGRAM", "EXECUTE ANY RULE", "EXECUTE ANY RULE SET", "EXECUTE ANY TYPE", "EXECUTE ASSEMBLY", "EXEMPT ACCESS POLICY", "EXEMPT IDENTITY POLICY", "EXEMPT REDACTION POLICY", "EXPORT FULL DATABASE", "FLASHBACK ANY TABLE", "FLASHBACK ARCHIVE ADMINISTER", "FORCE ANY TRANSACTION", "FORCE TRANSACTION", "GLOBAL QUERY REWRITE", "GRANT ANY OBJECT PRIVILEGE", "GRANT ANY PRIVILEGE", "GRANT ANY ROLE", "IMPORT FULL DATABASE", "INHERIT ANY PRIVILEGES", "INHERIT ANY REMOTE PRIVILEGES", "INSERT ANY CUBE DIMENSION", "INSERT ANY MEASURE FOLDER", "INSERT ANY TABLE", "KEEP DATE TIME", "KEEP SYSGUID", "LOCK ANY TABLE", "LOGMINING", "MANAGE ANY FILE GROUP", "MANAGE ANY QUEUE", "MANAGE FILE GROUP", "MANAGE SCHEDULER", "MANAGE TABLESPACE", "MERGE ANY VIEW", "ON COMMIT REFRESH", "PURGE DBA_RECYCLEBIN", "QUERY REWRITE", "READ ANY ANALYTIC VIEW CACHE", "READ ANY FILE GROUP", "READ ANY TABLE", "REDEFINE ANY TABLE", "RESTRICTED SESSION", "RESUMABLE", "SELECT ANY CUBE", "SELECT ANY CUBE BUILD PROCESS", "SELECT ANY CUBE DIMENSION", "SELECT ANY DICTIONARY", "SELECT ANY MEASURE FOLDER", "SELECT ANY MINING MODEL", "SELECT ANY SEQUENCE", "SELECT ANY TABLE", "SELECT ANY TRANSACTION", "SET CONTAINER", "SYSBACKUP", "SYSDBA", "SYSDG", "SYSKM", "SYSOPER", "TEXT DATASTORE ACCESS", "TRANSLATE ANY SQL", "UNDER ANY TABLE", "UNDER ANY TYPE", "UNDER ANY VIEW", "UNLIMITED TABLESPACE", "UPDATE ANY CUBE", "UPDATE ANY CUBE BUILD PROCESS", "UPDATE ANY CUBE DIMENSION", "UPDATE ANY TABLE", "USE ANY JOB RESOURCE", "USE ANY SQL TRANSLATION PROFILE", "WRITE ANY ANALYTIC VIEW CACHE" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(ListaPrivSis);

        ListaPrivObj.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "ALTER", "DEBUG", "DELETE", "EXECUTE", "FLASHBACK", "INDEX", "INSERT", "ON COMMIT REFRESH", "QUERY REWRITE", "READ", "REFERENCES", "SELECT", "UNDER", "UPDATE", "WRITE" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(ListaPrivObj);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Nombre de rol");

        tRolename.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Privilegios Seleccionados");

        tblTabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Privilegios"
            }
        ));
        jScrollPane4.setViewportView(tblTabla1);

        bAgregarPrivSis.setText("Agregar");
        bAgregarPrivSis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarPrivSisActionPerformed(evt);
            }
        });

        bAgregarPrivObj.setText("Agregar");
        bAgregarPrivObj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarPrivObjActionPerformed(evt);
            }
        });

        bLimpiar1.setText("Limpiar");
        bLimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLimpiar1ActionPerformed(evt);
            }
        });

        tblTabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Privilegios"
            }
        ));
        jScrollPane7.setViewportView(tblTabla2);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nombre de usuario / rol");

        tUsername2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Encabezado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(bAgregarPrivSis)
                                .addGap(121, 121, 121)
                                .addComponent(bAgregarPrivObj))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(70, 70, 70)
                                    .addComponent(tRolename, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(tUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addComponent(bLimpiar1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(347, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(tUsername2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(346, 346, 346))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Encabezado)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(tRolename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tUsername2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bAgregarPrivSis)
                            .addComponent(bAgregarPrivObj)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(bLimpiar1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(344, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Listar Privilegios del Usuario:
        Set<String> s = new LinkedHashSet<>(AllPrivileges(tUsername2.getText()));
        SeleccionarDatos2(s);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Generar Script 1
        GenerarScript1();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bAgregarPrivSisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarPrivSisActionPerformed
        if(ListaPrivSis.getSelectedValue() != null){
            ListarPrivilegiosSis();
            SeleccionarDatos1();
        }else{
            JOptionPane.showMessageDialog(this, "Especificar privilegio de sistema.", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_bAgregarPrivSisActionPerformed

    private void bLimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLimpiar1ActionPerformed
        // Limpiar Privilegios Seleccionados
        listaPrivilegios.clear();
        SeleccionarDatos1();
    }//GEN-LAST:event_bLimpiar1ActionPerformed

    private void bAgregarPrivObjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarPrivObjActionPerformed
        // Agregar Privilegio de Objeto
        String Objeto = "";
        if(ListaPrivObj.getSelectedValue() != null){
            if(ListaPrivObj.getSelectedValue().equals("ALTER")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE, SEQUENCE): ", "ALTER", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("DELETE")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE, VIEW, MATERIALIZED VIEW): ", "DELETE", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("DEBUG")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE, VIEW, PROCEDURE, FUNCTION, PACKAGE, USER-DEFINED TYPE): ", "DEBUG", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("FLASHBACK")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE, VIEW, MATERIALIZED VIEW): ", "FLASHBACK", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("INDEX")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE): ", "INDEX", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("INSERT")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE, VIEW, MATERIALIZED VIEW): ", "INSERT", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("ON COMMIT REFRESH")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE): ", "ON COMMIT REFRESH", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("QUERY REWRITE")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE): ", "QUERY REWRITE", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("REFERENCES")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE, VIEW): ", "REFERENCES", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("SELECT")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE, VIEW): ", "SELECT", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("UPDATE")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (TABLE, VIEW, MATERIALIZED VIEW): ", "UPDATE", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("UNDER")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (VIEW, USER-DEFINED TYPE): ", "UNDER", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("EXECUTE")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (PROCEDURE, FUNCTION, PACKAGE, LIBRARY, USER-DEFINED TYPE, OPERATOR, INDEXTYPE): ", "EXECUTE", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("READ")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (DIRECTORY): ", "READ", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(ListaPrivObj.getSelectedValue().equals("WRITE")){
                Objeto = JOptionPane.showInputDialog(this, "Especificar objeto (DIRECTORY): ", "WRITE", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "Error en la selección de privilegios de objeto.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            
            if (Objeto == null || Objeto.equals("")){
                JOptionPane.showMessageDialog(this, "Especificar objeto.", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else{
                ListarPrivilegiosObj(Objeto);
                SeleccionarDatos1();
            }
            
        } else{
            JOptionPane.showMessageDialog(this, "Especificar privilegio de objeto.", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_bAgregarPrivObjActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Encabezado;
    private javax.swing.JList<String> ListaPrivObj;
    private javax.swing.JList<String> ListaPrivSis;
    private javax.swing.JButton bAgregarPrivObj;
    private javax.swing.JButton bAgregarPrivSis;
    private javax.swing.JButton bLimpiar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField tRolename;
    private javax.swing.JTextArea tScript1;
    private javax.swing.JTextField tUsername;
    private javax.swing.JTextField tUsername2;
    private javax.swing.JTable tblTabla1;
    private javax.swing.JTable tblTabla2;
    // End of variables declaration//GEN-END:variables
}
