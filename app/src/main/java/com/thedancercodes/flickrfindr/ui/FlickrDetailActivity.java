package com.thedancercodes.flickrfindr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thedancercodes.flickrfindr.R;
import com.thedancercodes.flickrfindr.utils.Constants;

public class FlickrDetailActivity extends AppCompatActivity {

    public static String KEY_FLICKR_URL
            = "com.thedancercodes.flickrfindr.ui.FlickrDetailActivity.flickrURL";

    private ProgressBar mProgressBar;
    private ImageView mFlickrImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_detail);

        // Configure Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_flickr_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        }

        //Views
        mProgressBar = findViewById(R.id.progress_flickr_detail);
        mFlickrImage = findViewById(R.id.image_flickr);

        //Flickr URL
        Intent sourceIntent = getIntent();
        if (sourceIntent == null) {
            return;
        }

        //Get the Flickr URL attached to the Intent
        String flickrUrl = sourceIntent.getStringExtra(KEY_FLICKR_URL);
        if (flickrUrl.isEmpty()) {
            onErrorChanged(Constants.INVALID_FLICKR_URL_ERROR);
        } else {

            // Receive data from Intent
            String flickrPhoto = getIntent().getExtras().getString(KEY_FLICKR_URL);

            //Set the flickr image
            RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_thumbnail_placeholder);
            Glide.with(this)
                    .load(flickrPhoto)
                    .apply(options)
                    .into(mFlickrImage);

            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Called when a new error message is needs to be displayed to the user
     * @param message the error message to display
     */
    private void onErrorChanged(String message) {
        if (message == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage(message).setPositiveButton("OK", null).show();
    }
}
