package com.example.task1_1;

import static androidx.core.view.ViewCompat.setBackground;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GamePageActivity extends AppCompatActivity {
    GridLayout gridLayout;
    ImageView[][] imageViews;
    ImageView cross_btn;
    int size;

    ConstraintLayout constraintLayout;

    int pl1_count=0,pl2_count=0;
    TextView pl1_scor,pl1_nam,pl2_scor,pl2_nam;

    TextView winner_name,play_again,home_btn;

    TextView reset_btn,quit_btn;

    int cnt=0;

    Dialog dg1,dg2;

    String pl1_intent,pl2_intent;
    int gridSize1;
    Animation scaledown,scaleup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gamemain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        constraintLayout=findViewById(R.id.gamemain);
        gridLayout=findViewById(R.id.game_grid);
        pl1_nam=findViewById(R.id.pl1_name);
        pl1_scor=findViewById(R.id.pl1_score);
        pl2_nam=findViewById(R.id.pl2_name);
        pl2_scor=findViewById(R.id.pl2_score);
        cross_btn=findViewById(R.id.cross_button);

        dg1=new Dialog(GamePageActivity.this,R.style.CustomDialog);
        dg1.setContentView(R.layout.quit_dialog);
        dg2=new Dialog(GamePageActivity.this,R.style.CustomDialog);
        dg2.setContentView(R.layout.win_dialog);

        reset_btn=dg1.findViewById(R.id.reset_btn1);
        quit_btn=dg1.findViewById(R.id.quit_btn1);

        winner_name=dg2.findViewById(R.id.winner_name1);
        play_again=dg2.findViewById(R.id.play_again1);
        home_btn=dg2.findViewById(R.id.home_btn1);


        pl1_scor.setText(String.valueOf(pl1_count));
        pl2_scor.setText(String.valueOf(pl2_count));

        scaledown= AnimationUtils.loadAnimation(this,R.anim.down);
        scaleup=AnimationUtils.loadAnimation(this,R.anim.up);

        Intent i=getIntent();
        pl1_intent=i.getStringExtra("pl1_name");
        pl2_intent=i.getStringExtra("pl2_name");
        gridSize1=Integer.parseInt(i.getStringExtra("gridSize"));

        pl1_nam.setText(pl1_intent);
        pl2_nam.setText(pl2_intent);

        takeInput(gridSize1);
        //checKPointsandDispaly();
        onCLickcrossButton();

        checkClick(1);
    }


    void takeInput( int gridSize)
    {
        imageViews = new ImageView[gridSize][gridSize];
        gridLayout.removeAllViews();
        gridLayout.setRowCount(gridSize);
        gridLayout.setColumnCount(gridSize);

        for (int row =0;row<gridSize;row++)
        {
            for (int col=0;col<gridSize;col++)
            {

                imageViews[row][col]=new ImageView(GamePageActivity.this);
                imageViews[row][col].setImageResource(R.drawable.solid_fill_offwhite);

                size=togetBoxSize(gridSize);
                GridLayout.LayoutParams params=new GridLayout.LayoutParams();
                params.rowSpec=GridLayout.spec(row);//index,span,,weight
                params.columnSpec=GridLayout.spec(col);
                params.width=size;
                params.height=size;
                imageViews[row][col].setPadding(8,8,8,8);

               // imageViews[row][col].setLayoutParams(params);
                int id=(row+1)*1000+(col+1)*100;
                imageViews[row][col].setId(View.generateViewId());
                imageViews[row][col].setTag(id);
                gridLayout.addView(imageViews[row][col],params);

            }
        }

    }

    private int togetBoxSize(int row) {
        int screenWidth=getResources().getDisplayMetrics().widthPixels;
        int screenHeight=getResources().getDisplayMetrics().heightPixels;
        int boxwidth=screenWidth/row;
        int boxheight=screenHeight/row;
        return Math.min(boxheight,boxwidth)-10;
    }

    void checkClick(int turn)
    {
        if(turn==1)
            constraintLayout.setBackgroundColor(Color.parseColor("#3982EC"));
        else
            constraintLayout.setBackgroundColor(Color.parseColor("#C33927"));
        //1 is blue 2 is red in tag
        int childcount=gridLayout.getChildCount();
        for(int i=0;i<childcount;i++)
        {
            View v=gridLayout.getChildAt(i);
            ImageView imageView=(ImageView) v;
//            Object obj=imageView.getTag();
//            int tag=(Integer)obj;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.startAnimation(scaledown);
                    checkNumber(imageView,turn);//to check which number and which color is there

                }
            });

        }



    }
    void setNumberBlue(ImageView imageView,int tg1)
    {
        if(tg1==1) {
            imageView.setImageResource(R.drawable.blue_2);
            imageView.setPadding(8,8,8,8);
            checKPointsandDispaly();
            checkClick(2);
        }
        else if (tg1==2) {
            imageView.setImageResource(R.drawable.blue_3);
            imageView.setPadding(8,8,8,8);checkClick(2);
            checKPointsandDispaly();
            checkClick(2);
        }
        else{
            divide4Blue(imageView);
            checKPointsandDispaly();
            checkClick(2);


        }

    }
    void setNumberRed(ImageView imageView,int tg1)
    {
        if(tg1==1) {
            imageView.setImageResource(R.drawable.red_2);
            imageView.setPadding(8,8,8,8);
            checKPointsandDispaly();
            checkClick(1);
        }
        else if(tg1==2) {
            imageView.setImageResource(R.drawable.red_3);
            imageView.setPadding(8,8,8,8);
            checKPointsandDispaly();
            checkClick(1);
        }
        else{
            divide4Red(imageView);
            checKPointsandDispaly();
            checkClick(1);

        }
    }
    void checkNumber(ImageView img,int turn1)
    {
        Object obj=img.getTag();
        int tag=(Integer)obj;
        //Toast.makeText(this,String.valueOf(tag),Toast.LENGTH_SHORT).show();
        int tg=tag%10;
        if(tg==0){
            if (turn1==1) {
                img.setImageResource(R.drawable.blue_1);
                img.setPadding(8,8,8,8);
                int t = tag+10+ 1;//10 as the number has changed to 1 and 1 as the color id blue
               // Toast.makeText(this,String.valueOf(t),Toast.LENGTH_SHORT).show();
                img.setTag(t);
                checKPointsandDispaly();
                checkClick(2);
            }
            else {
                if (turn1 == 2) {
                    img.setImageResource(R.drawable.red_1);
                    img.setPadding(8, 8, 8, 8);
                    int t = tag + 10 + 2;
                  //  Toast.makeText(this, String.valueOf(t), Toast.LENGTH_SHORT).show();
                    img.setTag(t);
                    checKPointsandDispaly();
                    checkClick(1);
                }
            }
        }
        else if(tg==1&&turn1==1){

            int tg1=(tag%100)/10;
            int t1=tag+10;
            img.setTag(t1);
            setNumberBlue(img,tg1);
        }


        else if(turn1==2&&tg==2) {
                int tg1 = (tag % 100) / 10;
//            Toast.makeText(this,String.valueOf(tg1),Toast.LENGTH_SHORT).show();
                int t1 = tag + 10;
                img.setTag(t1);
//            Toast.makeText(this,String.valueOf(t1+" "+tag),Toast.LENGTH_SHORT).show();
                setNumberRed(img, tg1);
            }



    }


void divide4Red(ImageView img)
{
    Object object= img.getTag();
    int tag=(Integer)object;
    img.setTag((tag/100)*100);
    img.setImageResource(R.drawable.solid_fill_offwhite);//tag must become **00 afte division
    int r=(tag/1000)-1;int c=(tag/100)%10-1;
    int t1=(tag/10)%10;
    //to deal with above
    if(r>0) {
        tag = (Integer) imageViews[r - 1][c].getTag();
        TodealwithDivideRED(tag, imageViews[r - 1][c]);
    }

    //to deal with below
    if(r<gridSize1-1) {
        tag = (Integer) imageViews[r + 1][c].getTag();
        TodealwithDivideRED(tag, imageViews[r + 1][c]);
    }

    //to deal with left
    if(c>0) {
        tag = (Integer) imageViews[r][c - 1].getTag();
        TodealwithDivideRED(tag, imageViews[r][c - 1]);
    }

    //to deal with right
    if(c<gridSize1-1) {
        tag = (Integer) imageViews[r][c + 1].getTag();
        TodealwithDivideRED(tag, imageViews[r][c + 1]);
    }

    img.setImageResource(R.drawable.solid_fill_offwhite);

}
    void divide4Blue(ImageView img)
    {
        Object object= img.getTag();
        int tag=(Integer)object;
        img.setTag((tag/100)*100);//tag must become **00 afte division
        img.setImageResource(R.drawable.solid_fill_offwhite);
        int r=(tag/1000)-1;int c=(tag/100)%10-1;
        int t1=(tag/10)%10;
        //to deal with above
        if(r>0) {
            tag = (Integer) imageViews[r - 1][c].getTag();
            TodealwithDivideBLUE(tag, imageViews[r - 1][c]);
        }


        //to deal with below
        if(r<gridSize1-1) {
            tag = (Integer) imageViews[r + 1][c].getTag();
            TodealwithDivideBLUE(tag, imageViews[r + 1][c]);
        }

        //to deal with left
        if(c>0) {
            tag = (Integer) imageViews[r][c - 1].getTag();
            TodealwithDivideBLUE(tag, imageViews[r][c - 1]);
        }


        //to deal with right
        if(c<gridSize1-1) {
            tag = (Integer) imageViews[r][c + 1].getTag();
            TodealwithDivideBLUE(tag, imageViews[r][c + 1]);
        }

        //Toast.makeText(this,"hiiii",Toast.LENGTH_SHORT).show();


    }
 void TodealwithDivideRED(int tag,ImageView img)
 {
     if((tag/10)%10==0) {
         img.setImageResource(R.drawable.red_1);//set the tag as well
         img.setTag(tag+10+2);

     } else if ((tag/10)%10==1) {
         img.setImageResource(R.drawable.red_2);
         img.setTag((tag/10)*10+10+2);

     } else if ((tag/10)%10==2) {
         img.setImageResource(R.drawable.red_3);
         img.setTag((tag/10)*10+10+2);
     }
     else{
         img.setImageResource(R.drawable.solid_fill_offwhite);
         divide4Red(img);
     }


 }
    void TodealwithDivideBLUE(int tag,ImageView img)
    {
        if((tag/10)%10==0) {
            img.setImageResource(R.drawable.blue_1);//set the tag as well
            img.setTag(tag+10+1);

        } else if ((tag/10)%10==1) {
            img.setImageResource(R.drawable.blue_2);
            img.setTag((tag/10)*10+10+1);

        } else if ((tag/10)%10==2) {
            img.setImageResource(R.drawable.blue_3);
            img.setTag((tag/10)*10+10+1);
        }
        else{

            img.setImageResource(R.drawable.solid_fill_offwhite);
            divide4Blue(img);
        }


    }

void checKPointsandDispaly() {

    int count1 = 0, count2 = 0;
    int childcount = gridLayout.getChildCount();
    for (int i = 0; i < childcount; i++) {
        View v = gridLayout.getChildAt(i);
        ImageView imageView = (ImageView) v;
        Object obj = imageView.getTag();
        int t1 = (Integer) obj;
        if (t1 % 10 == 1) {
            count1 = count1 + (t1 / 10) % 10;

        }

        if (t1 % 10 == 2) {
            count2 = count2 + (t1 / 10) % 10;

        }


    }
    pl1_count = count1;
    pl2_count = count2;
    pl1_scor.setText(String.valueOf(pl1_count));
    pl2_scor.setText(String.valueOf(pl2_count));
    String winner = "";
    SharedPreferences pref=getSharedPreferences("wins",MODE_PRIVATE);
    SharedPreferences.Editor editor=pref.edit();
    if(cnt>1){
    if (count1 == 0 || count2 == 0) {
        if (count1 == 0) {

            winner = String.valueOf(pl2_nam.getText());
            editor.putInt("Playertwo",pref.getInt("Playertwo",0)+1);
            editor.apply();
        }
        if (count2 == 0) {
            winner = String.valueOf(pl1_nam.getText());
            editor.putInt("Playerone",pref.getInt("Playerone",0)+1);
            editor.apply();

        }

        winner_name.setText(winner);
        dg2.setCancelable(false);
        dg2.show();
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               play_again.setAnimation(scaleup);
                reset();
                dg2.dismiss();
            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_btn.setAnimation(scaleup);
                dg2.dismiss();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                setContentView(R.layout.activity_main);
                startActivity(i);
            }
        });
    }
}
    cnt++;
}
void onCLickcrossButton()
{
    cross_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //dispaly dialog

            dg1.setCancelable(true);
            dg1.show();
            quit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quit_btn.setAnimation(scaleup);
                    dg1.dismiss();

                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    setContentView(R.layout.activity_main);

                }
            });
            reset_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reset_btn.setAnimation(scaleup);
                    dg1.dismiss();
                    reset();
                }
            });
            //to reset or continue
        }
    });
}
void reset()
{
    cnt=0;
    int childcount=gridLayout.getChildCount();
    for(int i=0;i<childcount;i++) {
        View v = gridLayout.getChildAt(i);
        ImageView imageView = (ImageView) v;
        Object obj=imageView.getTag();
        int t1=(Integer)obj;
        imageView.setTag((t1/100)*100);
        imageView.setImageResource(R.drawable.solid_fill_offwhite);
    }
    pl2_scor.setText(String.valueOf(0));
    pl1_scor.setText(String.valueOf(0));
}


}

