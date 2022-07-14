package flashcards;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MyLogger {

    private final StringBuilder sb = new StringBuilder();
    final Scanner sc = CommonScanner.getInstance();
    private static final MyLogger logger = new MyLogger();

    private MyLogger() {}

    public static MyLogger getInstance() {
        return logger;
    }

    public void print(String text) {
        print(text, false);
    }

    public void print(String text, boolean onlyLog) {
        if (!onlyLog) {
            System.out.println(text);
        }
        sb.append(text).append("\n");
    }

    public void saveToFile() {
        print("File name:");
        String fileName = sc.nextLine();
        print(fileName, true);
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            print("Failed to save the log to the file.");
            return;
        }
        print("The log has been saved.");
    }
}
