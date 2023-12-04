package com.satdroid.serveup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NGOloginemail extends AppCompatActivity {

    AppCompatButton signin,signinphone;
    TextInputLayout email, pass;
    TextView forgotPassword, createAccount;
    String emailid,pwd;

    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngologinemail);

        try{
            signin=findViewById(R.id.emailloginbtn_NGO);
            signinphone=findViewById(R.id.btnphonelogin_NGO);
            email=findViewById(R.id.loginemail_NGO);
            pass=findViewById(R.id.loginpass_NGO);
            forgotPassword=findViewById(R.id.forgotpass_NGO);
            createAccount=findViewById(R.id.createaccounttextview_NGO);

            fauth=FirebaseAuth.getInstance();

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailid=email.getEditText().getText().toString().trim();
                    pwd=pass.getEditText().getText().toString().trim();
                    if(isValid()){
                        final ProgressDialog mDialog=new ProgressDialog(NGOloginemail.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Login in please wait....");
                        mDialog.show();

                        fauth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    mDialog.dismiss();

                                    if(fauth.getCurrentUser().isEmailVerified()){
                                        mDialog.dismiss();
                                        Toast.makeText(NGOloginemail.this,"Congratulations! you have succesully loged in",Toast.LENGTH_SHORT).show();
                                        Intent inext=new Intent(NGOloginemail.this,NGO_panel_bottomNavigationview.class);
                                        startActivity(inext);
                                        finish();
                                    }
                                    else{
                                        ReusableCodeforAll.ShowAlert(NGOloginemail.this,"Verificaion failed","You have not verrified");
                                    }
                                }
                                else{
                                    mDialog.dismiss();
                                    ReusableCodeforAll.ShowAlert(NGOloginemail.this,"Eror",task.getException().getMessage());
                                }

                            }
                        });
                    }
                }
            });
            createAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(NGOloginemail.this,NGOregister.class));
                    finish();
                }
            });

            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(NGOloginemail.this,DonorForgotPass.class));
                    finish();
                }
            });

            signinphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(NGOloginemail.this,NGOloginphone.class));
                    finish();
                }
            });
        } catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){
        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isValid=false, isValidemail=false, isvalidpassword=false;
        if(TextUtils.isEmpty(emailid)){
            email.setErrorEnabled(true);
            email.setError("Email is required");
        }
        else {
            if(emailid.matches(emailpattern)){
                isValidemail=true;
            }
            else{
                email.setErrorEnabled(true);
                email.setError("Invalid email address");
            }
        }
        if(TextUtils.isEmpty(pwd)){

            pass.setErrorEnabled(true);
            pass.setError("Password is required");
        }
        else{
            isvalidpassword=true;
        }
        isValid=(isValidemail&&isvalidpassword) ?true:false;
        return isValid;


    }


}