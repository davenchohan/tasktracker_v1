package cmpt213.assignment1.tasktracker;

import com.google.gson.*;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 * A class that creates a menu object with several options that can be used to edit/display certain things from an ArrayList of tasks.
 *
 * @author Daven Chohan
 */
public class Menu {

    private String title;
    private final String[] options = new String[7];
    private final List<Data> tasks;

    public Menu(String title, List<Data> tasks) {
        this.title = title;
        this.tasks = tasks;
    }

    private String getTitle() {
        return "\n" + title;
    }

    public void setTitle() {
        int titleLength = title.length();
        title = "\n# " + title + " #\n";
        for (int i = 0; i < (titleLength + 4) * 2; i++) {
            if (i < titleLength + 4) {
                title = '#' + title;
            } else {
                title = title + '#';
            }
        }
    }

    private void setOptions() {
        options[0] = "List all tasks";
        options[1] = "Add a new task";
        options[2] = "Remove a task";
        options[3] = "Mark a task as completed";
        options[4] = "List overdue incomplete tasks";
        options[5] = "List upcoming incomplete tasks";
        options[6] = "Exit";
    }

    private String getOptions() {
        String tempOptions = "";
        for (int i = 0; i < options.length; i++) {
            if (i != options.length - 1) {
                tempOptions = tempOptions + (i + 1) + ": " + options[i] + '\n';
            } else {
                tempOptions = tempOptions + (i + 1) + ": " + options[i];
            }
        }
        return tempOptions;
    }

    private int question() {
        int userChoice;
        System.out.print("Choose an option by entering 1-7: ");
        Scanner console = new Scanner(System.in);
        userChoice = console.nextInt();
        return userChoice;
    }

    private void chooseQuestion(int userChoice) {
        switch (userChoice) {
            case 1 -> listTasks();
            case 2 -> addTask();
            case 3 -> removeTask();
            case 4 -> completeTask();
            case 5 -> overdueTasks();
            case 6 -> upcomingTasks();
            case 7 -> exitProgram();
            default -> {
                System.out.println("Invalid selection. Enter a number between 1 and 7");
                chooseQuestion(question());
            }
        }
    }

    private void listTasks() {
        sortTask();
        int taskNumber = 1;
        if (tasks.isEmpty()) {
            System.out.println("No tasks to show.");
        } else {
            for (Data data : tasks) {
                System.out.println("\nTask #" + taskNumber + "\n" + data);
                taskNumber++;
            }
        }
        display();
    }

    private void addTask() {
        String name;
        String notes;
        int year;
        int month;
        int day;
        int hour;
        int minute;
        System.out.print("Enter the name of the new task: ");
        Scanner console = new Scanner(System.in);
        name = console.nextLine();
        if (name.isBlank()) {
            System.out.println("The task must have a name. Please enter one.");
            addTask();
            return;
        }
        System.out.print("Enter the notes of the new task: ");
        notes = console.nextLine();
        if (notes.isEmpty()) {
            notes = " ";
        }
        while (true) {
            try {
                do {
                    System.out.print("Enter the year of the due date: ");
                    year = console.nextInt();
                    if (year < 0) {
                        System.out.println("Please enter a valid year.");
                    }
                }
                while (year < 0);
                do {
                    System.out.print("Enter the month of the due date (1-12): ");
                    month = console.nextInt();
                    if (month > 12 || month < 1) {
                        System.out.println("Please enter a valid month (1-12).");
                    }
                }
                while (month > 12 || month < 1);
                do {
                    System.out.print("Enter the day of the due date (1-28/29/30/31): ");
                    day = console.nextInt();
                    if (day > 31 || day < 1) {
                        System.out.println("Please enter a valid day (1-28/29/30/31).");
                    }
                }
                while (day > 31 || day < 1);
                LocalDate date = LocalDate.of(year, month, day);
                break;
            } catch (DateTimeException e) {
                System.out.println("The date entered is invalid, please try again.");
            }
        }
        do {
            System.out.print("Enter the hour of the due date (0-23): ");
            hour = console.nextInt();
            if (hour > 23 || hour < 0) {
                System.out.println("Please enter a valid hour (0-23).");
            }
        }
        while (hour > 23 || hour < 0);
        do {
            System.out.print("Enter the minute of the due date (0-59): ");
            minute = console.nextInt();
            if (minute > 59 || minute < 0) {
                System.out.println("Please enter a valid minute (0-23).");
            }
        }
        while (minute > 59 || minute < 0);
        System.out.println("Task " + name + " has been added to the list of tasks.");
        month--;
        tasks.add(new Data(false, name, notes, new GregorianCalendar(year, month, day, hour, minute)));
        display();
    }

    private void removeTask() {
        sortTask();
        int taskNumber = 1;
        if (tasks.isEmpty()) {
            System.out.println("No tasks to show.");
            display();
            return;
        } else {
            for (Data data : tasks) {
                System.out.println("\nTask #" + taskNumber + "\n" + data);
                taskNumber++;
            }
        }
        System.out.print("\nEnter the task number you want to remove (0 to cancel): ");
        Scanner console = new Scanner(System.in);
        int choice = console.nextInt();
        if (choice == 0) {
            System.out.println("Cancelled.");
        } else if (choice <= tasks.size() && choice > 0) {
            System.out.println("Task " + tasks.get(choice - 1).getName() + " is now removed.");
            tasks.remove(choice - 1);
        } else {
            System.out.println("Invalid Choice. Please try again.");
            removeTask();
            return;
        }
        display();
    }

    private void completeTask() {
        int taskNumber = 0;
        boolean hasTasks = false;
        sortTask();
        for (Data data : tasks) {
            taskNumber++;
            if (!data.isCompleted()) {
                System.out.println("\nTask #" + taskNumber + "\n" + data.outputInfo());
                hasTasks = true;
            }
        }
        if (!hasTasks) {
            System.out.println("No incomplete tasks to show.");
            display();
            return;
        }
        System.out.print("\nEnter the task number you want to mark as completed (0 to cancel): ");
        Scanner console = new Scanner(System.in);
        int choice = console.nextInt();
        if (choice == 0) {
            System.out.print("\nCancelled.");
        } else if (choice <= tasks.size() && choice > 0) {
            tasks.get(choice - 1).setCompleted(true);
            System.out.println("Task " + tasks.get(choice - 1).getName() + " is now completed.");
        } else {
            System.out.println("Invalid Choice. Please try again.");
            completeTask();
            return;
        }
        display();
    }

    private void sortTask() {
        int taskSize = tasks.size();
        Data tempData;
        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < taskSize - 1; i++) {
                if (tasks.get(i).getDueDate().compareTo(tasks.get(i + 1).getDueDate()) > 0) {
                    tempData = tasks.get(i);
                    tasks.set(i, tasks.get(i + 1));
                    tasks.set(i + 1, tempData);
                    isSorted = false;
                }
            }
        }
    }

    private void overdueTasks() {
        sortTask();
        int taskNumber = 0;
        boolean hasTasks = false;
        GregorianCalendar currentTime = new GregorianCalendar();
        for (Data task : tasks) {
            if (task.getDueDate().compareTo(currentTime) < 0 && !task.isCompleted()) {
                taskNumber++;
                System.out.println("\nTask #" + taskNumber + "\n" + task.outputInfo());
                hasTasks = true;
            }
        }
        if (!hasTasks) {
            System.out.println("No overdue incomplete tasks to show.");
            display();
            return;
        }
        display();
    }

    private void upcomingTasks() {
        sortTask();
        int taskNumber = 0;
        boolean hasTasks = false;
        GregorianCalendar currentTime = new GregorianCalendar();
        for (Data task : tasks) {
            if (task.getDueDate().compareTo(currentTime) > 0 && !task.isCompleted()) {
                taskNumber++;
                System.out.println("\nTask #" + taskNumber + "\n" + task.outputInfo());
                hasTasks = true;
            }
        }
        if (!hasTasks) {
            System.out.println("No upcoming incomplete tasks to show.");
            display();
            return;
        }
        display();
    }

    private void exitProgram() {
        System.out.println("Thank you for using the system.");
        createJSON();
    }

    private void createJSON() {
        // Learned partly from GSON tutorial from Future Studio
        String directory = System.getProperty("user.dir") + "/data.json";
        Gson gson = new Gson();
        String json = gson.toJson(tasks);
        try {
            FileWriter fileWriter = new FileWriter(directory);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred in writing to the json file.");
            e.printStackTrace();
        }
    }

    public void readJSON() {
        // Learned from Professor Brian Fraser's tutorial on JSON
        String directory = System.getProperty("user.dir") + "/data.json";
        File myFile = new File(directory);
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred in creating the json file.");
            e.printStackTrace();
        }
        try {
            if (myFile.length() == 0) {
                return;
            }
            JsonElement fileElement = JsonParser.parseReader(new FileReader(myFile));

            // Processing all tasks
            JsonArray jsonArrayOfTasks = fileElement.getAsJsonArray();
            for (JsonElement taskElement : jsonArrayOfTasks) {
                // Get the JsonObject:
                JsonObject taskJsonObject = taskElement.getAsJsonObject();

                // Extract data
                String name = taskJsonObject.get("name").getAsString();
                String notes = taskJsonObject.get("notes").getAsString();
                boolean isCompleted = taskJsonObject.get("isCompleted").getAsBoolean();
                JsonObject tempJsonObject = taskJsonObject.get("dueDate").getAsJsonObject();

                int year = tempJsonObject.get("year").getAsInt();
                int month = tempJsonObject.get("month").getAsInt();
                int day = tempJsonObject.get("dayOfMonth").getAsInt();
                int hour = tempJsonObject.get("hourOfDay").getAsInt();
                int minute = tempJsonObject.get("minute").getAsInt();

                tasks.add(new Data(isCompleted, name, notes, new GregorianCalendar(year, month, day, hour, minute)));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found.");
            e.printStackTrace();
        }
    }

    public void display() {
        setOptions();
        System.out.println(getTitle());
        System.out.println(getOptions());
        chooseQuestion(question());
    }
}
