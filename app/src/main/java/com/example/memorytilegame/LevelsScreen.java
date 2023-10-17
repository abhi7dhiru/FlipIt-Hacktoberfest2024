package com.example.memorytilegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent iEasy = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(iEasy);
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMedium = new Intent(getApplicationContext(), MedActivity.class);
                startActivity(iMedium);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iHard = new Intent(getApplicationContext(), HardActivity.class);
                startActivity(iHard);
            }
        });

    }
}