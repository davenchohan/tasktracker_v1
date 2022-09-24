package cmpt213.assignment1.tasktracker;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that creates an ArrayList of tasks and uses Menu.java and Data.java to display a task tracker.
 *
 * @author Daven Chohan
 */

public class Main {

    public static void main(String[] args) {
        // Create cmpt213.assignment1.tasktracker.Menu
        List<Data> tasks = new ArrayList<>();
        Menu menu = new Menu("My Tasks", tasks);
        menu.readJSON();
        menu.setTitle();
        menu.display();

    }
}
