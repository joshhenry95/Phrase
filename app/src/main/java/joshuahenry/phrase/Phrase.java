package joshuahenry.phrase;

/**
 * Created by joshh_000 on 7/18/2018.
 */

public class Phrase {

    private String phrase;
    private String category;

    public Phrase(String phrase, String category) {
        this.phrase = phrase;
        this.category = category;
    }

    public String getPhraseStr() {
        return phrase;
    }

    public String getCategory() {
        return category;
    }
}
