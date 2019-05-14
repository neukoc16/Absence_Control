package htl.neuabuer.absence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Konstantin
 */
public class Student {

    private int ID;
    private String FirstName;
    private String LastName;
    private String Class;
    private LocalDateTime entry;
    private LocalDateTime exit;
    private int AbsenceCounter;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public Student(int ID, String FirstName, String LastName, String Class, LocalDateTime entry, LocalDateTime exit, int AbsenceCounter) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Class = Class;
        this.entry = entry;
        this.exit = exit;
        this.AbsenceCounter = AbsenceCounter;
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

    public LocalDateTime getEntry() {
        return entry;
    }

    public LocalDateTime getExit() {
        return exit;
    }

    public int getAbsenceCounter() {
        return AbsenceCounter;
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

    public void setEntry(LocalDateTime entry) {
        this.entry = entry;
    }

    public void setExit(LocalDateTime exit) {
        this.exit = exit;
    }

    public void setAbsenceCounter(int AbsenceCounter) {
        this.AbsenceCounter = AbsenceCounter;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s %s %s %s %d", ID, FirstName, LastName, Class,
                entry.format(dtf),
                exit.format(dtf), AbsenceCounter);
    }
}
