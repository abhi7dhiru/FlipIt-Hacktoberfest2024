package com.example.memorytilegame.levels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.memorytilegame.R;
import com.example.memorytilegame.adapter.LevelAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EasyActivity extends AppCompatActivity {

    ArrayList<Integer> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    ImageView winGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        recyclerView = findViewById(R.id.easyRV);
        winGame = findViewById(R.id.winGame);

        Collections.addAll(arrayList,
                R.drawable.i7, R.drawable.i7,
                R.drawable.i2, R.drawable.i2,
                R.drawable.i3, R.drawable.i3,
                R.drawable.i4, R.drawable.i4,
                R.drawable.i5, R.drawable.i5,
                R.drawable.i6, R.drawable.i6 );
        Collections.shuffle(arrayList);

        LevelAdapter adapter = new LevelAdapter(this, arrayList, recyclerView, winGame);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

    }
}