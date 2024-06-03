package com.example.memorytilegame.levels;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.memorytilegame.MainActivity;
import com.example.memorytilegame.R;
import com.example.memorytilegame.adapter.LevelAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {
    ArrayList<Integer> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    ImageView winGame, goBackFromGame, pause;
    TextView countdown, timerTextView;
    View blackOverlay, touchInterceptor;
    Handler handler;
    Runnable timerRunnable;
    long startTime, elapsedTime;
    boolean isCountdownFinished = false;
    Button resumeTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        recyclerView = findViewById(R.id.recyclerView);
        winGame = findViewById(R.id.winGame);
        countdown = findViewById(R.id.countdown);
        blackOverlay = findViewById(R.id.blackOverlay);
        touchInterceptor = findViewById(R.id.touchInterceptor);
        timerTextView = findViewById(R.id.timer);
        goBackFromGame = findViewById(R.id.goBackFromGame);
        pause = findViewById(R.id.pauseTimer);
        resumeTimer = findViewById(R.id.resumeTimer);
        handler = new Handler();

        Intent intent = getIntent();
        int noOfCols = intent.getIntExtra("noOfColumns", 0);
        int index = intent.getIntExtra("index", 0);

        startCountdown();

        Collections.addAll(arrayList,
                R.drawable.i1, R.drawable.i1,
                R.drawable.i2, R.drawable.i2,
                R.drawable.i3, R.drawable.i3,
                R.drawable.i4, R.drawable.i4,
                R.drawable.i5, R.drawable.i5,
                R.drawable.i6, R.drawable.i6,
                R.drawable.i7, R.drawable.i7,
                R.drawable.i8, R.drawable.i8,
                R.drawable.i9, R.drawable.i9,
                R.drawable.i10, R.drawable.i10,
                R.drawable.i11, R.drawable.i11,
                R.drawable.i12, R.drawable.i12,
                R.drawable.i13, R.drawable.i13,
                R.drawable.i14, R.drawable.i14,
                R.drawable.i15, R.drawable.i15);

        LevelAdapter adapter = new LevelAdapter(this, arrayList.subList(0, index), recyclerView, winGame, noOfCols, timerTextView, pause);
        recyclerView.setLayoutManager(new GridLayoutManager(this, noOfCols));
        recyclerView.setAdapter(adapter);

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long currentElapsedMillis = System.currentTimeMillis() - startTime + elapsedTime;
                int minutes = (int) (currentElapsedMillis / 60000);
                int seconds = (int) (currentElapsedMillis / 1000) % 60;
                int milliseconds = (int) (currentElapsedMillis % 1000);

                String timeString = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
                timerTextView.setText(timeString);
                handler.postDelayed(this, 1);
            }
        };

        goBackFromGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerTextView.getVisibility() == View.GONE) {
                    startActivity(new Intent(GameActivity.this, LevelsScreen.class));
                    finish();
                }
                else pauseGame();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blackOverlay.setVisibility(View.VISIBLE);
                touchInterceptor.setVisibility(View.VISIBLE);
                countdown.setVisibility(View.VISIBLE);
                countdown.setText("Paused!");

                // Save the elapsed time
                elapsedTime += System.currentTimeMillis() - startTime;
                stopTimer();

                resumeTimer.setVisibility(View.VISIBLE);
                resumeTimer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startAnimation(view);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                blackOverlay.setVisibility(View.GONE);
                                touchInterceptor.setVisibility(View.GONE);
                                countdown.setVisibility(View.GONE);
                                startTimer(); // Start the timer without resetting elapsedTime

                                resumeTimer.setVisibility(View.GONE);
                            }
                        }, 100);

                    }
                });
            }
        });
    }

    private void startCountdown() {
        countdown.setVisibility(View.VISIBLE);
        blackOverlay.setVisibility(View.VISIBLE);
        touchInterceptor.setVisibility(View.VISIBLE);

        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                if (secondsRemaining > 0) {
                    countdown.setText(String.valueOf(secondsRemaining));
                } else {
                    countdown.setText("Start!");
                }
            }

            public void onFinish() {
                countdown.setVisibility(View.GONE);
                blackOverlay.setVisibility(View.GONE);
                touchInterceptor.setVisibility(View.GONE);
                isCountdownFinished = true;
                startTimer();
            }
        }.start();
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        handler.post(timerRunnable);
    }

    private void stopTimer() {
        handler.removeCallbacks(timerRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isCountdownFinished) {
            elapsedTime += System.currentTimeMillis() - startTime;
            stopTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCountdownFinished) {
            startTimer(); // Start the timer without resetting elapsedTime
        }
    }

    public void pauseGame() {

        elapsedTime += System.currentTimeMillis() - startTime;
        stopTimer();

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this)
                .setTitle("Go Back?")
                .setMessage("All your progress will be lost")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(GameActivity.this, LevelsScreen.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Dismiss the dialog and resume the timer
                        dialogInterface.dismiss();
                        startTimer(); // Continue the timer from where it left off
                    }
                });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if(timerTextView.getVisibility() == View.GONE) {
            startActivity(new Intent(GameActivity.this, LevelsScreen.class));
            finish();
        }
        else if(resumeTimer.getVisibility() == View.GONE) pauseGame();
        else finish();
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
