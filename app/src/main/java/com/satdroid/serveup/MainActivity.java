package com.satdroid.serveup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
AppCompatButton signEm, signPh, signNew;
ImageView bgimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);
       // bgimage = findViewById(R.id.bgimage);
     //   bgimage.setAnimation(zoomin);
     //   bgimage.setAnimation(zoomout);

/*        zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bgimage.startAnimation(zoomin);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bgimage.startAnimation(zoomout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
*/
        signEm = findViewById(R.id.signem);
        signPh = findViewById(R.id.signph);
        signNew = findViewById(R.id.signnew);

        signEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent isignem = new Intent(MainActivity.this, Choicepage.class);
                isignem.putExtra("Home", "Email");
                startActivity(isignem);
            }
        });

        signPh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent isignph = new Intent(MainActivity.this, Choicepage.class);
                isignph.putExtra("Home", "Phone");
                startActivity(isignph);
            }
        });

        signNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent isignnew = new Intent(MainActivity.this, Choicepage.class);
                isignnew.putExtra("Hello", "New");
                startActivity(isignnew);
            }
        });

    }

}