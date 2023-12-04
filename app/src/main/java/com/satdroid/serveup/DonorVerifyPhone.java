package com.satdroid.serveup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class DonorVerifyPhone extends AppCompatActivity {

    String phoneNumber;
    EditText otpInput;
   long timeoutSeconds = 120L;
    AppCompatButton verifybtn,resendOTPbtn;
    ProgressBar progressBar;
    TextView resendOTPtextview;

    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    FirebaseAuth mauth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_verify_phone);

        otpInput=findViewById(R.id.phoneotp_donor);
        verifybtn=findViewById(R.id.phoneOtPNext_btn_donor);
        progressBar=findViewById(R.id.pgBar_donor);
        resendOTPbtn=findViewById(R.id.phoneOtpResendbtn_donor);
        resendOTPtextview=findViewById(R.id.phoneOtpTextview_donor);

        phoneNumber=getIntent().getExtras().getString("mobilenumber");
        sendOtp(phoneNumber,false);

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredOTP=otpInput.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCode,enteredOTP);
                signIn(credential);
                setInprogress(true);
            }
        });

        resendOTPbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp(phoneNumber,true);
            }
        });
    }
    void sendOtp(String phoneNumber, boolean isResend)
    {
        startResendTimer();
        setInprogress(true);
        PhoneAuthOptions.Builder builder=
         PhoneAuthOptions.newBuilder(mauth)
                 .setPhoneNumber(phoneNumber)
                 .setTimeout(60L,TimeUnit.SECONDS)
                 .setActivity(this)
                 .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                     @Override
                     public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                         signIn(phoneAuthCredential);
                         setInprogress(false);
                     }
                     @Override
                     public void onVerificationFailed(@NonNull FirebaseException e) {
                         AndroidUtil.showToast(getApplicationContext(),"verificaiton failed");
                         setInprogress(false);
                     }
                     @Override
                     public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                         super.onCodeSent(s, forceResendingToken);
                         verificationCode=s;
                         resendingToken=forceResendingToken;
                         AndroidUtil.showToast(getApplicationContext(),"OTP sent sucessfully");
                         setInprogress(false);
                     }
                 });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }
        else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

     void setInprogress(boolean inProgress) {
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            verifybtn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            verifybtn.setVisibility(View.VISIBLE);
        }

    }
    void signIn(PhoneAuthCredential phoneAuthCredential){

        setInprogress(true);
        mauth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                setInprogress(false);
                if(task.isSuccessful()){
                    AndroidUtil.showToast(getApplicationContext(),"OTP verified");
                    Intent inext=new Intent(DonorVerifyPhone.this,MainActivity.class);
                    startActivity(inext);

                }
                else
                {
                    AndroidUtil.showToast(getApplicationContext(),"OTP verifiacaition failed");
                }
            }
        });

    }

    void startResendTimer() {
        resendOTPbtn.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                resendOTPtextview.setText("Resend OTP in " + timeoutSeconds + " seconds");
                if (timeoutSeconds <= 0) {
                    timeoutSeconds = 120L;
                    timer.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resendOTPbtn.setEnabled(true);
                        }
                    });
                }
            }
        },0,1000);
    }
    }

