package htl.neuabuer.absence;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/*
 * Model for Student-Objects to synch with DB
 */
public class MyTableModel extends AbstractTableModel {

    private static final String[] colNames = {"ID", "First Name", "Last Name", "Class"};
    private final ArrayList<Student> students = new ArrayList<>();

    public void add(Student s) {
        students.add(s);
        fireTableRowsInserted(students.size() - 1, students.size() - 1);
    }

    public void remove(int idx) {
        students.remove(idx);
        fireTableRowsDeleted(idx, idx);
    }

    @Override
    public String getColumnName(int i) {
        return colNames[i];
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIdx, int colIdx) {
        Student s = students.get(rowIdx);
        return s;
    }

    public void clear() {
        students.clear();
    }
}
