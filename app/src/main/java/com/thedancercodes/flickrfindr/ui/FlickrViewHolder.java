package com.thedancercodes.flickrfindr.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thedancercodes.flickrfindr.R;
import com.thedancercodes.flickrfindr.model.FlickrPhotos;

import java.text.DateFormat;
import java.util.Map;

class FlickrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mFlickrThumbnail;
    private TextView mFlickrTitle;
    private TextView mFlickrUrl;
    private TextView mUpdatedText;

    private IFlickrViewHolderListener mListener;

    FlickrViewHolder(@NonNull View itemView, IFlickrViewHolderListener listener) {
        super(itemView);

        mListener = listener;

        mFlickrThumbnail = itemView.findViewById(R.id.photo_thumbnail);
        mFlickrTitle = itemView.findViewById(R.id.text_flickr_title);
        mFlickrUrl = itemView.findViewById(R.id.text_flickr_url);
        // mUpdatedText = itemView.findViewById(R.id.text_gist_updated);
        itemView.setOnClickListener(this);
    }

    /***
     * Configure the view according to the FlickrPhotos info provided
     * @param flickrPhotos the Gist info to display in this view
     */
    void configureView(FlickrPhotos flickrPhotos) {

        //Set the Flickr Title
        if (flickrPhotos != null) {
            mFlickrTitle.setText(flickrPhotos.getTitle());
        }
        else {
            mFlickrTitle.setText("ERROR - NO TITLE");
        }
        //Set the URL description
        mFlickrUrl.setText(flickrPhotos.getUrlS());

        //Set the author avatar image
        RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_thumbnail_placeholder);
        Glide.with(mFlickrThumbnail)
                .load(flickrPhotos.getUrlS())
                .apply(options)
                    .into(mFlickrThumbnail);
        }

    @Override
    public void onClick(View v) {
        if (mListener == null) {
            return;
        }

        //Tell the listener that the gist was clicked
        mListener.onFlickerItemClicked(getAdapterPosition());
    }

    interface IFlickrViewHolderListener {

        /***
         * Called when a Flickr item is clicked
         * @param position the position of the Flickr item that was clicked
         */
        void onFlickerItemClicked(int position);
    }
}
