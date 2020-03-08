package com.example.appproject.model;

import android.content.Context;

import com.example.appproject.RxApplication;

public class DbController {

    private static final String TAG = "DbController";
    private static final String DB_NAME = "resource.db";

    private static DbController mInstance;

    private DaoMaster.DevOpenHelper mDevOpenHelper;

    private DaoMaster mDaoMaster;

    private DaoSession mDaoSession;

    private PictureDao mPictureDao;

    public static DbController getInstance() {
        if (mInstance == null) {
            synchronized(DbController.class) {
                if (mInstance == null) {
                    mInstance = new DbController();
                }
            }
        }
        return mInstance;
    }

    private DbController() {
        Context context = RxApplication.getContext();
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        mDaoMaster = new DaoMaster(mDevOpenHelper.getWritableDb());
        mDaoSession = mDaoMaster.newSession();
        mPictureDao = mDaoSession.getPictureDao();
    }

    public PictureDao getPictureDao() {
        return mPictureDao;
    }
}
