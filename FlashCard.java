package flashcards;

public class FlashCard {

    private final String term;
    private final String definition;
    private int mistakes;

    public FlashCard(String term, String definition) {
        this(term, definition, 0);
    }

    public FlashCard(String term, String definition, int mistakes) {
        this.term = term;
        this.definition = definition;
        this.mistakes = Math.max(0, mistakes);
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public boolean checkAnswer(String str) {
        boolean isCorrect = str != null && str.equals(definition);
        if (!isCorrect) {
            mistakes++;
        }
        return isCorrect;
    }

    @Override
    public int hashCode() {
        return term.hashCode();
    }
}
