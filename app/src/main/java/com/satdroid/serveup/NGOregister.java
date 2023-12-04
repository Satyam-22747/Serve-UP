package com.satdroid.serveup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class NGOregister extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    TextInputLayout Fname,Lname,Email,Password,cnfpass,mobileno,addressLine1,addressLine2,pincode;
    AppCompatButton registerNGObtn, emailLbtn, phonebtn;
    CountryCodePicker cpp;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname,lname, emailid, password, confpassword, mobile, house,Area,Pincode,role="NGO",addressline1,addressline2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngoregister);

        Fname=(TextInputLayout)findViewById(R.id.firstName_ngo);
        Lname=(TextInputLayout)findViewById(R.id.lastName_ngo);
        Email=(TextInputLayout)findViewById(R.id.email_input_ngo);
        Password=(TextInputLayout)findViewById(R.id.password_ngo);
        cnfpass=(TextInputLayout)findViewById(R.id.cnfm_password_ngo);
        mobileno=(TextInputLayout)findViewById(R.id.phone_input_ngo);
        addressLine1=(TextInputLayout)findViewById(R.id.address1_ngo);
        addressLine2=(TextInputLayout)findViewById(R.id.address2_ngo);
        pincode=(TextInputLayout)findViewById(R.id.pincode_ngo);
        registerNGObtn=(AppCompatButton)findViewById(R.id.register_btn_ngo);
        emailLbtn=(AppCompatButton)findViewById(R.id.email_btn_ngo);
        phonebtn=(AppCompatButton)findViewById(R.id.phone_btn_ngo);
        cpp=(CountryCodePicker)findViewById(R.id.country_code_ngo);
        databaseReference=firebaseDatabase.getInstance().getReference("NGO");
        FAuth= FirebaseAuth.getInstance();

        registerNGObtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname=Fname.getEditText().getText().toString().trim();
                lname=Lname.getEditText().getText().toString().trim();
                emailid=Email.getEditText().getText().toString().trim();
                password=Password.getEditText().getText().toString().trim();
                confpassword=cnfpass.getEditText().getText().toString().trim();
                mobile=mobileno.getEditText().getText().toString().trim();
                Pincode=pincode.getEditText().getText().toString().trim();
                addressline1=addressLine1.getEditText().getText().toString().trim();
                addressline2=addressLine2.getEditText().getText().toString().trim();

                if(isValid())
                {
                    final ProgressDialog mDialog=new ProgressDialog(NGOregister.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Regitration in progress please wait....");
                    mDialog.show();
                    FAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                String useridDonor=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference=FirebaseDatabase.getInstance().getReference("User").child(useridDonor);
                                final HashMap<String,String> hashmap=new HashMap<>();
                                hashmap.put("Role",role);
                                databaseReference.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        HashMap<String,String> hashMap1=new HashMap<>();
                                        hashMap1.put("Mobile No",mobile);
                                        hashMap1.put("First Name",fname);
                                        hashMap1.put("Last Name",lname);
                                        hashMap1.put("Email Id",emailid);
                                        hashMap1.put("Mobile No",mobile);
                                        hashMap1.put("Password",password);
                                        hashMap1.put("Pin Code",Pincode);
                                        hashMap1.put("Confirm Password",confpassword);
                                        hashMap1.put("Address Line1",addressline1);
                                        hashMap1.put("Address Line2",addressline2);

                                        firebaseDatabase.getInstance().getReference("NGO")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();
                                                        FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(NGOregister.this);
                                                                    builder.setMessage("You have registered Successfully. Kindly verify your email!!");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                            dialog.dismiss();
                                                                            String mobileNumber= cpp.getSelectedCountryCodeWithPlus()+mobile;
                                                                            Intent donorintent=new Intent(NGOregister.this,NGOVerifyPhone.class);
                                                                            donorintent.putExtra("mobilenumberNGO",mobileNumber);
                                                                            startActivity(donorintent);
                                                                        }
                                                                    });
                                                                    AlertDialog Alert = builder.create();
                                                                    Alert.show();
                                                                }
                                                                else {

                                                                    mDialog.dismiss();
                                                                    ReusableCodeforAll.ShowAlert(NGOregister.this,"Error",task.getException().getMessage());

                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });
                            }
                            else
                            {
                                mDialog.dismiss();
                                ReusableCodeforAll.ShowAlert(NGOregister.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

        emailLbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NGOregister.this,NGOloginemail.class));
                finish();
            }
        });
        phonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NGOregister.this, NGOloginphone.class));
                finish();
            }
        });


    }

    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid()
    {
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cnfpass.setErrorEnabled(false);
        cnfpass.setError("");
        pincode.setErrorEnabled(false);
        pincode.setError("");
        addressLine1.setErrorEnabled(false);
        addressLine1.setError("");
        addressLine2.setErrorEnabled(false);
        addressLine2.setError("");

        boolean isValid=false,isValidfname=false,isValidlname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobileno=false,isValidpincode=false,isValidaddressline1=false,isValidaddressline2=false;
        if(TextUtils.isEmpty(fname))
        {
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        }
        else
        {
            isValidfname=true;
        }
        if(TextUtils.isEmpty(lname))
        {
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        }
        else
        {
            isValidlname=true;
        }
        if(TextUtils.isEmpty(emailid))
        {
            Email.setErrorEnabled(true);
            Email.setError("Email is required");
        }
        else
        {
            if(emailid.matches(emailPattern)) {
                isValidemail = true;
            }

            else
            {
                Email.setErrorEnabled(true);
                Email.setError("Enter a valid Email id");
            }
        }
        if(TextUtils.isEmpty(password))
        {
            Password.setErrorEnabled(true);
            Password.setError("Enter Password");
        }
        else
        {
            if(password.length()<8)
            {
                Password.setErrorEnabled(true);
                Password.setError("Password is Weak");
            }
            else {
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confpassword))
        {
            cnfpass.setErrorEnabled(true);
            cnfpass.setError("Enter password again");
        }
        else
        {
            if(!password.equals(confpassword))
            {
                cnfpass.setErrorEnabled(true);
                cnfpass.setError("Password didn't match");
            }
            else
            {isValidconfpassword=true;}
        }
        if(TextUtils.isEmpty(mobile))
        {
            mobileno.setErrorEnabled(true);
            mobileno.setError("Enter mobile no");
        }
        else
        {
            if(mobile.length()<10)
            {
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid Mobile no");
            }
            else
            {isValidmobileno=true;}
        }
        if(TextUtils.isEmpty(Pincode))
        {
            pincode.setErrorEnabled(true);
            pincode.setError("Please enter pincode");
        }
        else
        {
            isValidpincode=true;
        }
        if(TextUtils.isEmpty(addressline1))
        {
            addressLine1.setErrorEnabled(true);
            addressLine1.setError("Enter ths valid address");
        }
        else
        {
            isValidaddressline1=true;
        }
        if(TextUtils.isEmpty(addressline2))
        {
            addressLine2.setErrorEnabled(true);
            addressLine2.setError("Enter ths valid address");
        }
        else
        {
            isValidaddressline2=true;
        }
        isValid=(isValidaddressline1&&isValidaddressline2&&isValidfname&&isValidlname&&isValidemail&&isValidconfpassword&&isValidpassword&&isValidmobileno&&isValidpincode) ?true:false;
        return isValid;
    }


}