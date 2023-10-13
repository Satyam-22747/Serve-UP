package com.satdroid.serveup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Choicepage extends AppCompatActivity {

    AppCompatButton donor, ngo;
    Intent intent;
    String type;
    LinearLayout bgimage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choicepage);

        AnimationDrawable animationDrawable=new AnimationDrawable();
        animationDrawable.addFrame(getDrawable(R.drawable.img1),3000);
        animationDrawable.addFrame(getDrawable(R.drawable.img2),3000);
        animationDrawable.addFrame(getDrawable(R.drawable.img3),3000);
        animationDrawable.addFrame(getDrawable(R.drawable.img4),3000);
        animationDrawable.addFrame(getDrawable(R.drawable.img5),3000);
        animationDrawable.addFrame(getDrawable(R.drawable.img6),3000);
        animationDrawable.addFrame(getDrawable(R.drawable.img7),3000);
        animationDrawable.addFrame(getDrawable(R.drawable.img8),3000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);

        bgimage=findViewById(R.id.back3);
        bgimage.setBackground(animationDrawable);
        animationDrawable.start();

        intent=getIntent();
        type=intent.getStringExtra("Home").toString().trim();

    donor=(AppCompatButton)findViewById(R.id.donor);
    ngo=(AppCompatButton)findViewById(R.id.NGO);

    donor.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(type.equals("Email"))
            {
                Intent loginemaildonor=new Intent(Choicepage.this,Donorloginemail.class);
            startActivity(loginemaildonor);
            finish();
            }
            if(type.equals("Phone"))
            {
                Intent loginphonedonor=new Intent(Choicepage.this,Donorloginphone.class);
                startActivity(loginphonedonor);
                finish();
            }

            if(type.equals("Signup"))
            {
                Intent DonorRegister=new Intent(Choicepage.this,Donorregister.class);
                startActivity(DonorRegister);
                finish();
            }
        }
    });

    ngo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(type.equals("Email"))
            {
                Intent loginemailngo=new Intent(Choicepage.this,NGOloginemail.class);
                startActivity(loginemailngo);
                finish();
            }
            if(type.equals("Phone"))
            {
                Intent loginphonengo=new Intent(Choicepage.this,NGOloginphone.class);
                startActivity(loginphonengo);
                finish();
            }

            if(type.equals("Signup"))
            {
                Intent NgoRegister=new Intent(Choicepage.this,NGOregister.class);
                startActivity(NgoRegister);
                finish();
            }
        }
    });



    }
}