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
    }

    public void startGame(View view) {
        Intent intent = new Intent(LevelsScreen.this, GameActivity.class);
        int cols, index;
        if(view.equals(easy)) {
            cols = 3;
            index = 12;
        }
        else if(view.equals(medium)){
            cols = 4;
            index = 20;
        }
        else{
            cols = 5;
            index = 30;
        }
        intent.putExtra("noOfColumns", cols);
        intent.putExtra("index", index);
        startActivity(intent);
    }
}