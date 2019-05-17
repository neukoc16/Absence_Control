package htl.neuabuer.absence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Student Object with DB-Properties
 * Timekeeping done here
 * Absences calculated here
 */
public class Student {

    private final DateTimeFormatter abstimedtf = DateTimeFormatter.ofPattern("LL dd HH:mm:ss yyyy");
    private final LocalDateTime end[] = new LocalDateTime[5];
    private final DayOfWeek dow;
    private final int today;

    private final int ID;
    private final String FirstName;
    private final String LastName;
    private final String Class;

    public Student(int ID, String FirstName, String LastName, String Class) throws SQLException {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Class = Class;

        end[0] = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 20));
        end[1] = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 20));
        end[2] = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 15));
        end[3] = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 20));
        end[4] = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 15));

        dow = LocalDate.now().getDayOfWeek();
        today = dow.getValue() - 1;

        if (!existsToday()) {
            createToday();
        }
    }

    public int getID() {
        return ID;
    }

    public LocalDateTime getEnd(int idx) {
        return end[idx];
    }

    public int getToday() {
        return today;
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

    public int getAbsenceCounterAll() throws SQLException {
        String sqlString = "SELECT SUM(DATE_PART('hour', (endt-startt)-(exit-entry))) "
                + "FROM log WHERE studentid = " + ID;
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int getAbsenceCounterToday() throws SQLException {
        String sqlString = "SELECT DATE_PART('hour', (endt-startt)-(exit-entry)) "
                + "FROM log WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void setEntry(LocalDateTime entry) throws SQLException {
        String sqlString = "UPDATE log SET entry = '" + entry.format(abstimedtf) + "' WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        s.executeUpdate(sqlString);
    }

    public void setExit(LocalDateTime exit) throws SQLException {
        String sqlString = "UPDATE log SET exit = '" + exit.format(abstimedtf) + "' WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        s.executeUpdate(sqlString);
    }

    public LocalDateTime getEntry() throws SQLException {
        String sqlString = "SELECT entry FROM log WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        if (rs.next()) {
            return rs.getTimestamp(1).toLocalDateTime();
        }
        return null;
    }

    public LocalDateTime getExit() throws SQLException {
        String sqlString = "SELECT exit FROM log WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        if (rs.next()) {
            return rs.getTimestamp(1).toLocalDateTime();
        }
        return null;
    }

    public boolean existsToday() throws SQLException {
        String sqlString = "SELECT * FROM log WHERE studentid = " + ID + " AND cdate = '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "'";
        Statement s = GUI.getCon().createStatement();
        ResultSet rs = s.executeQuery(sqlString);
        while (rs.next()) {
            if (rs.getString(1) != null) {
                return true;
            }
        }
        return false;
    }

    public void createToday() throws SQLException {
        String sqlString = "INSERT INTO log (studentid, cdate, entry, exit, endt, startt)"
                + " VALUES (" + ID + ", '" + LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                + "', '" + LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)).format(abstimedtf)
                + "', '" + end[today].format(abstimedtf)
                + "', '" + end[today].format(abstimedtf)
                + "', '" + LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)).format(abstimedtf)
                + "')";
        Statement s = GUI.getCon().createStatement();
        s.executeUpdate(sqlString);
    }

    @Override
    public String toString() {
        try {
            return String.format("%d %s %s %s %d", ID, FirstName, LastName, Class, this.getAbsenceCounterAll());
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
