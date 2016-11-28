package kr.ac.jbnu.se.foodtruckowner.service;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.model.Item;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Part;

public interface ApiService {
    //로그인 요청
    @FormUrlEncoded
    @POST("/owner/login_request")
    Call<Owner> request_login(@Field("email") String email, @Field("password") String password);

    //회원가입 요청
    @FormUrlEncoded
    @POST("/owner/owner_join")
    Call<Integer> owner_join(@Field("email") String email, @Field("password") String password,
                             @Field("phone_number") String phone_number , @Field("business_number") String business_number);

    @GET("/owner/mytruck_info")
    Call<FoodTruckModel> requestMyTruckInfo(@Query("owner_id") int owner_id);

        //업주 정보 변경 요청
    @GET("owner/changeOwnerInfo")
    Call<Integer> change_owner_info(@Query("modify_info") String modify_info);

        //업주 비밀번호 변경 요청
    @GET("owner/change_password")
    Call<Integer> change_password(@Query("owenr_id") int owenr_id, @Query("password") String password, @Query("password_confirmation") String password_confirmation);

    // ======================= 메뉴 관련 요청 =======================
        //업주가 자신의 메뉴 보려고 요청
    @FormUrlEncoded
    @POST("/owner/truck_menus_owner")
    Call<ArrayList<MenuModel>> truck_menus_owner(@Field("id") int owner_id);

        //메뉴 추가 요청
    @FormUrlEncoded
    @POST("/owner/add_menu")
    Call<Boolean> add_menu(@Field("menu_info") JsonObject menu_info);

        //메뉴 삭제 요청
    @GET("/owner/delete_menu")
    Call<Boolean> delete_menu(@Query("menu_id") int menu_id);

    // ======================= 행사 관련 요청 =======================
        //행사 정보 요청
    @GET("/common/festival_info")
    Call<ArrayList<Item>> festival_info();

        //행사 참가신청 요청
    @GET("/owner/request_festival")
    Call<Boolean> request_festival(@Query("owner_id") int owner_id, @Query("festival_id") int festival_id);

        //행사 참가신청 취소 요청
    @GET("/owner/request_cancle_festival")
    Call<Boolean> request_cancle_festival(@Query("owner_id") int owner_id, @Query("festival_id") int festival_id);

        //내가 참가신청한 행사 정보 요청
    @GET("/owner/selected_festival_info")
    Call<ArrayList<Item>> selected_festival(@Query("onwer_id") int owner_id);

    // ====================== 트럭 정보 ======================
        //푸드트럭 정보 생성 신청
    @Multipart
    @POST("/owner/truck_info_save")
    Call<Boolean> save_truckInfo(@Part MultipartBody.Part file, @Part("truck_info") JsonObject truck_info);

    @GET("/owner/set_open")
    Call<Boolean> set_open(@Query("foodtruck_id") int foodtruck_id);

    @GET("/owner/close_open")
    Call<Boolean> close_open(@Query("foodtruck_id") int foodtruck_id);

    @Multipart
    @POST("/owner/upload_truck_image")
    Call<Boolean> upload_image(@Part MultipartBody.Part file, @Part("foodtruck_id") int foodtruck_id);

    @GET("/owner/change_mytruck_info")
    Call<FoodTruckModel> change_mytruck_info(@Field("modify_info") JsonObject modify_info);

}