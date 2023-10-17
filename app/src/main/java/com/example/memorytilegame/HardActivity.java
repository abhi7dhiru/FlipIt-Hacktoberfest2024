package com.example.memorytilegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Stack;

public class HardActivity extends AppCompatActivity {
    ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12, img13, img14, img15, img16, img17, img18, img19, img20, img21, img22, img23, img24, img25, img26, img27, img28, img29, img30, newView, selImg1, selImg2;
    Button checkBtn, restart;
    Stack <Integer> stkTag = new Stack<Integer>();
    Stack <ImageView> imgStack = new Stack<ImageView>();
    TextView textView;
    int top = -1;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);
        img7 = findViewById(R.id.img7);
        img8 = findViewById(R.id.img8);
        img9 = findViewById(R.id.img9);
        img10 = findViewById(R.id.img10);
        img11 = findViewById(R.id.img11);
        img12 = findViewById(R.id.img12);
        img13 = findViewById(R.id.img13);
        img14 = findViewById(R.id.img14);
        img15 = findViewById(R.id.img15);
        img16 = findViewById(R.id.img16);
        img17 = findViewById(R.id.img17);
        img18 = findViewById(R.id.img18);
        img19 = findViewById(R.id.img19);
        img20 = findViewById(R.id.img20);
        img21 = findViewById(R.id.img21);
        img22 = findViewById(R.id.img22);
        img23 = findViewById(R.id.img23);
        img24 = findViewById(R.id.img24);
        img25 = findViewById(R.id.img25);
        img26 = findViewById(R.id.img26);
        img27 = findViewById(R.id.img27);
        img28 = findViewById(R.id.img28);
        img29 = findViewById(R.id.img29);
        img30 = findViewById(R.id.img30);
        textView = findViewById(R.id.textView);

    }

    public void Push(View view) {
        top++;
        if(top < 2) {
            if(top == 0) textView.setText("Choose another");

            newView = (ImageView) view;
            int tag = Integer.parseInt((String) newView.getTag());
            stkTag.push(tag);

            imgStack.push(newView);
            view.setEnabled(false);

//            Toast.makeText(this, "Tag: " + tag, Toast.LENGTH_SHORT).show();

            switch (tag) {
                case 1: newView.setImageResource(R.drawable.i1);
                    break;
                case 2: newView.setImageResource(R.drawable.i2);
                    break;
                case 3: newView.setImageResource(R.drawable.i3);
                    break;
                case 4: newView.setImageResource(R.drawable.i4);
                    break;
                case 5: newView.setImageResource(R.drawable.i5);
                    break;
                case 6: newView.setImageResource(R.drawable.i6);
                    break;
                case 8: newView.setImageResource(R.drawable.i7);
                    break;
                case 10: newView.setImageResource(R.drawable.i8);
                    break;
                case 11: newView.setImageResource(R.drawable.i9);
                    break;
                case 13: newView.setImageResource(R.drawable.i10);
                    break;
                case 14: newView.setImageResource(R.drawable.i11);
                    break;
                case 15: newView.setImageResource(R.drawable.i12);
                    break;
                case 16: newView.setImageResource(R.drawable.i13);
                    break;
                case 19: newView.setImageResource(R.drawable.i14);
                    break;
                case 20: newView.setImageResource(R.drawable.i15);
                    break;

            }

            if(top == 1) {
                textView.setText("Click check");
                checkBtn = findViewById(R.id.checkBtn);
                checkBtn.setVisibility(VISIBLE);
            }
        }
        else {
            textView.setText("Check selected items");
        }
    }

    public void check(View view) {
        checkBtn.setVisibility(INVISIBLE);
        int tag1 = stkTag.pop();
        int tag2 = stkTag.pop();
        top = -1;
        if(tag1 == tag2){
            textView.setText("Matched!");
            count++;
            selImg1 = imgStack.pop();
            selImg2 = imgStack.pop();
            selImg1.setVisibility(INVISIBLE);
            selImg2.setVisibility(INVISIBLE);
            if(count == 15) {
                textView.setText("You won this game\nClick Restart to play again");
                GridLayout grid = findViewById(R.id.grid);
                grid.setVisibility(View.GONE);
                restart = findViewById(R.id.restart);
                restart.setVisibility(VISIBLE);

                restart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });
            }
        }
        else {
            textView.setText("Oops, Try again.");
            selImg1 = imgStack.pop();
            selImg2 = imgStack.pop();
            selImg1.setImageResource(R.drawable.box);
            selImg1.setEnabled(true);
            selImg2.setImageResource(R.drawable.box);
            selImg2.setEnabled(true);
        }

//        else Toast.makeText(this, "Select a pair", Toast.LENGTH_SHORT).show();
    }
}