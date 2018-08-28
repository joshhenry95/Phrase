package joshuahenry.phrase;

/**
 * Created by joshh_000 on 7/18/2018.
 */
//TODO: Remove unused imports
import android.content.Context;
//import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Phrases {

    public static List<Phrase> phrases = new ArrayList<Phrase>();
    public static Phrase activePhrase;

    public static void createPhraseList(BufferedReader bufReader) {
        // Exit app if we can't parse the file!
        try {
            String currLine;

            while ((currLine = bufReader.readLine()) != null) {

                if (currLine.length() <= 28) {
                    String phrase = currLine.substring(currLine.indexOf(':') + 1, currLine.length());
                    String category = currLine.substring(0, currLine.indexOf(':'));

                    if (isValid(phrase)) {
                        Phrase currPhrase = new Phrase(phrase, category);
                        phrases.add(currPhrase);
                    }
                }
            }

            System.out.println("Number of phrases = " + phrases.size());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    private static boolean isValid(String phrase) {
        /*
        Checks to see if any word in the phrase is longer than seven letters
        If it is, then return false. Else, true.
         */

        int wordCnt = 0;
        boolean done = false;

        while (!done) {
            int spaceIndex = phrase.indexOf(' ');
            String currWord = "";

            if (spaceIndex != -1) {
                currWord = phrase.substring(0, spaceIndex);
                phrase = phrase.substring(spaceIndex + 1, phrase.length());
            } else {
                currWord = phrase;
                done = true;
            }

            wordCnt++;

            if (currWord.length() > 7)
                return false;
        }

        //return wordCnt <= 4;
        return true;
    }

    public static Phrase getRandomPhrase() {
        Random rand = new Random();
        int index = rand.nextInt((phrases.size()-1) + 1);

        return phrases.remove(index);
    }
}
