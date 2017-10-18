package com.linhlee.vidientu.retrofit;

import com.linhlee.vidientu.models.BalanceRequest;
import com.linhlee.vidientu.models.BannerRequest;
import com.linhlee.vidientu.models.CardRequest;
import com.linhlee.vidientu.models.FullNameRequest;
import com.linhlee.vidientu.models.NewsRequest;
import com.linhlee.vidientu.models.OtherRequest;
import com.linhlee.vidientu.models.PageRequest;
import com.linhlee.vidientu.models.TransactionRequest;
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

    //Lựa chọn chế độ ODP hoặc không chọn
    @POST("/rest/api/chooseODP")
    Call<OtherRequest> chooseODP(@Header("token") String token, @Body HashMap<String, Object> body);

    //Đổi mật khẩu 1
    @POST("/rest/api/changeMK1")
    Call<OtherRequest> changePass1(@Header("token") String token, @Body HashMap<String, Object> body);

    //Đổi mật khẩu 2
    @POST("/rest/api/changeMK2")
    Call<OtherRequest> changePass2(@Header("token") String token, @Body HashMap<String, Object> body);

    //Đổi thông tin cá nhân
    @POST("/rest/api/changeProfile")
    Call<UserRequest> changeProfile(@Header("token") String token, @Body HashMap<String, Object> body);

    //Lấy số dư hiện tại của khách hàng
    @POST("/rest/api/getBalance")
    Call<BalanceRequest> getBalance(@Header("token") String token);

    //Nạp thẻ cào thành tiền
    @POST("/rest/api/cardCharge")
    Call<OtherRequest> cardCharge(@Header("token") String token, @Body HashMap<String, Object> body);

    //Chuyển tiền cho tài khoản khác
    @POST("/rest/api/transferTcsr")
    Call<OtherRequest> transferMoney(@Header("token") String token, @Body HashMap<String, Object> body);

    //Mua thẻ cào, thẻ game
    @POST("/rest/api/buyCard")
    Call<OtherRequest> buyCard(@Header("token") String token, @Body HashMap<String, Object> body);

    //Nạp trực tiếp tiền cho di động bằng tiền trên thecaosieure
    @POST("/rest/api/topupMobile")
    Call<OtherRequest> topupMobile(@Header("token") String token, @Body HashMap<String, Object> body);

    //Lấy thông tin tên ngân hàng hoặc tên loại thẻ cho các chức năng rút tiền và các chức năng mua thẻ game điện thoại
    @POST("/rest/api/getCardInfo")
    Call<CardRequest> getCardInfo(@Body HashMap<String, Object> body);

    //Lấy thông tin chiết khấu nạp thẻ vào ví
    @POST("/rest/api/getCardDPT")
    Call<CardRequest> getCardDPT();

    //Lấy DS số ATM đã được lưu trên hệ thống của khách hàng
    @POST("/rest/api/getPaysave")
    Call<OtherRequest> getPaySave(@Header("token") String token, @Body HashMap<String, Object> body);

    //Rút tiền về số tài khoản ngân hàng
    @POST("/rest/api/postAccountbank")
    Call<OtherRequest> postAccountBank(@Header("token") String token, @Body HashMap<String, Object> body);

    //Rút tiền về số ATM ngân hàng
    @POST("/rest/api/postATMBank")
    Call<OtherRequest> postATMBank(@Header("token") String token, @Body HashMap<String, Object> body);

    //Rút tiền theo số chứng minh thư và địa chỉ cmt
    @POST("/rest/api/postCMTdraw")
    Call<OtherRequest> postCMTdraw(@Header("token") String token, @Body HashMap<String, Object> body);

    //Lấy nội dung hiển thị theo tên chức năng
    @POST("/rest/api/getContentbyname")
    Call<PageRequest> getContentByName(@Body HashMap<String, Object> body);

    //Lấy Danh sách lịch sử giao dịch của khách hàng
    @POST("/rest/api/getAllTransaction")
    Call<TransactionRequest> getAllTransaction(@Header("token") String token, @Body HashMap<String, Object> body);

    //Đăng xuất
    @POST("/rest/api/LogoutTcsr")
    Call<OtherRequest> logout(@Header("token") String token);

    //Thanh toán hợp đồng trả góp
    @POST("/rest/api/postSaleHD")
    Call<OtherRequest> postSaleHD(@Header("token") String token, @Body HashMap<String, Object> body);

    //Lấy ảnh top banner ở trang chủ
    @POST("/rest/api/getBanner")
    Call<BannerRequest> getBanner();

    //Lấy danh sách tin tức
    @POST("/rest/api/getArticle")
    Call<NewsRequest> getArticle();

    //Lấy fullname theo tên tài khoản
    @POST("/rest/api/getFullnameUser")
    Call<FullNameRequest> getFullName(@Body HashMap<String, Object> body);
}
