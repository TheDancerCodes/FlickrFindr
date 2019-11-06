package com.thedancercodes.flickrfindr.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thedancercodes.flickrfindr.api.IFlickrService;
import com.thedancercodes.flickrfindr.model.FlickrPhotos;
import com.thedancercodes.flickrfindr.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrDetailViewModel extends ViewModel {
    private MutableLiveData<FlickrPhotos> flickrPhoto;
    private MutableLiveData<Integer> mProgressBarVisibility = new MutableLiveData<>();
    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();
    private String mFlickrId;

    private IFlickrService flickrService;

    public FlickrDetailViewModel() {
        super();
        init();
        initService();
    }

    /***
     * Clean and initialize the state of the view and all related counters and flags
     */
    private void init() {

        mProgressBarVisibility.setValue(View.GONE);
        mErrorMessage.setValue(null);
        flickrPhoto = null;
    }

    /***
     * Configure a new Retrofit instance for future API calls with no authorization
     */
    private void initService() {

        // Create a new Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.FLICKR_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        flickrService = retrofit.create(IFlickrService.class);
    }

    public LiveData<Integer> getProgressBarVisibility() {
        return mProgressBarVisibility;
    }

    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }

    /**
     * Set the ID of the Flickr object to use
     * @param flickrId the ID of the Flickr object
     */
    public void setFlickrId(String flickrId) {
        this.mFlickrId = flickrId;
    }

    /**
     * Get an observable instance of the FlickrPhoto.  This will also load the FlickrPhoto from the API
     *  if one has not been loaded yet.
     * @return an observable FlickrPhoto
     * @see LiveData
     */
    public LiveData<FlickrPhotos> getFlickrPhoto() {
        if (flickrPhoto == null) {
            flickrPhoto = new MutableLiveData<>();
            loadFlickrPhoto();
        }
        return flickrPhoto;
    }

    /**
     * Download a FlickrPhoto from the GitHub API
     */
    private void loadFlickrPhoto() {

        // Make sure that a FlickrPhoto ID was stored for use
        if (mFlickrId.isEmpty()) {
            showError(Constants.INVALID_FLICKR_ID_ERROR);
            return;
        }

        // Show the progress bar
        mProgressBarVisibility.postValue(View.VISIBLE);


    }

    /**
     * Convenience method for showing an error to the user
     *
     * @param message the message to show to the user
     */
    private void showError(String message) {
        mProgressBarVisibility.postValue(View.GONE);
        mErrorMessage.postValue(message);
    }
}
