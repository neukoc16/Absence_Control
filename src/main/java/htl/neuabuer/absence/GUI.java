package htl.neuabuer.absence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Konstantin
 */
public class GUI extends javax.swing.JFrame {

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private final String atr[] = new String[7];
    private final MyTableModel model = new MyTableModel();
    private Connection con;

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        tbstudents.setModel(model);
        tbstudents.setDefaultRenderer(Object.class, new MyTableCellRenderer());

        try {
            con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Students", "postgres", "postgres");
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        atr[0] = "studentid";
        atr[1] = "firstname";
        atr[2] = "lastname";
        atr[3] = "class";
        atr[4] = "entry";
        atr[5] = "exit";
        atr[6] = "absencecounter";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmmenu = new javax.swing.JPopupMenu();
        miexit = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbstudents = new javax.swing.JTable();
        btload = new javax.swing.JButton();

        miexit.setText("Absent");
        miexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miexitActionPerformed(evt);
            }
        });
        pmmenu.add(miexit);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbstudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbstudents.setComponentPopupMenu(pmmenu);
        jScrollPane1.setViewportView(tbstudents);

        btload.setText("Load");
        btload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(btload, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(btload, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(166, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btloadActionPerformed
        model.clear();

        try (java.sql.Statement stat = con.createStatement()) {
            String sqlString = "SELECT * FROM student WHERE studentid=1";
            ResultSet rs = stat.executeQuery(sqlString);
            while (rs.next()) {
                String name[] = new String[7];
                for (int i = 0; i < name.length; i++) {
                    name[i] = rs.getString(atr[i]);
                }

                Student s = new Student(
                        Integer.parseInt(name[0]),
                        name[1],
                        name[2],
                        name[3],
                        LocalDateTime.parse(name[4], dtf),
                        LocalDateTime.parse(name[5], dtf),
                        Integer.parseInt(name[6]));
                model.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btloadActionPerformed

    private void miexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miexitActionPerformed
        Student s = (Student) model.getValueAt(tbstudents.getSelectedRow(), tbstudents.getSelectedColumn());
        s.setExit(LocalDateTime.now());
        tbstudents.repaint();

        System.out.println(s.toString());

        try (java.sql.Statement stat = con.createStatement()) {
            String sqlString = String.format("UPDATE student "
                    + "SET exit = %s, entry=%s"
                    + "WHERE studentid=%d", s.getExit().format(dtf), s.getEntry().format(dtf), s.getID());
            ResultSet rs = stat.executeQuery(sqlString);
            while (rs.next()) {
            }
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_miexitActionPerformed
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btload;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem miexit;
    private javax.swing.JPopupMenu pmmenu;
    private javax.swing.JTable tbstudents;
    // End of variables declaration//GEN-END:variables
}
