package com.satdroid.serveup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hbb20.CountryCodePicker;

public class Donorregister extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    String UttarPradesh[]={"Sultanpur","Lucknow","Prayagraj","Kanpur","Banaras"};
    TextInputLayout fname,Lname,Email,Pass,cpass,mobileno,houseno,area,pincode;
    Spinner Statespin,Cityspin;
    AppCompatButton Signup, EmailL, Phone;
    CountryCodePicker cpp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorregister);
    }
}