package flashcards;

import java.util.Scanner;

public class CommonScanner {

    private static final Scanner instance = new Scanner(System.in);

    private CommonScanner() {}

    public static Scanner getInstance() {
        return instance;
    }
}
