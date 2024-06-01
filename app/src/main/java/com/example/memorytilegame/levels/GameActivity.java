package com.example.memorytilegame.levels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.memorytilegame.R;
import com.example.memorytilegame.adapter.LevelAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    ArrayList<Integer> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    ImageView winGame;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        recyclerView = findViewById(R.id.recyclerView);
        winGame = findViewById(R.id.winGame);

        Intent intent = getIntent();
        int noOfCols = intent.getIntExtra("noOfColumns", 0);
        int index = intent.getIntExtra("index", 0);

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

        LevelAdapter adapter = new LevelAdapter(this, arrayList.subList(0, index), recyclerView, winGame, noOfCols);
        recyclerView.setLayoutManager(new GridLayoutManager(this, noOfCols));
        recyclerView.setAdapter(adapter);
    }


}