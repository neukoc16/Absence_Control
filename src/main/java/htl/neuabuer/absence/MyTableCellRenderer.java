package htl.neuabuer.absence;

import java.awt.Component;
import java.time.format.DateTimeFormatter;
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
            case 4:
                label.setText(s.getEntry().format(dtf));
                break;
            case 5:
                label.setText(s.getExit().format(dtf));
                break;
            case 6:
                label.setText(s.getAbsenceCounter() + "");
                break;
        }
        return label;
    }

}
