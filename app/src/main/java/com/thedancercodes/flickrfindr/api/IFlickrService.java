package com.thedancercodes.flickrfindr.api;

import com.thedancercodes.flickrfindr.model.MainFlickrModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Define all API methods in this interface
 */
public interface IFlickrService {

    // Method to return images using a search term
    @GET("services/rest/?method=flickr.photos.search&per_page=25&nojsoncallback=1&format=json&extras=url_s")
    Call<MainFlickrModel> searchFlickrPhotos(
            @Query("api_key") String apiKey,
            @Query("text") String text
    );
}
