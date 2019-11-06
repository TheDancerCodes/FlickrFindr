package com.thedancercodes.flickrfindr.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thedancercodes.flickrfindr.R;
import com.thedancercodes.flickrfindr.model.FlickrPhotos;

import java.util.List;

public class FlickrListFragment extends FlickrFragment {

    public static FlickrListFragment newInstance() {
        return new FlickrListFragment();
    }

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        progressBar = rootView.findViewById(R.id.progress_flickr);


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search_item);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    mViewModel.searchFlickrPhotos(query);

                    observeViewModel(query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void observeViewModel(String q) {
        super.observeViewModel(q);
        mIsLoadingMore = true;
        mViewModel.getFlickrPhotos(q).observe(this, this::onFlickrsChanged);
        mViewModel.getProgressBarVisibility().observe(this, visibility -> progressBar.setVisibility(visibility));
    }

    @Override
    protected void onFlickrsChanged(List<FlickrPhotos> flickrPhotos) {
        mAdapter.setIsLoadMoreEnabled(mViewModel.isMoreSearchFlickrPhotosAvailable());
        super.onFlickrsChanged(flickrPhotos);
    }
}
