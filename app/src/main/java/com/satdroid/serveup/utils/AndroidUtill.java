package com.satdroid.serveup.utils;

import android.content.Context;
import android.widget.Toast;

public class AndroidUtill {

    public static void showToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }
}
