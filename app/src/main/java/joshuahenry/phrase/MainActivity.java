package joshuahenry.phrase;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /* Declare our buttons and labels: */
        final Button startButton = (Button) findViewById(R.id.start_button);
        final Button highScoreButton = (Button) findViewById(R.id.high_score_button);
        final TextView normalGameHighScoreLabel = (TextView) findViewById(R.id.normal_game_high_score_label);
        final TextView timeAttackHighScoreLabel = (TextView) findViewById(R.id.time_attack_high_score_label);

        // Set normalGameHighScoreLabel and timeAttackHighScoreLabel's visibility to invisible:
        normalGameHighScoreLabel.setVisibility(View.INVISIBLE);
        timeAttackHighScoreLabel.setVisibility(View.INVISIBLE);

        // Open the txt file containing the phrases:
        AssetManager assetManager = getAssets();

        // Exit if we can't open the file; can't play without it!
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(assetManager.open("phrase1.txt")));

            // Create the list of Phrases
            Phrases.createPhraseList(reader);
        } catch (IOException e) {
            e.printStackTrace();
            finish();
            System.exit(0);
        }

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectGameTypeActivity.class));
            }
        });

        highScoreButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // We use the internal storage for keeping the high scores.
                // First line - normal game high score
                // Second line - TimeAttack high score and number of phrases completed (comma separated)



            }
        });
    }
}
