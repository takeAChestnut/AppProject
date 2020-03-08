package com.example.appproject.view;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appproject.R;
import com.example.appproject.model.Picture;
import com.example.appproject.view.PictureFragment.OnListFragmentInteractionListener;
import com.example.appproject.view.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PictureRecyclerViewAdapter extends RecyclerView.Adapter<PictureRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private final List<Picture> mList;
    private final OnListFragmentInteractionListener mListener;

    public PictureRecyclerViewAdapter(Context context, List<Picture> items, OnListFragmentInteractionListener listener) {
        mContext = context;
        mList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_picture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picture picture = mList.get(position);
        holder.mItem = picture;
        holder.mIdView.setText(String.valueOf(picture.getId()));
        holder.mContentView.setText("美图-" + (position + 1));
        Glide.with(mContext)
                .load(picture.getImgurl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateDataSet(List<Picture> pictures) {
        int beforeSize = mList.size();
        mList.addAll(pictures);
        //notifyItemRangeChanged(beforeSize, pictures.size());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Picture mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.item_pic);
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
