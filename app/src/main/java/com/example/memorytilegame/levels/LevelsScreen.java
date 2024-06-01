package com.example.memorytilegame.levels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.memorytilegame.R;

public class LevelsScreen extends AppCompatActivity {

Button easy, medium, hard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_screen);

        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEasy = new Intent(LevelsScreen.this, EasyActivity.class);
                startActivity(iEasy);
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMedium = new Intent(LevelsScreen.this, MedActivity.class);
                startActivity(iMedium);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iHard = new Intent(LevelsScreen.this, HardActivity.class);
                startActivity(iHard);
            }
        });

    }
}