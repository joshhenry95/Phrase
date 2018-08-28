package joshuahenry.phrase;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimeAttackActivity extends AppCompatActivity {

    private final int WRONG_GUESS_SCORE = 10;
    private final int CORRECT_GUESS_SCORE = 20;
    private List<String> guessedLetters;
    private boolean gameOver;
    private int numPhrasesSolved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack);

        numPhrasesSolved = 0;
        gameOver = false;
        guessedLetters = new ArrayList<String>();

        Player.score = 0;

        final String[][] gameBoardLetters = new String[4][7];

        // Labels
        final TextView timeLabel = (TextView) findViewById(R.id.time_label);
        final TextView scoreLabel = (TextView) findViewById(R.id.score_label);
        final TextView feedbackLabel = (TextView) findViewById(R.id.feedback_label);
        final TextView categoryLabel = (TextView) findViewById(R.id.category_label);

        // EditText
        final EditText enterLetter = (EditText) findViewById(R.id.enter_letter);

        // A lot of buttons...
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        final Button button4 = (Button) findViewById(R.id.button4);
        final Button button5 = (Button) findViewById(R.id.button5);
        final Button button6 = (Button) findViewById(R.id.button6);
        final Button button7 = (Button) findViewById(R.id.button7);
        final Button button8 = (Button) findViewById(R.id.button8);
        final Button button9 = (Button) findViewById(R.id.button9);
        final Button button10 = (Button) findViewById(R.id.button10);
        final Button button11 = (Button) findViewById(R.id.button11);
        final Button button12 = (Button) findViewById(R.id.button12);
        final Button button13 = (Button) findViewById(R.id.button13);
        final Button button14 = (Button) findViewById(R.id.button14);
        final Button button15 = (Button) findViewById(R.id.button15);
        final Button button16 = (Button) findViewById(R.id.button16);
        final Button button17 = (Button) findViewById(R.id.button17);
        final Button button18 = (Button) findViewById(R.id.button18);
        final Button button19 = (Button) findViewById(R.id.button19);
        final Button button20 = (Button) findViewById(R.id.button20);
        final Button button21 = (Button) findViewById(R.id.button21);
        final Button button22 = (Button) findViewById(R.id.button22);
        final Button button23 = (Button) findViewById(R.id.button23);
        final Button button24 = (Button) findViewById(R.id.button24);
        final Button button25 = (Button) findViewById(R.id.button25);
        final Button button26 = (Button) findViewById(R.id.button26);
        final Button button27 = (Button) findViewById(R.id.button27);
        final Button button28 = (Button) findViewById(R.id.button28);

        // Enter guess button
        final Button enterGuess = (Button) findViewById(R.id.enter_button);

        final Button[][] gameBoardButtons = {{button1, button2, button3, button4, button5, button6, button7},
                {button8, button9, button10, button11, button12, button13, button14}, {button15, button16,
                button17, button18, button19, button20, button21}, {button22, button23, button24, button25, button26, button27, button28}};

        timeLabel.setText("Time: 300");
        scoreLabel.setText("Score: 0");
        feedbackLabel.setText("");
        enterLetter.setText("");


        new CountDownTimer(180000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // Convert the time to X:XX rather than XXX seconds

                int mins = (int) millisUntilFinished / (60 * 1000);
                int seconds = (int) millisUntilFinished % (60 * 1000) / 1000;

                if (mins == 0) {
                    // Set the timeLabel's color to red
                    timeLabel.setTextColor(Color.RED);
                }

                if (seconds / 10 == 0) {
                    timeLabel.setText("Time: " + mins + ":0" + seconds);
                } else {
                    timeLabel.setText("Time: " + mins + ":" + seconds);
                }


            }

            @Override
            public void onFinish() {
                timeLabel.setText("Time: 0:00");
                feedbackLabel.setText("Times up! You have solved " + numPhrasesSolved + " phrases and scored " + Player.score + " points!");
                gameOver = true;

                Player.setTimeAttackHighScore();
                Player.setTimeAttackNumPhrasesHighScore(numPhrasesSolved);

                Utility.reset();
                numPhrasesSolved = 0;
            }
        }.start();


        Utility.createGameBoard(gameBoardButtons, gameBoardLetters, categoryLabel, feedbackLabel);

        enterLetter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                enterLetter.setText("");
            }
        });

        enterGuess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String guess = enterLetter.getText().toString();
                enterLetter.setText("");


                Utility.hideInputKeyboard(TimeAttackActivity.this, v);

                // If the length of guess is > 1, then treat it like
                // a guess to solve the puzzle. If it's == 1 then
                // treat it like they're trying to guess a letter
                // to show on the board.
                if (!gameOver) {
                    if (guess.length() > 1) {
                        String feedbackStr = "";

                        if (!guess.equalsIgnoreCase(Phrases.activePhrase.getPhraseStr())) {
                            feedbackStr = "Sorry, but " + "\'" + guess + "\' is not correct!";
                            Player.subtractFromScore(WRONG_GUESS_SCORE);
                        } else {
                            numPhrasesSolved++;

                            feedbackStr = "\'" + guess.toUpperCase() + "\' is correct!";
                            Player.addToScore((CORRECT_GUESS_SCORE));

                            // Unhide all of the letters on the board:
                            Utility.displayLetters(gameBoardButtons, gameBoardLetters, categoryLabel, true);

                            // Wait four seconds before starting a new round or displaying end game:
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    // Run createGameBoard after 4 seconds:

                                    Utility.createGameBoard(gameBoardButtons, gameBoardLetters, categoryLabel, feedbackLabel);

                                }
                            }, 4000);
                        }

                        scoreLabel.setText("Score: " + Player.score);
                        feedbackLabel.setText(feedbackStr);
                    } else {  // Else if the string entered has one character, see if it's in the phrase:
                        Utility.guessLetter(gameBoardButtons, gameBoardLetters, feedbackLabel, guess);
                        scoreLabel.setText("Score: " + Player.score);
                    }
                }
            }
        });

    }
}
