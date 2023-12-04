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

public class NGOloginphone extends AppCompatActivity {

    EditText num;
    AppCompatButton sendOtp, signinEmail;
    TextView newSignup;
    CountryCodePicker cpp;
    FirebaseAuth fauth;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngologinphone);

        num=findViewById(R.id.PhoneNumber_NGO);
        sendOtp=findViewById(R.id.btnphoneOTP_NGO);
        cpp=findViewById(R.id.CCPphone_NGO);
        signinEmail=findViewById(R.id.NGO_login_email);
        newSignup=findViewById(R.id.new_account_NGO);
        fauth= FirebaseAuth.getInstance();

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number=num.getText().toString().trim();
                String PhoneNum=number+cpp.getSelectedCountryCodeWithPlus();
                Intent inext=new Intent(NGOloginphone.this,PhoneOTP_NGO.class);
                inext.putExtra("PhoneNumber_NGO",PhoneNum);
                startActivity(inext);
                finish();
            }
        });

    }
}