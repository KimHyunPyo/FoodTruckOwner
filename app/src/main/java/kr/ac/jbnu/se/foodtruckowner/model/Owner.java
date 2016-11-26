package kr.ac.jbnu.se.foodtruckowner.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 2016-11-02.
 */
public class Owner implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("business_number")
    private String business_number;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBusiness_number() {
        return business_number;
    }

    public void setBusiness_number(String business_number) {
        this.business_number = business_number;
    }


}

