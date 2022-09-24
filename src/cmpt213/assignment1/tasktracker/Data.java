package cmpt213.assignment1.tasktracker;

import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * A class that creates a data object which holds a task including specific information such as name, notes, due date and if it is completed.
 *
 * @author Daven Chohan
 */
public class Data {
    private boolean isCompleted;
    private final String name;
    private final String notes;
    private final GregorianCalendar dueDate;

    public Data(boolean isCompleted, String name, String notes, GregorianCalendar dueDate) {
        this.isCompleted = isCompleted;
        this.name = name;
        this.notes = notes;
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getName() {
        return name;
    }

    public GregorianCalendar getDueDate() {
        return dueDate;
    }

    public String fixHour() {
        int hour = dueDate.get(Calendar.HOUR_OF_DAY);
        return String.format("%02d", hour);
    }

    public String fixMinute() {
        int minute = dueDate.get(Calendar.MINUTE);
        return String.format("%02d", minute);
    }

    public int fixMonth() {
        int month = dueDate.get(Calendar.MONTH);
        return month + 1;
    }

    public String outputInfo() {
        return "Task: " + name +
                "\nNotes: " + notes +
                "\nDue date: " + dueDate.get(Calendar.YEAR) + "-" + fixMonth() + "-" + dueDate.get(Calendar.DAY_OF_MONTH) + " " + fixHour() + ":"
                + fixMinute();
    }

    @Override
    public String toString() {
        String tempBool;
        if (isCompleted()) {
            tempBool = "Yes";
        } else {
            tempBool = "No";
        }
        return "Task: " + name +
                "\nNotes: " + notes +
                "\nDue date: " + dueDate.get(Calendar.YEAR) + "-" + fixMonth() + "-" + dueDate.get(Calendar.DAY_OF_MONTH) + " " + fixHour() + ":"
                + fixMinute() +
                "\nCompleted? " + tempBool;
    }
}
