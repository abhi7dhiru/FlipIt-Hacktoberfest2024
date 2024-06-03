package com.example.memorytilegame.adapter;

import static android.view.View.GONE;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memorytilegame.R;
import com.example.memorytilegame.SharedPreferencesHelper;
import com.example.memorytilegame.levels.GameActivity;
import com.example.memorytilegame.levels.LevelsScreen;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
    Context context;
    List<Integer> animalList;
    RecyclerView recyclerView;
    ImageView winGame, pauseGame;
    TextView timerTextView;
    Stack <Integer> imgStack = new Stack<Integer>();
    Stack <ImageView> imgOfBlock = new Stack<ImageView>();
    private SharedPreferencesHelper sharedPreferencesHelper;
    int noOfCols;
    int top = 0, count = 0;
    public LevelAdapter(Context context, List<Integer> animalList, RecyclerView recyclerView, ImageView winGame, int noOfCols, TextView timerTextView, ImageView pauseGame) {
        this.context = context;
        this.animalList = animalList;
        this.recyclerView = recyclerView;
        this.winGame = winGame;
        this.pauseGame = pauseGame;
        this.noOfCols = noOfCols;
        this.timerTextView = timerTextView;
        Collections.shuffle(animalList);
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.box_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
        if(noOfCols == 5){
            layoutParams.height = 164;
            layoutParams.width = 164;
        }
        else if(noOfCols == 4){
            layoutParams.height = 200;
            layoutParams.width = 200;
        }
        else{
            layoutParams.height = 250;
            layoutParams.width = 250;
        }

        holder.cardView.setLayoutParams(layoutParams);

        holder.imgBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(view);

                top++;
                if(top < 3){
                    holder.imgBlock.setImageResource(animalList.get(holder.getAdapterPosition()));
                    holder.imgBlock.setEnabled(false);
                    imgStack.push(animalList.get(holder.getAdapterPosition()));
                    imgOfBlock.push(holder.imgBlock);
                }
                if(top == 2){
                    // Will Now get the drawable and imageview to check the similarity;
                    int img1 = imgStack.pop();
                    int img2 = imgStack.pop();
                    ImageView imageView2 = imgOfBlock.pop();
                    ImageView imageView1 = imgOfBlock.pop();
                    imageView1.setEnabled(true);
                    imageView2.setEnabled(true);

                    // If the drawables popped are equal, set invisible the imageview and hence increment the count;
                    if(img1 == img2) {
                        count++;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imageView2.setVisibility(View.INVISIBLE);
                                    imageView1.setVisibility(View.INVISIBLE);
                                    top = 0;

                                    if(count == getItemCount() / 2){
                                        Log.d("jeet gaya tu", ":wi hogaye ");
                                        winGame();
                                    }
                                }
                            }, 1000);
                        }
                    }

                    // If drawables are not equal set their images back to the box they were;
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imageView2.setImageResource(R.drawable.box);
                                    imageView1.setImageResource(R.drawable.box);
                                    top = 0;
                                }
                            }, 1000);
                        }
                    }
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    // On touching the block it should shrink and get back to original form;
    public void startAnimation(View v){
        v.animate()
                .scaleX(0.85f)
                .scaleY(0.85f)
                .setDuration(50)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                }).start();
    }

    public void winGame(){
        recyclerView.setVisibility(GONE);
        winGame.setVisibility(View.VISIBLE);
        pauseGame.setVisibility(GONE);
        Glide.with(context)
                .asGif()
                .load(R.raw.game_over)
                .into(winGame);
        String score = timerTextView.getText().toString();
        timerTextView.setVisibility(GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                winGame.setVisibility(GONE);
                showScoreDialog(score);
            }
        }, 2000);
    }

    public void showScoreDialog(String score) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_score);
        dialog.show();

        Button btnDone = dialog.findViewById(R.id.btnDone);
        TextView yourScore = dialog.findViewById(R.id.yourScore);
        TextView highestScore = dialog.findViewById(R.id.highestScore);
        TextView newHighScore = dialog.findViewById(R.id.newHighScore);

        yourScore.setText("Your score " + score);

        String getHighscore = getHighScoreAccToLevel(noOfCols);

        if (getHighscore == null) {
            highestScore.setText("Highest Score " + score);
            newHighScore.setVisibility(View.VISIBLE);

            saveHighestScore(noOfCols, score);
        }
        else {
            if(convertToTimestamp(score) < convertToTimestamp(getHighscore)){
                newHighScore.setVisibility(View.VISIBLE);
                highestScore.setText("Highest Score " + score);

                saveHighestScore(noOfCols, score);
                //sharedPreferencesHelper.saveString(score);
            }
            else{
                highestScore.setText("Highest Score " + getHighscore);
                newHighScore.setVisibility(GONE);
            }
        }
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                context.startActivity(new Intent(context, LevelsScreen.class));
                if (context instanceof GameActivity) {
                    ((GameActivity) context).finish();
                }
            }
        });
    }

    public long convertToTimestamp(String timeString) {
        // Split the string by the colon (":") delimiter
        String[] timeComponents = timeString.split(":");

        // Parse minutes, seconds, and milliseconds
        int minutes = Integer.parseInt(timeComponents[0]);
        int seconds = Integer.parseInt(timeComponents[1]);
        int milliseconds = Integer.parseInt(timeComponents[2]);

        // Convert each component to milliseconds
        long totalMilliseconds = (minutes * 60 * 1000) + (seconds * 1000) + milliseconds;

        return totalMilliseconds;
    }

    public String getHighScoreAccToLevel(int noOfCols) {
        if(noOfCols == 3) return sharedPreferencesHelper.getHighestScoreLevel1();
        if(noOfCols == 4) return sharedPreferencesHelper.getHighestScoreLevel2();
        if(noOfCols == 5) return sharedPreferencesHelper.getHighestScoreLevel3();
        else return null;
    }

    public void saveHighestScore(int noOfCols, String score) {
        if(noOfCols == 3) sharedPreferencesHelper.saveHighestScoreLevel1(score);
        if(noOfCols == 4) sharedPreferencesHelper.saveHighestScoreLevel2(score);
        if(noOfCols == 5) sharedPreferencesHelper.saveHighestScoreLevel3(score);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBlock;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBlock = itemView.findViewById(R.id.imgBlock);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}

