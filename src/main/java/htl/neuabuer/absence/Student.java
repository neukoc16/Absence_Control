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

    private final int ID;
    private final String FirstName;
    private final String LastName;
    private final String Class;
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
        String sqlString = "SELECT SUM(end-entry) FROM log WHERE studentid = " + ID;
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void setEntry(LocalTime entry) throws SQLException {
        String sqlString = "UPDATE log SET entry = '" + entry.format(dtf) + "' WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        rs.next();
    }

    public void setExit(LocalTime exit) throws SQLException {
        String sqlString = "UPDATE log SET exit = '" + exit.format(dtf) + "' WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        rs.next();
    }

    public LocalTime getEntry() throws SQLException {
        String sqlString = "SELECT entry FROM log WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        if (rs.next()) {
            return rs.getTime(1).toLocalTime();
        }
        return null;
    }

    public LocalTime getExit() throws SQLException {
        String sqlString = "SELECT exit FROM log WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        if (rs.next()) {
            return rs.getTime(1).toLocalTime();
        }
        return null;

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
