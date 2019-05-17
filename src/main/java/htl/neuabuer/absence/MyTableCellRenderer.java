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

public class MyTableCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        Student s = (Student) value;
        JLabel label = new JLabel();
        label.setOpaque(true);

        if (LocalTime.now().isAfter(s.getEnd(s.getToday()).toLocalTime()) || LocalTime.now().isBefore(LocalTime.of(8, 0))) {
            label.setBackground(Color.black);
            label.setForeground(Color.white);
        } else {
            try {
                if (LocalTime.now().isBefore(s.getExit().toLocalTime())) {
                    label.setBackground(Color.green);
                    label.setForeground(Color.black);
                } else {
                    label.setBackground(Color.red);
                    label.setForeground(Color.white);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MyTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (isSelected) {
            label.setBackground(Color.LIGHT_GRAY);
            label.setForeground(Color.black);
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
        }
        return label;
    }
}
