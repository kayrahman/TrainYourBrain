/*
TODO:
1,Add adMob account-done
2,Share the app
3,"Time remaining","High Score","Tap the right answer"-done
4,challenge your friend
5,Share on facebook
6,add some animations

*/
package com.nkr.neel.trainyourbrain;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private Button startBtn;
    private ArrayList<Integer> answers=new ArrayList<Integer>();
    private int locationOfCorrectAnswer;

    private int score=0;
    int numOfQuestions=0;
    TextView pointTextView;
    RelativeLayout mRelativeLayout;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;

    TextView resultTxtRl;
    TextView resultTxt;
    TextView sumTextView;
    private TextView remSec;
    Button playAgainBtn;

    RelativeLayout gameLayout;
    RelativeLayout mResultLayout;

    int sign=0;
    boolean isFinished=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn=(Button)findViewById(R.id.startBtn);
        sumTextView=(TextView)findViewById(R.id.sumTxt);

         button1=(Button)findViewById(R.id.button1);
         button2=(Button)findViewById(R.id.button2);
         button3=(Button)findViewById(R.id.button3);
         button4=(Button)findViewById(R.id.button4);
         button5=(Button)findViewById(R.id.button5);
         button6=(Button)findViewById(R.id.button6);

        resultTxt=(TextView)findViewById(R.id.resultTxt);
        resultTxtRl=(TextView)findViewById(R.id.resultTxtRl);
        pointTextView=(TextView)findViewById(R.id.scoreTxt);
        remSec=(TextView)findViewById(R.id.remSecondsTxt);
        playAgainBtn=(Button)findViewById(R.id.playAgainBtn);
        gameLayout=(RelativeLayout)findViewById(R.id.gameRelativeLayout);
        mRelativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        mResultLayout=(RelativeLayout)findViewById(R.id.resultLayout);

        try {
            updateQuestion();

        }catch (IllegalStateException ex){
            Log.i("Dowuble Tap", String.valueOf(ex));
        }

        catch (Exception ex){
            Log.i("Exception", String.valueOf(ex));
        };



    }
    public void changeBackgroundColor(RelativeLayout layout){
        String color;
        String[] colors={
                "#39add1",
                "#3079ab",
                "#c25975",
                "#e0ab18",
                "#637a91",
                "#f092b0",
                "#e15258",
                "#b7c0c7",
                "#f9845b",
                "#838cc7",
                "#7d669e",
                "#53bbb4",
                "#51b46d"

        };
        Random random=new Random();
       int randomNumbers=random.nextInt(colors.length);
         color=colors[randomNumbers];
        int colorAsint= Color.parseColor(color);

        layout.setBackgroundColor(colorAsint);
    }

    public void updateQuestion(){
        Random rand=new Random();
        int a=rand.nextInt(31);
        int b=rand.nextInt(31);

        sign=rand.nextInt(4);

        for(int i=0;i<=3;i++){
            if(sign==0){
                sumTextView.setText(Integer.toString(a)+"+"+Integer.toString(b));
            }
            else if(sign==1){
                sumTextView.setText(Integer.toString(a)+"-"+Integer.toString(b));
            }
            else if (sign==2){
                sumTextView.setText(Integer.toString(a)+"*"+Integer.toString(b));
            }
            else if(sign==3){
                sumTextView.setText(Integer.toString(a)+"/"+Integer.toString(b));
            }
        }


        locationOfCorrectAnswer=rand.nextInt(5);

        answers.clear();


        int incorrectanswer;
        for(int i=0;i<=6;i++){
            if(i==locationOfCorrectAnswer&&sign==0){
                answers.add(a+b);
            }
            else if(i==locationOfCorrectAnswer&&sign==1){
                answers.add(a-b);
            }
            else if(i==locationOfCorrectAnswer && sign==2){
                answers.add(a*b);
            }
            else if(i==locationOfCorrectAnswer&& sign==3){
               try {
                   answers.add(a / b);
               }catch (ArithmeticException e){
                   Log.i("Can not divide by 0", String.valueOf(e));
               }
            }
            else{
                incorrectanswer=rand.nextInt(61);
                while(incorrectanswer==a+b){
                    incorrectanswer=rand.nextInt(61);
                }

                answers.add(incorrectanswer);
            }
        }

        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));
        button5.setText(Integer.toString(answers.get(4)));
        button6.setText(Integer.toString(answers.get(5)));

        changeBackgroundColor(mRelativeLayout);

    }

    public void start(View view) {
        startBtn.setVisibility(view.INVISIBLE);
        mResultLayout.setVisibility(view.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);

        playAgain(findViewById(R.id.playAgainBtn));

    }

    public void chooseAnswer(View view) {

        if(isFinished==false) {
            resultTxt.setVisibility(view.VISIBLE);
            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
                resultTxt.setText("Correct!");
                score++;
            } else {
                resultTxt.setText("Wrong!");

            }
            numOfQuestions++;

            pointTextView.setText(Integer.toString(score) + "/" + Integer.toString(numOfQuestions));

            updateQuestion();
        }
        else{
            resultTxt.setText("Game Over:Your Score:"+ Integer.toString(score)+"/"+ Integer.toString(numOfQuestions));

        }
    }

    public void playAgain(final View view) {
        isFinished=false;
        score=0;
        numOfQuestions=0;
        resultTxt.setVisibility(View.INVISIBLE);
        pointTextView.setText("0/0");
        gameLayout.setVisibility(view.VISIBLE);
        mResultLayout.setVisibility(view.INVISIBLE);



        new CountDownTimer(30*1000+100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                remSec.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                isFinished=true;

                remSec.setText("0s");


                gameLayout.setVisibility(view.INVISIBLE);

                mResultLayout.setVisibility(view.VISIBLE);
                resultTxtRl.setVisibility(view.VISIBLE);
                resultTxtRl.setText("Your Score:"+ Integer.toString(score)+"/"+ Integer.toString(numOfQuestions));
                playAgainBtn.setVisibility(view.VISIBLE);



            }
        }.start();

    }
}
