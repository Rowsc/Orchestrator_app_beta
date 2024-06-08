package com.example.orchestrator_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloaderActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "HttpImageExample";
    private ImageView myImage;
    private EditText urlText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_downloader);
        urlText = (EditText) findViewById(R.id.imageUrl);
        myImage = (ImageView) findViewById(R.id.myImage);

    }
    public void DownloadImageClick(View v) {
        String ImageUrlString = urlText.getText().toString();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr!= null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new DownloadImageTask().execute(ImageUrlString);
            } else {
                Toast.makeText(getApplicationContext(),"No network connection available.",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
// params comes from the execute() call: urls[0] is the url.
            try {
                return downloadImage(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result!=null){
                myImage.setImageBitmap(result);
            } else {

                Toast.makeText(getApplicationContext(),"Unavailable image. Check the URL field.",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private Bitmap downloadImage(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
// Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();
// Convert the InputSteam into an image
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
// Makes sure that the InputStream is closed after the app is
// finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}

