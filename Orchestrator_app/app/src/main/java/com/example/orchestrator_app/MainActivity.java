package com.example.orchestrator_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import androidx.annotation.Nullable;
import org.tensorflow.lite.examples.imageclassification.databinding.ActivityMainBinding;

/** Entrypoint for app */
///onCreate run - > tre fragments (Camera, classification, permission e delega a loro il funzionamneto )
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }



}