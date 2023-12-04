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
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Firebase;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class Donorregister extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    String UttarPradesh[]={"Sultanpur","Lucknow","Prayagraj","Kanpur","Banaras"};
    TextInputLayout Fname,Lname,Email,Password,cnfpass,mobileno,addressLine1,addressLine2,pincode;
    Spinner Statespin,Cityspin;
    AppCompatButton registerDonorbtn, EmailLbtn, Phonebtn;
    CountryCodePicker cpp;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname,lname, emailid, password, confpassword, mobile, house,Area,Pincode,role="Donor",addressline1,addressline2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorregister);

        Fname=(TextInputLayout)findViewById(R.id.firstName_donor);
        Lname=(TextInputLayout)findViewById(R.id.lastName_donor);
        Email=(TextInputLayout)findViewById(R.id.email_donor);
        Password=(TextInputLayout)findViewById(R.id.password_donor);
        cnfpass=(TextInputLayout)findViewById(R.id.cnfm_password_donor);
        mobileno=(TextInputLayout)findViewById(R.id.phone_donor);
        addressLine1=(TextInputLayout)findViewById(R.id.address1_donor);
        addressLine2=(TextInputLayout)findViewById(R.id.address2_donor);
        pincode=(TextInputLayout)findViewById(R.id.pincode_donor);

        registerDonorbtn=(AppCompatButton)findViewById(R.id.btn_register_donor);
        EmailLbtn=(AppCompatButton)findViewById(R.id.signin_email_donor);
        Phonebtn=(AppCompatButton)findViewById(R.id.signin_phone_donor);

        cpp=(CountryCodePicker)findViewById(R.id.country_code_donor);

        databaseReference=firebaseDatabase.getInstance().getReference("Donor");
        FAuth= FirebaseAuth.getInstance();

        registerDonorbtn.setOnClickListener(new View.OnClickListener() {
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
                    final ProgressDialog mDialog=new ProgressDialog(Donorregister.this);
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

                                        firebaseDatabase.getInstance().getReference("Donor")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();
                                                        FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Donorregister.this);
                                                                    builder.setMessage("You have registered Successfully. Kindly verify your email!!");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                            dialog.dismiss();
                                                                            String mobileNumber= cpp.getSelectedCountryCodeWithPlus()+mobile;
                                                                            Intent donorintent=new Intent(Donorregister.this,DonorVerifyPhone.class);
                                                                            donorintent.putExtra("mobilenumber",mobileNumber);
                                                                            startActivity(donorintent);
                                                                        }
                                                                    });
                                                                    AlertDialog Alert = builder.create();
                                                                    Alert.show();
                                                                }
                                                                else {
                                                                    mDialog.dismiss();
                                                                    ReusableCodeforAll.ShowAlert(Donorregister.this,"Error",task.getException().getMessage());
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });
                            }
                            else{
                                mDialog.dismiss();
                                ReusableCodeforAll.ShowAlert(Donorregister.this,"Error",task.getException().getMessage());
                            }

                        }
                    });
                }
            }
        });

        EmailLbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Donorregister.this,Donorloginemail.class));
                finish();
            }
        });

        Phonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Donorregister.this,Donorloginphone.class));
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