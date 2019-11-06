package com.thedancercodes.flickrfindr.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thedancercodes.flickrfindr.R;
import com.thedancercodes.flickrfindr.model.FlickrPhotos;
import com.thedancercodes.flickrfindr.viewmodel.MainViewModel;

import java.util.List;

public class FlickrFragment extends Fragment implements FlickrAdapter.IFlickrListListener {

    MainViewModel mViewModel;

    RecyclerView mFlickrList;
    FlickrAdapter mAdapter = new FlickrAdapter();

    boolean mIsLoadingMore = false;

    private View mEmptyListView;
    private ProgressBar mProgressBar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flickrs, container, false);

        mEmptyListView = rootView.findViewById(R.id.view_flickrs_empty);
        mProgressBar = rootView.findViewById(R.id.progress_flickr);
        mFlickrList = rootView.findViewById(R.id.list_flickrs);
        mFlickrList.setVisibility(View.GONE);

        FragmentActivity activity = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        mFlickrList.setLayoutManager(linearLayoutManager);
        mFlickrList.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        // mAdapter.setIsLoadMoreEnabled(true);
        mFlickrList.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        // observeViewModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter.setListener(null);
    }

    @Override
    public void onFlickerItemClicked(String gistId) {
        // Intent flickrIntent = new Intent(getActivity(), FlickrDetailActivity.class);
        // flickrIntent.putExtra(FlickrDetailActivity.KEY_FLICKR_ID, flickrId);
        // startActivity(flickrIntent);
    }

    /**
     * Observe all of the necessary properties of the view model
     */
    protected void observeViewModel(String q) {
        mViewModel.getErrorMessage().observe(this, this::onErrorChanged);
    }

    /**
     * Called when the list of FlickrPhotos has updated and the UI needs to be updated to reflect it
     * @param flickrPhotos the new list of FlickrPhotos
     */
    protected void onFlickrsChanged(List<FlickrPhotos> flickrPhotos) {
        if (flickrPhotos.size() == 0) {
            mEmptyListView.setVisibility(View.VISIBLE);
            mFlickrList.setVisibility(View.GONE);
        }
        else {
            mFlickrList.setVisibility(View.VISIBLE);
            mEmptyListView.setVisibility(View.GONE);
        }
        mProgressBar.setVisibility(View.GONE);
        mAdapter.setFlickrPhotos(flickrPhotos);
        mIsLoadingMore = false;
    }

    /**
     * Called when a new error message is needs to be displayed to the user
     * @param message the error message to diaplsy
     */
    private void onErrorChanged(String message) {
        if (message == null) {
            return;
        }
        mProgressBar.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error").setMessage(message).setPositiveButton("OK", null).show();
    }

}
