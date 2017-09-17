package com.fine.thistimeitsgonnabefine;

/**
 * Created by Martyna on 16.09.2017.
 */

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Martyna on 16.09.2017.
 */
public interface MyApi {

    //@Multipart
    @POST("api/hackzurich/v1/attributes/")
    Call<MyResponse> call(@Body RequestBody body, @HeaderMap() HashMap<String,String> map);
    //  Call<MyResponse> call(@Part("file\"; fileName=\"myFile.png\" ")RequestBody body, @HeaderMap() HashMap<String,String> map);
}
