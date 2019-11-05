package com.thedancercodes.flickrfindr.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thedancercodes.flickrfindr.api.IFlickrService;
import com.thedancercodes.flickrfindr.model.FlickrPhotos;
import com.thedancercodes.flickrfindr.model.FlickrPhotosResponse;
import com.thedancercodes.flickrfindr.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    //private MutableLiveData<List<FlickrPhotos>> flickrPhotos = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgressBarVisibility = new MutableLiveData<>();
    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();

    private int photosPagesLoaded = 0;

    private IFlickrService flickrService;

    public MainViewModel() {
        super();
        init();
        initService();
    }


    /***
     * Clean and initialize the state of the view and all related counters and flags
     */
    private void init() {
        mErrorMessage.setValue(null);
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

    /**
     * Search for Flickr photos using a search term
     */
    public void searchFlickrPhotos() {
        flickrService.searchFlickrPhotos(
                "1508443e49213ff84d566777dc211f2a", "sunset")
                .enqueue(new Callback<FlickrPhotosResponse>() {
                    @Override
                    public void onResponse(Call<FlickrPhotosResponse> call, Response<FlickrPhotosResponse> response) {

                        if (!response.isSuccessful()) {
                            showError(response.message());
                        }

                        if (response.body() != null) {

                        }
                    }

                    @Override
                    public void onFailure(Call<FlickrPhotosResponse> call, Throwable t) {

                    }
                });
    }

    /**
     * Convenience method for showing an error to the user
     *
     * @param message the message to show to the user
     */
    private void showError(String message) {
        mErrorMessage.postValue(message);
    }
}
