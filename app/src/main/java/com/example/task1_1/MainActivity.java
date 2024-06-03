package com.example.task1_1;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button play_button;
    TextView help_text,clr_cnq1,pl1,pl2;
    SharedPreferences pref;

    Animation scaleup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        play_button=findViewById(R.id.playbutton);
        help_text=findViewById(R.id.help);
        clr_cnq1=findViewById(R.id.clr_cnq);
        pl1=findViewById(R.id.player1_wins);
        pl2=findViewById(R.id.player2_wins);


        scaleup= AnimationUtils.loadAnimation(this,R.anim.up);

        pref=getSharedPreferences("wins",MODE_PRIVATE);
//        SharedPreferences.Editor ed= pref.edit();
//        ed.clear();
//        ed.commit();
        pl1.setText("Player 1 WINS: "+String.valueOf(pref.getInt("Playerone",0)));
        pl2.setText("Player 2 WINS: "+String.valueOf(pref.getInt("Playertwo",0)));



        SpannableString string=new SpannableString(clr_cnq1.getText());
        string.setSpan(new ForegroundColorSpan(Color.BLACK),0,5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        int clr=Color.parseColor("#88000000");//to set faded black
        string.setSpan(new ForegroundColorSpan(clr),6,clr_cnq1.getText().length(), SPAN_INCLUSIVE_INCLUSIVE);
        clr_cnq1.setText(string);
        //add animations while clicking
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                play_button.setAnimation(scaleup);
                Intent i=new Intent(getApplicationContext(),DetailActivity.class);

                startActivity(i);
                //overridePendingTransition(R.anim.right, R.anim.out_left);



            }
        });

        help_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d=new Dialog(MainActivity.this,R.style.CustomDialog);
                d.setContentView(R.layout.help_dialog);

                d.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                d.setCancelable(true);
                d.show();


                //d.setContentView(R.layout.help_dialog);

            }
        });
//        int screenWidth=getResources().getDisplayMetrics().widthPixels;
//        int screenHeight=getResources().getDisplayMetrics().heightPixels;
//        Toast.makeText(this,String.valueOf(screenHeight+" "+screenWidth),Toast.LENGTH_SHORT).show();
    }
}