package joshuahenry.phrase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectGameTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game_type);

        final Button normalGameButton = (Button) findViewById(R.id.normal_game_button);
        final Button timeAttackButton = (Button) findViewById(R.id.time_attack_button);


        normalGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectGameTypeActivity.this, NormalGameActivity.class));
            }
        });

        timeAttackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectGameTypeActivity.this, TimeAttackActivity.class));
            }
        });
    }
}
