package com.thedancercodes.flickrfindr.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thedancercodes.flickrfindr.model.FlickrPhotos;

import java.util.List;

public class FlickrListFragment extends FlickrFragment {

    public static FlickrListFragment newInstance() {
        return new FlickrListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mFlickrList.getLayoutManager();

        if (linearLayoutManager != null) {

            //Add a scroll listener to trigger a call to load more when the user reaches the bottom
            // of the list
            mFlickrList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //Prevent any calls to update when the list has it disabled
                    if (!mAdapter.isLoadMoreEnabled()) {
                        return;
                    }

                    //Prevent any calls to update if the list is empty
                    int totalItemCount = linearLayoutManager.getItemCount();
                    if (totalItemCount <= 0) {
                        return;
                    }
                    //Check to see if the last visible item is the last item in the list
                    int lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (!mIsLoadingMore && lastVisibleItem >= totalItemCount - 1) {
                        mIsLoadingMore = true;
                        mViewModel.searchFlickrPhotos();
                    }
                }
            });
        }

        return rootView;
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        mIsLoadingMore = true;
        mViewModel.getFlickrPhotos().observe(this, this::onFlickrsChanged);
    }

    @Override
    protected void onFlickrsChanged(List<FlickrPhotos> flickrPhotos) {
        mAdapter.setIsLoadMoreEnabled(mViewModel.isMoreSearchFlickrPhotosAvailable());
        super.onFlickrsChanged(flickrPhotos);
    }
}
