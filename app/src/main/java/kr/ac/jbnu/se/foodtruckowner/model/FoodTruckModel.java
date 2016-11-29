package kr.ac.jbnu.se.foodtruckowner.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by son on 11/1/16.
 */
public class FoodTruckModel {
    public volatile static FoodTruckModel uniqueInstance;
    private FoodTruckModel() { }

    public static FoodTruckModel getInstance() {
        if(uniqueInstance == null) { //있는지 체크 없으면
            uniqueInstance = new FoodTruckModel(); //생성한뒤
        }
        return uniqueInstance;
    }

    @SerializedName("id")
    private int FT_ID;
    @SerializedName("owner_id")
    private int FT_OWNER_ID;
    @SerializedName("name")
    private String FT_NAME;
    @SerializedName("category")
    private int FT_CATEGORY;
    @SerializedName("tag")
    private String FT_TAG;
    @SerializedName("rating")
    private float FT_RATING;
    @SerializedName("open")
    private Boolean FT_START;
    @SerializedName("payment_card")
    private Boolean FT_PAYMENT;
    @SerializedName("image")
    private FoodTruckUrlModel FT_IMAGE_URL;
    @SerializedName("lat")
    private Double FT_LAT; //위도
    @SerializedName("lng")
    private Double FT_LNG; //경도

    private int FT_LIKE = 0;
    private String FT_LOCATIONNAME;

    public int getFT_ID() {return FT_ID;}

    public void setFT_ID(int FT_ID) {this.FT_ID = FT_ID;}

    public int getFtOwnerId() {
        return FT_OWNER_ID;
    }

    public void setFtOwnerId(int ftOwnerId) {
        FT_OWNER_ID = ftOwnerId;
    }

    public String getFtName() {
        return FT_NAME;
    }

    public void setFtName(String ftName) {
        FT_NAME = ftName;
    }

    public int getFtCategory() {
        return FT_CATEGORY;
    }

    public void setFtCategory(int ftCategory) {
        FT_CATEGORY = ftCategory;
    }

    public String getFtTaag() {
        return FT_TAG;
    }

    public void setFtTaag(String ftTaag) {
        FT_TAG = ftTaag;
    }

    public float getFtRating() {
        return FT_RATING;
    }

    public void setFtRating(float ftRating) {
        FT_RATING = ftRating;
    }

    public int getFtLike() {
        return FT_LIKE;
    }

    public void setFtLike(int ftLike) {
        FT_LIKE = ftLike;
    }

    public Boolean getFtStart() {
        return FT_START;
    }

    public void setFtStart(Boolean ftStart) {
        FT_START = ftStart;
    }

    public Boolean getFtPayment() {
        return FT_PAYMENT;
    }

    public void setFtPayment(Boolean ftPayment) {
        FT_PAYMENT = ftPayment;
    }

    public String getFT_LOCATIONNAME() {
        return FT_LOCATIONNAME;
    }

    public void setFT_LOCATIONNAME(String FT_LOCATIONNAME) {
        this.FT_LOCATIONNAME = FT_LOCATIONNAME;
    }

    public String getFT_IMAGE_URL() {
        return FT_IMAGE_URL.getUrl();
    }

    public void setFT_IMAGE_URL(FoodTruckUrlModel FT_IMAGE_URL) {
        this.FT_IMAGE_URL = FT_IMAGE_URL;
    }

    public Double getFT_LAT() {
        return FT_LAT;
    }

    public void setFT_LAT(Double FT_LAT) {
        this.FT_LAT = FT_LAT;
    }

    public Double getFT_LNG() {
        return FT_LNG;
    }

    public void setFT_LNG(Double FT_LNG) {
        this.FT_LNG = FT_LNG;
    }

    public class FoodTruckUrlModel {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
