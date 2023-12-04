package com.satdroid.serveup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class splashscreen extends AppCompatActivity {

    ImageView imganim;
    TextView txt;
    FirebaseAuth Fauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
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

                Fauth=FirebaseAuth.getInstance();
                if(Fauth.getCurrentUser()!=null){
                    if(Fauth.getCurrentUser().isEmailVerified()){
                        Fauth=FirebaseAuth.getInstance();

                        databaseReference=FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()+"/Role");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String role=snapshot.getValue(String.class);
                                if(role.equals("Donor")){
                                    startActivity(new Intent(splashscreen.this, Donor_panel_bottomNavigationview.class));
                                    finish();
                                }
                                if(role.equals("NGO")){
                                    startActivity(new Intent(splashscreen.this, NGO_panel_bottomNavigationview.class));
                                    finish();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(splashscreen.this,error.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(splashscreen.this);
                        builder.setMessage("Check whether your email is verified or not, otherwise verify your registered email");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent imain=new Intent(splashscreen.this, MainActivity.class);
                                startActivity(imain);
                                finish();
                            }
                        });
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                        Fauth.signOut();
                    }
                }

                else {
                    Intent imain = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(imain);
                    finish();
                }
            }}, 1000);

    }
    }
