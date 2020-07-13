package com.example.diceanime;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Random;

import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    //サイコロの目の数　定数
    private static final int DiCE_COUNT = 6;
    //サイコロの出目（ランダムな値）の入れ物　変数
    private int diceNo;

    //ランダムな値を生成するクラス
    private Random random;

    //画面左上のText部品
    private TextView textDicePip;
    //画面中央のサイコロのImageView部品
    private ImageView imageView;
    //音楽を再生する部品
    private MediaPlayer mediaPlayer;

    //サイコロを停止させる定数
    private static final int DICE_STOP = 0;

    //アニメーションを表示させる部品
    private AnimationDrawable diceAnimation;

    private TextView dice_result;

    /*
    サイコロの出目に応じて、適切な画像,テキストを表示させる処理
    */
    private void setImage( int diceNo){
        switch (diceNo){
            case 1:
                imageView.setImageResource(R.drawable.d1);
                dice_result.setText("1");
                break;
            case 2:
                imageView.setImageResource(R.drawable.d2);
                dice_result.setText("2");
                break;
            case 3:
                imageView.setImageResource(R.drawable.d3);
                dice_result.setText("3");
                break;
            case 4:
                imageView.setImageResource(R.drawable.d4);
                dice_result.setText("4");
                break;
            case 5:
                imageView.setImageResource(R.drawable.d5);
                dice_result.setText("5");
                break;
            case 6:
                imageView.setImageResource(R.drawable.d6);
                dice_result.setText("6");
                break;
        }
    }


    private void setMusic(){
        mediaPlayer = MediaPlayer.create(this,R.raw.dice1);
    }

    //サイコロのアニメーション処理
    private void diceRoll(){
        //サイコロの画像をタッチする際、サイコロが止まっていると動かす処理
        if(diceNo == DICE_STOP) {
            setAnimationImage();

            diceAnimation.start();
            //サイコロの出目（1～6）の作成
            diceNo = random.nextInt(DiCE_COUNT) + 1;

            //画面左上のメッセージ（止まります）に変更
            textDicePip.setText(R.string.message_stop_touch);
            //画面中央上のメッセージ（結果は・・・）に変更
            dice_result.setText(R.string.message_dice_result);
            //音楽の再生
            //音楽ファイルの再生位置を0秒目に設定
            mediaPlayer.seekTo(0);
            mediaPlayer.start();

        //サイコロの画像をタッチする際、サイコロが動いていると止まる処理
        } else {
            diceAnimation.stop();

            setImage(diceNo);


            diceNo = DICE_STOP;
            //画面左上のメッセージ（動きます）に変更

            textDicePip.setText(R.string.message_start_touch);
        }
    }
    //アニメーション処理
    private void setAnimationImage(){
        imageView.setImageResource(R.drawable.dice_anime);
        diceAnimation = (AnimationDrawable) imageView.getDrawable();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //指を離すとき（ACTION_UP)だけ判定させる
        if (event.getAction() == MotionEvent.ACTION_UP){
            //diceRoll処理実行
            diceRoll();
        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //レイアウトXMLのファイル名を指定する
        setContentView(R.layout.activity_main);

        //ランダム部品生成
        random  = new Random();


        //レイアウトXMLファイル上で作成したTextView部品のIDを指定する
        textDicePip = (TextView)findViewById(R.id.textView);

        dice_result = (TextView)findViewById(R.id.textView3);

        //起動時には「動きます！」と表示
        textDicePip.setText(R.string.message_start_touch);
        //起動時には「結果は・・・」と表示
        dice_result.setText(R.string.message_dice_result);



        //レイアウトXMLファイル上で作成したImageView部品のIDを指定する
        imageView = (ImageView)findViewById(R.id.imageView);



        //音楽再生部品を生成するメソッドを呼び出す
        setMusic();

        //起動時のサイコロは止まらせておく
        diceNo = DICE_STOP;

    }
}
