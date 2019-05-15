package htl.neuabuer.absence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Konstantin
 */
public class Student {

    private int ID;
    private String FirstName;
    private String LastName;
    private String Class;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public Student(int ID, String FirstName, String LastName, String Class) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Class = Class;

//        if (!existsToday()) {
//            createToday();
//        }
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getClassName() {
        return Class;
    }

    public int getAbsenceCounter() throws SQLException {
        String sqlString = "SELECT SUM(end-entry) FROM student_absencess WHERE studentid = " + ID;
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        return rs.getInt(1);
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public void setClass(String Class) {
        this.Class = Class;
    }

    public void setEntry(LocalTime entry) throws SQLException {
        String sqlString = "UPDATE student_absencess SET entry = '" + entry.format(dtf) + "' WHERE studentid = " + ID + " AND date = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        //return rs.getTime(1).toLocalTime();
    }

    public void setExit(LocalTime exit) throws SQLException {
        String sqlString = "UPDATE student_absencess SET exit = '" + exit.format(dtf) + "' WHERE studentid = " + ID + " AND date = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        //return rs.getTime(1).toLocalTime();
    }

    public LocalTime getEntry() throws SQLException {
        String sqlString = "SELECT entry FROM student_absencess WHERE studentid = " + ID + " AND date = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet result = s.executeQuery(sqlString);
        return result.getTime(1).toLocalTime();
    }

    public LocalTime getExit() throws SQLException {
        String sqlString = "SELECT exit FROM student_absencess WHERE studentid = " + ID + " AND date = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet result = s.executeQuery(sqlString);
        return result.getTime(1).toLocalTime();
    }

    @Override
    public String toString() {
        try {
            return String.format("%d %s %s %s %d", ID, FirstName, LastName, Class, this.getAbsenceCounter());
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
