package com.linhlee.vidientu.retrofit;

import com.linhlee.vidientu.models.OtherRequest;
import com.linhlee.vidientu.models.UserRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Linh Lee on 6/18/2017.
 */
public interface IRetrofitAPI {

    //Đăng nhập
    @POST("/rest/api/LoginTcsr")
    Call<UserRequest> login(@Body HashMap<String, Object> body);

    //Kiểm tra mã đăng nhập ODP
    @POST("/rest/api/checkODP")
    Call<OtherRequest> checkODP(@Header("token") String token, @Body HashMap<String, Object> body);

    //Gửi lại mã đăng nhập ODP
    @POST("/rest/api/resentODP")
    Call<OtherRequest> resendODP(@Header("token") String token);

    //Lấy thông tin khách hàng
    @POST("/rest/api/getUserInfo")
    Call<UserRequest> getUserInfo(@Header("token") String token);

    //Đổi mật khẩu 1
    @POST("/rest/api/changeMK1")
    Call<OtherRequest> changePass1(@Header("token") String token, @Body HashMap<String, Object> body);

    //Đổi mật khẩu 2
    @POST("/rest/api/changeMK2")
    Call<OtherRequest> changePass2(@Header("token") String token, @Body HashMap<String, Object> body);

    //Đổi thông tin cá nhân
    @POST("/rest/api/changeProfile")
    Call<UserRequest> changeProfile(@Header("token") String token, @Body HashMap<String, Object> body);
}
