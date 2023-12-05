package com.satdroid.serveup.DonorPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.satdroid.serveup.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class Post_Dish extends AppCompatActivity {

  /*  ImageButton imageButton;
    AppCompatButton post_dish_btn;
    TextInputLayout desc,qty,foodname;
    String description, quantity, FoodName;
    Uri imageUri;
    private Uri mCropUrImageuri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,data;
    FirebaseAuth fauth;
    StorageReference ref;
    String DonorId, RandomUid, Addressline1, Addressline2;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_dish);

  /*      storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        desc=findViewById(R.id.description_donor);
        qty=findViewById(R.id.food_quantity);
        foodname=findViewById(R.id.food_item_name);
      //  post_dish_btn=findViewById(R.id.post_food_btn);
        fauth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getInstance().getReference("FoodItemDetails");

        try {
            String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            data=firebaseDatabase.getInstance().getReference("Donor").child(userid);
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Donor donor=snapshot.getValue(Donor.class);
                    Addressline1=donor.getAddressLine1();
                    Addressline2=donor.getAddressLine2();
                    imageButton=findViewById(R.id.imageButton);

                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSelectImageclick(v);

                        }
                    });
                    post_dish_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FoodName = foodname.getEditText().getText().toString().trim();
                            description = desc.getEditText().getText().toString().trim();
                            quantity = qty.getEditText().getText().toString().trim();

                            if(isValid())
                            {
                                uploadImage();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e)
        {
            Log.e("Error: ",e.getMessage());
        }*/
    }

  /*  private void uploadImage()
    {
        if(imageUri!=null)
        {
            final ProgressDialog progressDialog=new ProgressDialog(Post_Dish.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            RandomUid= UUID.randomUUID().toString();
            ref=storageReference.child(RandomUid);
            DonorId=FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FoodItemDetails info = new FoodItemDetails(FoodName, quantity, description, String.valueOf(uri), RandomUid, DonorId);
                            firebaseDatabase.getInstance().getReference("FoodDetails").child(Addressline1).child(Addressline2).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUid)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Post_Dish.this, "Dish Posted Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Post_Dish.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot)
                {
                    double progress = (100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int) progress+"%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });

        }
    }

    private boolean isValid()
    {
        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        foodname.setErrorEnabled(false);
        foodname.setError("");

        boolean isValidDescription = false,isValidFoodname=false,isValidQuantity=false,isValid=false;
        if(TextUtils.isEmpty(description)){
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");
        }else{
            desc.setError(null);
            isValidDescription=true;
        }
        if(TextUtils.isEmpty(quantity)){
            qty.setErrorEnabled(true);
            qty.setError("Enter food quantity");
        }else{
            isValidQuantity=true;
        }
        if(TextUtils.isEmpty(FoodName)){
            foodname.setErrorEnabled(true);
            foodname.setError("Please provide the food name");
        }else{
            isValidFoodname=true;
        }
        isValid = (isValidDescription && isValidQuantity && isValidFoodname)?true:false;
        return isValid;
    }
    private void startCropImageActivity(Uri imageuri){
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    private void onSelectImageclick(View v){

        CropImage.startPickImageActivity(this);
    }

    //@Override
    public void onRequestPermissionsResultCall(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(mCropUrImageuri !=null && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            startCropImageActivity(mCropUrImageuri);
        }else{
            Toast.makeText(this,"Cancelling! Permission Not Granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            imageUri= CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageUri)){
                mCropUrImageuri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else{
                startCropImageActivity(imageUri);
            }
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                ((ImageButton) findViewById(R.id.imageButton)).setImageURI(result.getUri());
                Toast.makeText(this,"Cropped Successfully!",Toast.LENGTH_SHORT).show();
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(this,"Failed To Crop"+result.getError(),Toast.LENGTH_SHORT).show();

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }*/
}