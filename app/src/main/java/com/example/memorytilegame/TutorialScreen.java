package com.example.memorytilegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.memorytilegame.levels.LevelsScreen;

public class TutorialScreen extends AppCompatActivity {

    ImageView goBackFromTut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_screen);

        goBackFromTut = findViewById(R.id.goBackFromTut);

        goBackFromTut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TutorialScreen.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TutorialScreen.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}