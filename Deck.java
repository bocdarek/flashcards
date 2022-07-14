package flashcards;

import java.util.*;

public class Deck {

    private final Set<FlashCard> cards = new HashSet<>();

    public Set<FlashCard> getCards() {
        return cards;
    }

    public FlashCard getRandomCard() {
        List<FlashCard> cardList = new ArrayList<>(cards);
        int index = (int) (cardList.size() * Math.random());
        return cardList.get(index);
    }

    public void add(FlashCard card) {
        cards.add(card);
    }

    public void add(String term, String definition) {
        add(term, definition, 0);
    }

    public void add(String term, String definition, int mistakes) {
        FlashCard card = new FlashCard(term, definition, mistakes);
        cards.add(card);
    }

    public boolean remove(FlashCard card) {
        if (cards.contains(card)) {
            cards.remove(card);
            return true;
        }
        return false;
    }

    public boolean remove(String term) {
        FlashCard card = getByTerm(term);
        if (cards.contains(card)) {
            cards.remove(card);
            return true;
        }
        return false;
    }

    public boolean isTermPresent(String term) {
        for (FlashCard card : cards) {
            if (card.getTerm().equalsIgnoreCase(term)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDefinitionPresent(String def) {
        for (FlashCard card : cards) {
            if (card.getDefinition().equalsIgnoreCase(def)) {
                return true;
            }
        }
        return false;
    }

    public FlashCard getByDefinition(String def) {
        for (FlashCard card : cards) {
            if (card.getDefinition().equalsIgnoreCase(def)) {
                return card;
            }
        }
        return null;
    }

    public FlashCard getByTerm(String term) {
        for (FlashCard card : cards) {
            if (card.getTerm().equalsIgnoreCase(term)) {
                return card;
            }
        }
        return null;
    }
}
