package com.example.appproject.network;

import com.example.appproject.model.Picture;
import com.example.appproject.model.PictureDao;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface PictureService {

    @GET("api.php?type=json")
    Observable<Picture> getPicture();
}
