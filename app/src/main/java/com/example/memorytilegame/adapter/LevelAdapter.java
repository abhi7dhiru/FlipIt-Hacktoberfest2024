package com.example.memorytilegame.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memorytilegame.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
    Context context;
    ArrayList<Integer> animalList;
    RecyclerView recyclerView;
    ImageView winGame;
    Stack <Integer> imgStack = new Stack<Integer>();
    Stack <ImageView> imgOfBlock = new Stack<ImageView>();
    int top = 0, count = 0;
    public LevelAdapter(Context context, ArrayList<Integer> animalList, RecyclerView recyclerView, ImageView winGame) {
        this.context = context;
        this.animalList = animalList;
        this.recyclerView = recyclerView;
        this.winGame = winGame;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.box_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imgBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(view);

                top++;
                if(top < 3){
                    holder.imgBlock.setImageResource(animalList.get(holder.getAdapterPosition()));
                    imgStack.push(animalList.get(holder.getAdapterPosition()));
                    imgOfBlock.push(holder.imgBlock);
                }
                if(top == 2){
                    // Will Now get the drawable and imageview to check the similarity;
                    int img1 = imgStack.pop();
                    int img2 = imgStack.pop();
                    ImageView imageView2 = imgOfBlock.pop();
                    ImageView imageView1 = imgOfBlock.pop();

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
        recyclerView.setVisibility(View.GONE);
        winGame.setVisibility(View.VISIBLE);
        Glide.with(context)
                .asGif()
                .load(R.raw.game_over)
                .into(winGame);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBlock;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBlock = itemView.findViewById(R.id.imgBlock);

        }
    }
}

