package kr.ac.jbnu.se.foodtruckowner.service;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.model.MenuModel;
import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

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

    //업주가 자신의 메뉴 보려고 요청
    @FormUrlEncoded
    @POST("/owner/truck_menus_owner")
    Call<ArrayList<MenuModel>> truck_menus_owner(@Field("id") int owner_id);

    //업주 메뉴 추가 요청
    @FormUrlEncoded
    @POST("/owner/add_menu")
    Call<Boolean> add_menu(@Field("menu_info") JsonObject menu_info);


}