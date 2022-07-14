package flashcards;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private final static Scanner sc = CommonScanner.getInstance();
    private final static MyLogger logger = MyLogger.getInstance();
    private final static DeckHandler deckHandler = new DeckHandler();

    public static void main(String[] args) {
        HashMap<String, String> arguments = new HashMap<>();
        for (int i = 1; i < args.length; i += 2) {
            String key = args[i - 1];
            String value = args[i];
            arguments.put(key, value);
        }

        if (arguments.containsKey("-import")) {
            deckHandler.importCards(arguments.get("-import"));
        }

        while (true) {
            logger.print("Input the action (add, remove, import, export, ask, exit" +
                    ", log, hardest card, reset stats):");
            String command = sc.nextLine();
            logger.print(command, true);
            switch (command.trim().toLowerCase()) {
                case "add":
                    deckHandler.addCard();
                    break;
                case "remove":
                    deckHandler.removeCard();
                    break;
                case "ask":
                    deckHandler.askForCards();
                    break;
                case "import":
                    deckHandler.importCards();
                    break;
                case "export":
                    deckHandler.exportCards();
                    break;
                case "hardest card":
                    deckHandler.findHardestCard();
                    break;
                case "reset stats":
                    deckHandler.resetStats();
                    break;
                case "log":
                    logger.saveToFile();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    if (arguments.containsKey("-export")) {
                        deckHandler.exportCards(arguments.get("-export"));
                    }
                    return;
            }
            logger.print("");
        }
    }
}
