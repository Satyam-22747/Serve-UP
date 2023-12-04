package com.satdroid.serveup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Donorloginphone extends AppCompatActivity {

    EditText num;
    AppCompatButton sendOtp, signinEmail;
    TextView newSignup;
    CountryCodePicker cpp;
    FirebaseAuth fauth;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorloginphone);

        num=findViewById(R.id.PhoneNotext_donor);
        sendOtp=findViewById(R.id.btnphoneOTP_donor);
        cpp=findViewById(R.id.CCPphonetext_donor);
        signinEmail=findViewById(R.id.donorloginemail);
        newSignup=findViewById(R.id.new_account_donor);

        fauth=FirebaseAuth.getInstance();

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number=num.getText().toString().trim();
                String Phonenum=cpp.getSelectedCountryCodeWithPlus()+number;
                Intent inext=new Intent(Donorloginphone.this,PhoneOTp_donor.class);

                inext.putExtra("Phonenum",Phonenum);
                startActivity(inext);
                finish();
            }
        });

    }
}