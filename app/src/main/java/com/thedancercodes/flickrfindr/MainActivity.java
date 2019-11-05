package com.thedancercodes.flickrfindr;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.thedancercodes.flickrfindr.api.IFlickrService;
import com.thedancercodes.flickrfindr.model.FlickrPhotos;
import com.thedancercodes.flickrfindr.model.FlickrPhotosResponse;
import com.thedancercodes.flickrfindr.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private IFlickrService flickrService;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initService();

        flickrService.searchFlickrPhotos(
                "10d3dff04ffac1932a8672f5e39a7727", "sunset")
                .enqueue(new Callback<FlickrPhotosResponse>() {
                    @Override
                    public void onResponse(Call<FlickrPhotosResponse> call, Response<FlickrPhotosResponse> response) {

                        if (!response.isSuccessful()) {
                            Log.d(TAG, "Response not Successful");
                        }

                        if (response.body() != null) {
                            // flickrPhotos.postValue(response.body());
                            String LOG = String.valueOf(response.body());
                            Log.i(TAG, "Got response with status code " +
                                    response.code() +  " and message " +
                                    response.message());
                            // val body = response?.body()
                            Log.i(TAG, "Response body = " +  response.body().getTotal());
                        }
                    }

                    @Override
                    public void onFailure(Call<FlickrPhotosResponse> call, Throwable t) {
                        Log.i(TAG, "Call to " + call.request().url() +
                                "failed with " + t.toString());
                    }
                });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
