package com.ghostcreations.androidloginreg;

import com.ghostcreations.androidloginreg.models.ServerRequest;
import com.ghostcreations.androidloginreg.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Ghost on 7/29/2016.
 */
public interface RegisterInterface {

    @POST("register/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}

