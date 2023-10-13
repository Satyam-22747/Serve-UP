package com.satdroid.serveup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashscreen extends AppCompatActivity {

    ImageView imganim;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        imganim=findViewById(R.id.imgAnim);
        txt=findViewById(R.id.Txtview);

        Animation alpha= AnimationUtils.loadAnimation(splashscreen.this,R.anim.zoomlogo);
        Animation textalpha= AnimationUtils.loadAnimation(splashscreen.this,R.anim.txtanim);

        imganim.setAnimation(alpha);
        txt.setAnimation(textalpha);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent imain=new Intent(splashscreen.this, MainActivity.class);
                startActivity(imain);
                finish();
            }}, 4000);

    }
    }
