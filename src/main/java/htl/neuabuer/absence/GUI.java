package htl.neuabuer.absence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Konstantin
 */
public class GUI extends javax.swing.JFrame {

    private static Connection con;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private final String atr[] = new String[7];
    private final LocalTime end[] = new LocalTime[5];
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

        tbstudents.setModel(model);
        tbstudents.setDefaultRenderer(Object.class, new MyTableCellRenderer());

        atr[0] = "studentid";
        atr[1] = "firstname";
        atr[2] = "lastname";
        atr[3] = "class";
        atr[4] = "entry";
        atr[5] = "exit";
        atr[6] = "absencecounter";

        end[0] = LocalTime.of(16, 20);
        end[1] = LocalTime.of(16, 20);
        end[2] = LocalTime.of(13, 15);
        end[3] = LocalTime.of(16, 20);
        end[4] = LocalTime.of(13, 15);
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
                String sqlString = "SELECT * FROM student WHERE studentid=" + i;
                ResultSet rs = stat.executeQuery(sqlString);

                while (rs.next()) {
                    String name[] = new String[7];
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
        miexit = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbstudents = new javax.swing.JTable();

        miexit.setText("Absent");
        miexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miexitActionPerformed(evt);
            }
        });
        pmmenu.add(miexit);

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
            s.setExit(LocalTime.now());
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (java.sql.Statement stat = con.createStatement()) {
            String sqlString
                    = "UPDATE student SET exit = '" + s.getExit().format(dtf) + "', entry= '" + s.getEntry().format(dtf)
                    + "' WHERE studentid=" + s.getID();
            stat.execute(sqlString);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        load();
        tbstudents.repaint();
    }//GEN-LAST:event_miexitActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        load();
    }//GEN-LAST:event_formWindowOpened
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
    private javax.swing.JMenuItem miexit;
    private javax.swing.JPopupMenu pmmenu;
    private javax.swing.JTable tbstudents;
    // End of variables declaration//GEN-END:variables
}
