package com.thedancercodes.flickrfindr.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thedancercodes.flickrfindr.R;
import com.thedancercodes.flickrfindr.model.FlickrPhotos;

import java.util.ArrayList;
import java.util.List;

public class FlickrAdapter extends RecyclerView.Adapter
        implements FlickrViewHolder.IFlickrViewHolderListener {

    private List<FlickrPhotos> mFlickrPhotos;
    private IFlickrListListener mListener;
    private boolean mIsLoadMoreEnabled = false;

    @Override
    public int getItemViewType(int position) {
        if (position >= mFlickrPhotos.size()) {
            return R.layout.item_load_more;
        }

        return R.layout.item_photo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.item_photo:
                return new FlickrViewHolder(view, this);
            default:
                return new LoadMoreViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FlickrViewHolder) {
            FlickrPhotos flickrPhotos = mFlickrPhotos.get(position);
            FlickrViewHolder flickrViewHolder = (FlickrViewHolder) holder;
            flickrViewHolder.configureView(flickrPhotos);
        }
    }

    @Override
    public int getItemCount() {
        if (mFlickrPhotos == null) {
            return 0;
        }

        int itemCount = mFlickrPhotos.size();
        //Add one to the item count if loading more is enabled and there is already data in the list
        if (itemCount > 0 && mIsLoadMoreEnabled) {
            itemCount++;
        }
        return itemCount;
    }

    /***
     * Update the data set driving this adapter
     * @param flickrPhotos the new list of gists to use
     */
    public void setFlickrPhotos(List<FlickrPhotos> flickrPhotos) {
        //When the new list is NULL, use a blank list
        if (flickrPhotos == null) {
            mFlickrPhotos = new ArrayList<>();
            notifyDataSetChanged();
            return;
        }
        //Get the current size of the list
        int oldSize = mFlickrPhotos != null ? mFlickrPhotos.size() : 0;
        //Get the size of the new list
        int newSize = flickrPhotos.size();
        //Update the gist list
        mFlickrPhotos = new ArrayList<>(flickrPhotos);
        if (oldSize <= 0) {
            //When the size of the old list was 0, refresh the whole list
            notifyDataSetChanged();
        }
        else if (newSize > oldSize) {
            //When the size of the new list is greater than the old one, insert all new items
            notifyItemRangeInserted(oldSize, newSize - oldSize);
        }
        else {
            notifyDataSetChanged();
        }
    }

    /***
     * Set or clear the interface listening to Gist clicks from this adapter
     * @param mListener the interface listening or NULL to clear it
     */
    void setListener(IFlickrListListener mListener) {
        this.mListener = mListener;
    }

    /***
     * Used to determine if loading more when the list is scrolled to the bottom is enabled
     * @return TRUE if loading more is enabled, FALSE if not
     */
    boolean isLoadMoreEnabled() {
        return mIsLoadMoreEnabled;
    }

    /***
     * Set whether loading more when the list is scrolled to the bottom is enabled or not
     * @param isLoadMoreEnabled TRUE if loading more is enabled, FALSE if not
     */
    void setIsLoadMoreEnabled(boolean isLoadMoreEnabled) {
        this.mIsLoadMoreEnabled = isLoadMoreEnabled;
    }

    @Override
    public void onFlickerItemClicked(int position) {

        if (mListener == null) {
            return;
        }

        //Tell the listener that a gist was clicked
        mListener.onFlickerItemClicked(mFlickrPhotos.get(position).getId());
    }

    public interface IFlickrListListener {

        /***
         * Called when a Gist is clicked
         * @param gistId the ID of the Gist that was clicked
         */
        void onFlickerItemClicked(String gistId);
    }
}
