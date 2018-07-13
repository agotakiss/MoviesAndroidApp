package com.agotakiss.androidtest.presentation;

import android.app.Activity;
import android.util.Log;

public class BaseActivity extends Activity {
    protected final String TAG = this.getClass().getSimpleName();


    protected void logE(Object message){
        Log.e(TAG, message.toString());
    }
    protected void logD(String message){
        Log.d(TAG, message);
    }
}
