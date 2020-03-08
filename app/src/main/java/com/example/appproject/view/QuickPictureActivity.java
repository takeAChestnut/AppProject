package com.example.appproject.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appproject.R;
import com.example.appproject.model.DbController;
import com.example.appproject.model.Picture;
import com.example.appproject.model.PictureDao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuickPictureActivity extends AppCompatActivity {

    private static final String TAG = "QuickPictureActivity";
    public static final String PICTURE_ID = "picture_id";

    protected ImageView mImageViewOne;
    protected ImageView mImageViewTwoe;
    protected ImageView mImageViewThree;
    @BindView(R.id.view_page)
    protected ViewPager mViewPager;

    private long mId;
    private Picture mPicture;
    private PictureDao mPictureDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_picture);

        Intent intent = getIntent();
        mId = intent.getLongExtra(PICTURE_ID, -1);

        ButterKnife.bind(this);
        mPictureDao = DbController.getInstance().getPictureDao();

        initView();
        if (mId >= 0) {
            displayPicture(mId);
        }
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.)
        mViewPager.addView(mImageViewOne);
        mViewPager.addView(mImageViewTwoe);
        mViewPager.addView(mImageViewThree);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void displayPicture(long id) {
        mPicture = mPictureDao.loadByRowId(id);
        if (mPicture != null) {
            Glide.with(this)
                    .load(mPicture.getImgurl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageViewOne);
        }
    }
}
