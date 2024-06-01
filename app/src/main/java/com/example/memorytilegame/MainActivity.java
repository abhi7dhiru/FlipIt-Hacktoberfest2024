package com.example.memorytilegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.memorytilegame.levels.LevelsScreen;

public class MainActivity extends AppCompatActivity {
    Button play, tutorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        tutorial = findViewById(R.id.tutorial);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iPlay = new Intent(MainActivity.this, LevelsScreen.class);
                startActivity(iPlay);
            }
        });
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iTut = new Intent(MainActivity.this, TutorialScreen.class);
                startActivity(iTut);
            }
        });

    }
}