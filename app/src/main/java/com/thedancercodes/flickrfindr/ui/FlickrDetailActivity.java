package com.thedancercodes.flickrfindr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.thedancercodes.flickrfindr.R;
import com.thedancercodes.flickrfindr.utils.Constants;
import com.thedancercodes.flickrfindr.viewmodel.MainViewModel;

public class FlickrDetailActivity extends AppCompatActivity {

    public static String KEY_FLICKR_ID
            = "com.thedancercodes.flickrfindr.ui.FlickrDetailActivity.flickrId";

    private MainViewModel mViewModel;

    private ProgressBar mProgressBar;
    private ImageView mFlickrImage;

    private boolean mIsLoadingMoreComments = false;

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

        //ViewModel
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        //Flickr ID
        Intent sourceIntent = getIntent();
        if (sourceIntent == null) {
            return;
        }

        //Get the Flickr ID attached to the Intent
        String flickrId = sourceIntent.getStringExtra(KEY_FLICKR_ID);
        if (flickrId.isEmpty()) {
            onErrorChanged(Constants.INVALID_FLICKR_ID_ERROR);
        }
        else {
            mViewModel.setFlickrId(flickrId);
        }

        observeViewModel();
    }

    /**
     * Observe all of the necessary properties of the view model
     */
    private void observeViewModel() {
        mViewModel.getErrorMessage().observe(this, this::onErrorChanged);
    }

    private void onProgressBarVisibilityChanged(Integer visibility) {
        mProgressBar.setVisibility(visibility);
    }

    /**
     * Called when a new error message is needs to be displayed to the user
     * @param message the error message to diaplsy
     */
    private void onErrorChanged(String message) {
        if (message == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage(message).setPositiveButton("OK", null).show();
    }
}
