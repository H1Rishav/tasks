package com.example.task1_1;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    Button start;
    EditText pl1_name,pl2_name,gridSize;

    Animation scaleup,scaledown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       // overridePendingTransition(R.anim.left, R.anim.out_right);
        start=findViewById(R.id.startbutton);
        pl1_name=findViewById(R.id.player1_name);
        pl2_name=findViewById(R.id.player2_name);
        gridSize=findViewById(R.id.grid);

        scaleup= AnimationUtils.loadAnimation(this,R.anim.up);
        scaledown= AnimationUtils.loadAnimation(this,R.anim.down);



        start.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        start.startAnimation(scaleup);
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        start.startAnimation(scaledown);
                        break;
                }
                return true;
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check =checkIfnotEmpty(pl1_name)+checkIfnotEmpty(pl2_name)+checkIfnotEmpty(gridSize);
                if (check==3) {
                    Intent i = new Intent(getApplicationContext(), GamePageActivity.class);
                    i.putExtra("pl1_name", String.valueOf(pl1_name.getText()).toUpperCase());
                    i.putExtra("pl2_name", String.valueOf(pl2_name.getText()).toUpperCase());
                    i.putExtra("gridSize", String.valueOf(gridSize.getText()));
                    start.startAnimation(scaleup);
                    startActivity(i);
                }
                else
                    Toast.makeText(DetailActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
               // setContentView(R.layout.activity_game_page);
            }
        });
//which tile has been clicked
        //

    }

     int checkIfnotEmpty(EditText t) {
        if(String.valueOf(t.getText()).equals(""))
            return 0;
        else
            return 1;
    }
}