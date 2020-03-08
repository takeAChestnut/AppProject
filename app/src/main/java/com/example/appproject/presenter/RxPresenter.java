package com.example.appproject.presenter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Observer;

import com.example.appproject.model.DbController;
import com.example.appproject.model.Picture;
import com.example.appproject.model.PictureDao;
import com.example.appproject.network.NetWorkManager;
import com.example.appproject.network.PictureService;
import com.example.appproject.view.PictureRecyclerViewAdapter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RxPresenter {

    private static final String TAG = "RxPresenter";

    private Context mContext;

    private CompositeDisposable mDisposable;

    /**
     * Database Dao
     */
    private PictureDao mPictureDao;

    /**
     * Picture observer
     */
    private Observable<Picture> mObservable;
    private ObservableEmitter<Picture> mPictureEmitter;
    private PictureListener mListener;

    public RxPresenter(Context context) {
        mContext = context;
        mDisposable = new CompositeDisposable();
        mPictureDao = DbController.getInstance().getPictureDao();
    }

    public void requestPicture() {
        Retrofit retrofit = NetWorkManager.getInstance().getRetrofit();
        PictureService pictureService = retrofit.create(PictureService.class);
        /*Call<ResponseBody> call = pictureService.getPicture();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    Log.d(TAG, "onResponse: " + str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/
        for (int i = 0; i < 5; i++) {
            Disposable disposable = pictureService.getPicture()
                    .subscribeOn(Schedulers.single())
                    .subscribe(new Consumer<Picture>() {
                        @Override
                        public void accept(Picture picture) throws Exception {
                            //Log.d(TAG, "accept: " + picture);
                            mPictureEmitter.onNext(picture);
                        }
                    });
            mDisposable.add(disposable);
        }
    }

    public void destroy() {
        mDisposable.clear();
    }

    public void subscribePicture() {
         Observable<List<Picture>> newPic = mObservable.create(new ObservableOnSubscribe<Picture>() {

            @Override
            public void subscribe(ObservableEmitter<Picture> emitter) throws Exception {
                mPictureEmitter = emitter;
            }
        }).buffer(5);

        Disposable disposable1 = newPic.subscribe(new Consumer<List<Picture>>() {
            @Override
            public void accept(List<Picture> pictures) throws Exception {
                Log.d(TAG, "accept: " + pictures.size());
                mListener.onPictureDataUpdated(pictures);
                savePictureInfo(pictures);
            }
        });

        mDisposable.add(disposable1);
    }

    private void savePictureInfo(List<Picture> picture) {
        mPictureDao.insertOrReplaceInTx(picture);
        long count = mPictureDao.queryBuilder().count();
        Log.d(TAG, "savePictureInfo: count = " + count);
    }

    public List<Picture> getPictures() {
        List<Picture> pictures = mPictureDao.queryBuilder().list();
        return pictures;
    }

    public void setListener(PictureListener listener) {
        mListener = listener;
    }

    public interface PictureListener {
        void onPictureDataUpdated(List list);
    }
}
