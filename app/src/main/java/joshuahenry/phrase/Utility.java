package joshuahenry.phrase;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Utility {

    static List<String> guessedLetters = new ArrayList<String>();
    private static final int WRONG_LETTER_SCORE = 5;
    private static final int CORRECT_LETTER_SCORE = 3;
    public static int phrasesCnt = 0;

    public static void hideInputKeyboard(Activity activity, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void guessLetter(Button[][] gameBoardButtons, String[][] gameBoardLetters, TextView feedbackLabel, String guess) {

        // Go through the letters in the game board.
        // If the guessed letter is in there, display them on the board

        int numGuessedLettersFound = 0;

        if (guessedLetters.contains(guess.toUpperCase())) {
            if (guessedLetters.size() == 1) {
                feedbackLabel.setText("We've already given you the letter " + guess.toUpperCase() + "!");
            } else {
                feedbackLabel.setText("You've already guessed the letter " + guess.toUpperCase() + "!");
            }

            Player.subtractFromScore(WRONG_LETTER_SCORE);
        } else {

            if (guess.length() > 0) {
                for (int i = 0; i < gameBoardLetters.length; i++) {
                    for (int j = 0; j < gameBoardLetters[i].length; j++) {
                        if (gameBoardLetters[i][j] != null) {
                            if (gameBoardLetters[i][j].equalsIgnoreCase(guess)) {
                                numGuessedLettersFound++;
                                gameBoardButtons[i][j].setText(guess);
                            }
                        }
                    }
                }

                if (numGuessedLettersFound > 0) {
                    feedbackLabel.setText("There are " + numGuessedLettersFound + " " + guess.toUpperCase() + "'s in the phrase!");
                    Player.addToScore(numGuessedLettersFound * CORRECT_LETTER_SCORE);
                } else {
                    feedbackLabel.setText("Sorry, but there are no " + guess.toUpperCase() + "'s in the phrase!");
                    Player.subtractFromScore(WRONG_LETTER_SCORE);
                }
            } else {
                feedbackLabel.setText("You must enter a letter or try to solve the phrase!");
            }

        }

        guessedLetters.add(guess.toUpperCase());

    }

    public static void giveFirstLetter(Button[][] gameBoardButtons, String[][] gameBoardLetters, TextView feedbackLabel) {
        /*
        Finds the letter the occurs the least and displays it for the player.
         */

        HashMap<String, Integer> letters = new HashMap<String, Integer>();
        int min = 99;
        String minLetter = "";
        guessedLetters.clear();

        // Find the least used letter.
        for (int i = 0; i < gameBoardLetters.length; i++) {
            for (int j = 0; j < gameBoardLetters[i].length; j++) {
                String currLetter = gameBoardLetters[i][j];

                if (currLetter != null) {
                    if (letters.containsKey(currLetter)) {
                        //letters.rplace(currLetter, letters.get(currLetter) + 1);
                        int updatedCnt = letters.get(currLetter) + 1;
                        letters.remove(currLetter);
                        letters.put(currLetter, updatedCnt);
                    } else {
                        letters.put(currLetter, Integer.valueOf(1));
                    }

                    if (letters.get(currLetter) < min) {
                        min = letters.get(currLetter);
                        minLetter = currLetter;
                    }
                }
            }
        }

        // Displays the least used letter.
        for (int i = 0; i < gameBoardLetters.length; i++) {
            for (int j = 0; j < gameBoardLetters[i].length; j++) {
                if (gameBoardLetters[i][j] != null && gameBoardLetters[i][j].equals(minLetter)) {
                    gameBoardButtons[i][j].setText(minLetter);
                }
            }
        }

        guessedLetters.add(minLetter);

        feedbackLabel.setText("We've given you the letter " + minLetter + " to start you off!");
    }

    public static void createGameBoard(Button[][] gameBoardButtons, String[][] gameBoardLetters, TextView categoryLabel, TextView feedbackLabel) {
        int remainingRows = 4;              // Decrements when we have to move to another row (loop control)
        int currRowIndex = 0;               // Increments when we have to move to the next row (2D array indexing)
        int currColIndex = 0;               // Increments after we add a letter (2D array indexing)
        int remainingSquaresInCurrRow = 7;  // Decrements when we add a letter to the current row (how we know when we have to move
        boolean validPhrase = false;


        // Clear the current board if this is not the first phrase:
        if (phrasesCnt > 0) {
            for (int i = 0; i < gameBoardLetters.length; i++) {
                for (int j = 0; j < gameBoardLetters[i].length; j++) {
                    gameBoardLetters[i][j] = null;
                    gameBoardButtons[i][j].setText("");
                    gameBoardButtons[i][j].setEnabled(true);
                    gameBoardButtons[i][j].setBackgroundColor(Color.LTGRAY);
                }
            }
        }

        // Populate the board with a valid phrase (one that fits on the board!)
        while (!validPhrase) {
            Phrase currPhrase = Phrases.getRandomPhrase();
            Phrases.activePhrase = currPhrase;
            currRowIndex = 0;  // can be <= 3 (since 4 lines on the board)
            currColIndex = 0;  // can be <= 6 (since 7 chars per line)

            if (currPhrase == null) {
                feedbackLabel.setText("We've run out of phrases!");
                reset();
            } else {

                String[] wordArr = currPhrase.getPhraseStr().split(" ");

                ArrayList<String> wordArrList = new ArrayList<String>(Arrays.asList(wordArr)); // All strings in wordArrList have length <= 7


                while (currRowIndex <= 3 && !validPhrase) {
                    String currWord = wordArrList.get(0);

                    if (currColIndex + (currWord.length() - 1) > 6) { // bad: go to next line
                        currRowIndex++;
                        currColIndex = 0;
                    } else {
                        wordArrList.remove(0);

                        for (int i = 0; i < currWord.length(); i++) {
                            String currLetter = currWord.substring(i, i + 1);

                            gameBoardLetters[currRowIndex][currColIndex] = currLetter;

                            currColIndex++;
                        }

                        currColIndex++; // Inc currColIndex because otherwise, we wouldn't have a space in between words

                        if (wordArrList.size() == 0) {
                            validPhrase = true;
                        }

                    }
                }
            }
        }

        categoryLabel.setText("Category: " + Phrases.activePhrase.getCategory());
        phrasesCnt++;

        displayLetters(gameBoardButtons, gameBoardLetters, categoryLabel, false);
        giveFirstLetter(gameBoardButtons, gameBoardLetters, feedbackLabel);
    }

    public static void displayLetters(Button[][] gameBoardButtons, String[][] gameBoardLetters, TextView categoryLabel, boolean showAll) {

        for (int i = 0; i < gameBoardLetters.length; i++) {
            for (int j = 0; j < gameBoardLetters[i].length; j++) {

                if (gameBoardLetters[i][j] != null) {
                    gameBoardButtons[i][j].setBackgroundColor(Color.WHITE);

                    if (gameBoardLetters[i][j].equals(",")) {
                        gameBoardButtons[i][j].setText(",");
                    }

                    if (gameBoardLetters[i][j].equals("'")) {
                        gameBoardButtons[i][j].setText("'");
                    }

                    if (showAll) {
                        gameBoardButtons[i][j].setText(gameBoardLetters[i][j]);
                    }
                }
            }
        }

    }

    public static void reset() {
        phrasesCnt = 0;
        guessedLetters.clear();
        Player.score = 0;
    }
}
