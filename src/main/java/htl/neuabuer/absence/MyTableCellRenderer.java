package htl.neuabuer.absence;

import java.awt.Color;
import java.awt.Component;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Konstantin
 */
public class MyTableCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        Student s = (Student) value;
        JLabel label = new JLabel();
        label.setOpaque(true);

        try {
            if (s.getExit().isBefore(LocalTime.now())) {
                label.setBackground(Color.red);
                label.setForeground(Color.white);
            } else {
                label.setBackground(Color.green);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (isSelected) {
            label.setBackground(Color.LIGHT_GRAY);
        }

        switch (column) {
            case 0:
                label.setText(s.getID() + "");
                break;
            case 1:
                label.setText(s.getFirstName());
                break;
            case 2:
                label.setText(s.getLastName());
                break;
            case 3:
                label.setText(s.getClassName());
                break;
//            case 4: {
//                try {
//                    label.setText(s.getEntry().format(dtf));
//                } catch (SQLException ex) {
//                    Logger.getLogger(MyTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            break;
//            case 5: {
//                try {
//                    label.setText(s.getExit().format(dtf));
//                } catch (SQLException ex) {
//                    Logger.getLogger(MyTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            break;
//            case 6: {
//                try {
//                    label.setText(s.getAbsenceCounter() + "");
//                } catch (SQLException ex) {
//                    Logger.getLogger(MyTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            break;
        }
        return label;
    }
}
