package com.example.memorytilegame;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    //Logic: Stack maintain karege ek. Ek pe click kiya to usko stack me feke, dusre pe click kiya to usko bhi stack me feke. Har button pe ke tag option hai, jo similar ho sakta hai. Do ko similar rakhege, 1st click pe onclick call hoga, top++ hoga, stack[top] = view.getTag(); Dusre button pe click kiya, onclick listen hua, stack me feka. Ab ek button lagayege, wo show hoga jab top == 1, yaani ke stack me 2 element hai. na usse kam, na zyada. Condition click pe bhi ke agar top <= 1 hai to hi chale warna nahi. Phir jaise hi top == 1 ho, do elements ko ek function me bhejege, check karne ke liye ke dono ke tags same hai ke nahi. Hoge to success, else fail. Success me dono cards gayab hojayege, fail me firse palat jayege.

    // format (1, 7), (2, 3), (4, 9), (5, 12), (6, 8), (10, 11)
ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12 , newView, selImg1, selImg2;
Button checkBtn, restart;
Stack <Integer> stkTag = new Stack<Integer>();
Stack <ImageView> imgStack = new Stack<ImageView>();
TextView textView;
int top = -1;
int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                case 1: newView.setImageResource(R.drawable.i7);
                    break;
                case 2: newView.setImageResource(R.drawable.i2);
                    break;
                case 4: newView.setImageResource(R.drawable.i3);
                    break;
                case 5: newView.setImageResource(R.drawable.i4);
                    break;
                case 6: newView.setImageResource(R.drawable.i5);
                    break;
                case 10: newView.setImageResource(R.drawable.i6);
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
                if(count == 6) {
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