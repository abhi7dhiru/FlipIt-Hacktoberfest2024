package com.example.memorytilegame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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
                startAnimation(view);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent iPlay = new Intent(MainActivity.this, LevelsScreen.class);
                        startActivity(iPlay);
                        finish();
                    }
                }, 100);

            }
        });
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(view);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent iTut = new Intent(MainActivity.this, TutorialScreen.class);
                        startActivity(iTut);
                        finish();
                    }
                }, 100);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Leave the app")
                .setMessage("Are you sure about this?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    finish();
                })
                .setNegativeButton("No", (dialogInterface, i) -> {

                });
        builder.show();
    }

    public void startAnimation(View v){
        v.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(50)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(50)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                }).start();
    }
}