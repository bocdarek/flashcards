package flashcards;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeckHandler {

    private final Deck deck = new Deck();
    private final Scanner sc = CommonScanner.getInstance();
    final MyLogger logger = MyLogger.getInstance();

    public void addCard() {
        logger.print("The Card:");
        String term = sc.nextLine();
        logger.print(term, true);
        term = term.trim();
        if (deck.isTermPresent(term)) {
            logger.print(String.format("The card \"%s\" already exists.", term));
            return;
        }
        logger.print("The definition of the card:");
        String definition = sc.nextLine();
        logger.print(definition, true);
        definition = definition.trim();
        if (deck.isDefinitionPresent(definition)) {
            logger.print(String.format("The definition \"%s\" already exists.", definition));
            return;
        }
        deck.add(term, definition);
        logger.print(String.format("The pair (\"%s\":\"%s\") has been added.", term, definition));
    }

    public void removeCard() {
        logger.print("Which card?");
        String term = sc.nextLine();
        logger.print(term, true);
        term = term.trim();
        boolean isRemoved = deck.remove(term);
        if (isRemoved) {
            logger.print("The card has been removed.");
        } else {
            logger.print(String.format("Can't remove \"%s\": there is no such card.", term));
        }
    }

    public void askForCards() {
        if (deck.getCards().size() == 0) {
            logger.print("The deck is empty.");
            return;
        }
        logger.print("How many times to ask?");
        String num = sc.nextLine();
        logger.print(num, true);
        num = num.trim();
        if (!num.matches("^[1-9]\\d*$")) {
            return;
        }
        int turns = Integer.parseInt(num);
        for (int i = 0; i < turns; i++) {
            askForCard();
        }
    }

    private void askForCard() {
        FlashCard card = deck.getRandomCard();
        logger.print(String.format("Print the definition of \"%s\":", card.getTerm()));
        String answer = sc.nextLine();
        logger.print(answer, true);
        answer = answer.trim();
        if (card.checkAnswer(answer)) {
            logger.print("Correct!");
        } else if (deck.isDefinitionPresent(answer)) {
            logger.print(String.format("Wrong. The right answer is \"%s\", " +
                            "but your definition is correct for \"%s\".",
                    card.getDefinition(), deck.getByDefinition(answer).getTerm()));
        } else {
            logger.print(String.format("Wrong. The right answer is \"%s\".", card.getDefinition()));
        }
    }

    public void importCards() {
        String fileName = getFileName();
        importCards(fileName);
    }

    public void importCards(String fileName) {
        int loadedCards = 0;
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNext()) {
                String nextRow = fileScanner.nextLine();
                String[] cardData = nextRow.split("::");
                if (cardData.length < 2) {
                    continue;
                }
                String term = cardData[0];
                String definition = cardData[1];
                int mistakes;
                try {
                    mistakes = Integer.parseInt(cardData[2]);
                } catch (IndexOutOfBoundsException e) {
                    mistakes = 0;
                }
                if (deck.isTermPresent(term)) {
                    deck.remove(term);
                }
                deck.add(term, definition, mistakes);
                loadedCards++;
            }
        } catch (IOException e) {
            logger.print("File not found");
            return;
        }
        logger.print(String.format("%d cards have been loaded.", loadedCards));
    }

    public void exportCards() {
        String fileName = getFileName();
        exportCards(fileName);
    }

    public void exportCards(String fileName) {
        int savedCards = 0;
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (FlashCard card : deck.getCards()) {
                writer.printf("%s::%s::%S%n", card.getTerm(), card.getDefinition(), card.getMistakes());
                savedCards++;
            }
        } catch (IOException e) {
            logger.print("Failed to save to the file.");
            return;
        }
        logger.print(String.format("%d cards have been saved.", savedCards));
    }

    public String getFileName() {
        logger.print("File name:");
        String fileName = sc.nextLine();
        logger.print(fileName, true);
        return fileName.trim();
    }

    public void findHardestCard() {
        List<String> hardestTerms = new ArrayList<>();
        int maxMistakes = 0;
        for (FlashCard card : deck.getCards()) {
            if (card.getMistakes() == maxMistakes) {
                hardestTerms.add(card.getTerm());
            } else if (card.getMistakes() > maxMistakes) {
                maxMistakes = card.getMistakes();
                hardestTerms.clear();
                hardestTerms.add(card.getTerm());
            }
        }
        printHardestTerms(hardestTerms, maxMistakes);
    }

    public void printHardestTerms(List<String> terms, int mistakes) {
        if (mistakes == 0) {
            logger.print("There are no cards with errors.");
        } else if (terms.size() == 1) {
            logger.print(String.format("The hardest card is \"%s\". You have %d errors answering it.",
                    terms.get(0), mistakes));
        } else {
            logger.print(String.format("The hardest cards are \"%s\". You have %d errors answering them.",
                    String.join("\", \"", terms), mistakes));
        }
    }

    public void resetStats() {
        for (FlashCard card : deck.getCards()) {
            card.setMistakes(0);
        }
        logger.print("Card statistics have been reset.");
    }
}
