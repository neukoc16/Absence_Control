package htl.neuabuer.absence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Konstantin
 */
public class GUI extends javax.swing.JFrame {

    private static Connection con;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private final String atr[] = new String[4];
    private final Runnable run;
    private final MyTableModel model = new MyTableModel();
    private int count;

    static {
        try {
            con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Students", "postgres", "postgres");
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GUI() {
        initComponents();

        run = () -> {
            while (true) {
                load();
                tbstudents.repaint();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Thread thread = new Thread(run);
        thread.start();

        tbstudents.setModel(model);
        tbstudents.setDefaultRenderer(Object.class, new MyTableCellRenderer());

        atr[0] = "id";
        atr[1] = "firstname";
        atr[2] = "lastname";
        atr[3] = "class";
    }

    public static Connection getCon() {
        return con;
    }

    public void load() {
        model.clear();
        try (java.sql.Statement stat = con.createStatement()) {
            String sqlString = "SELECT COUNT(*) FROM student";
            ResultSet rs = stat.executeQuery(sqlString);
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 1; i < count + 1; i++) {
            try (java.sql.Statement stat = con.createStatement()) {
                String sqlString = "SELECT * FROM student WHERE id=" + i;
                ResultSet rs = stat.executeQuery(sqlString);

                while (rs.next()) {
                    String name[] = new String[4];
                    for (int j = 0; j < name.length; j++) {
                        name[j] = rs.getString(atr[j]);
                    }

                    Student s = new Student(
                            Integer.parseInt(name[0]),
                            name[1],
                            name[2],
                            name[3]);
                    model.add(s);
                }
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmmenu = new javax.swing.JPopupMenu();
        mientry = new javax.swing.JMenuItem();
        miexit = new javax.swing.JMenuItem();
        menabs = new javax.swing.JMenu();
        miabsall = new javax.swing.JMenuItem();
        miabstoday = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbstudents = new javax.swing.JTable();

        mientry.setText("Entry");
        mientry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mientryActionPerformed(evt);
            }
        });
        pmmenu.add(mientry);

        miexit.setText("Exit");
        miexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miexitActionPerformed(evt);
            }
        });
        pmmenu.add(miexit);

        menabs.setText("Absence");

        miabsall.setText("show ths year's Absence");
        miabsall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miabsallActionPerformed(evt);
            }
        });
        menabs.add(miabsall);

        miabstoday.setText("show today's Absence");
        miabstoday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miabstodayActionPerformed(evt);
            }
        });
        menabs.add(miabstoday);

        pmmenu.add(menabs);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miexitActionPerformed
        Student s = (Student) model.getValueAt(tbstudents.getSelectedRow(), 1);

        try {
            s.setExit(LocalDateTime.now());
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.fireTableDataChanged();

        load();
        tbstudents.repaint();
    }//GEN-LAST:event_miexitActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        load();
    }//GEN-LAST:event_formWindowOpened

    private void miabsallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miabsallActionPerformed
        Student s = (Student) model.getValueAt(tbstudents.getSelectedRow(), 1);

        try {
            JOptionPane.showMessageDialog(null, "All Abence-Hours in this year: " + s.getAbsenceCounterAll());
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_miabsallActionPerformed

    private void mientryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mientryActionPerformed
        Student s = (Student) model.getValueAt(tbstudents.getSelectedRow(), 1);

        try {
            s.setEntry(LocalDateTime.now());
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.fireTableDataChanged();

        load();
        tbstudents.repaint();
    }//GEN-LAST:event_mientryActionPerformed

    private void miabstodayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miabstodayActionPerformed
        Student s = (Student) model.getValueAt(tbstudents.getSelectedRow(), 1);

        try {
            JOptionPane.showMessageDialog(null, "Today's Absence-Hours: " + s.getAbsenceCounterToday());
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_miabstodayActionPerformed
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JMenu menabs;
    private javax.swing.JMenuItem miabsall;
    private javax.swing.JMenuItem miabstoday;
    private javax.swing.JMenuItem mientry;
    private javax.swing.JMenuItem miexit;
    private javax.swing.JPopupMenu pmmenu;
    private javax.swing.JTable tbstudents;
    // End of variables declaration//GEN-END:variables
}
