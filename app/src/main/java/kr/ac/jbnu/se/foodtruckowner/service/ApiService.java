package kr.ac.jbnu.se.foodtruckowner.service;

import kr.ac.jbnu.se.foodtruckowner.model.Owner;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {
    //public static final String API_URL = "https://server-blackdog11.c9users.io/";
    //로그인 요청
    @GET("/owner/login_request")
    Call<Owner> request_login(@Query("email") String email, @Query("password") String password);


//    //로그인 요청
//    @FormUrlEncoded
//    @POST("/client/login_request")
//    Call<UserModel> request_login(@Field("email") String email, @Field("password") String password);
//
//    @GET("/client/client_join")
//    Call<Integer> client_join(@Query("email") String email, @Query("password") String password, @Query("nickName") String nickname);
//
//    //FragmentHome에서 푸드트럭 리스트 요청
//    @FormUrlEncoded
//    @POST("/client/foodtruck_list")
//    Call<ArrayList<FoodTruckModel>> listFoodTrucks(@Field("category") int category);
//    //@GET("/client/foodtruck_list")
//    //Call<ArrayList<FoodTruckModel>> listFoodTrucks(@Query("category") int category);


}