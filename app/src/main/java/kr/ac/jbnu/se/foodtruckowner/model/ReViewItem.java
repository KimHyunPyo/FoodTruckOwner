package kr.ac.jbnu.se.foodtruckowner.model;

import android.graphics.drawable.Drawable;
import android.widget.RatingBar;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by hyunjung on 2016-11-01.
 */

public class ReviewItem {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("rating")
    private float rating;
    @SerializedName("image")
    private Image image;
    @SerializedName("client")
    private Client client;

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setRating(float rating) { this.rating = rating; }
    public void setImage(Image image) { this.image = image; }
    public void setClient(Client client) { this.client = client; }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public float getRating() { return rating; }
    public String getImage() { return image.getUrl(); }
    public Client getClient() { return client; }

    public String getClientNickname() { return (getClient().getNickName()); }
    public String getClientImage() { return (getClient().getImage().getUrl()); }

    private class Client {
        @SerializedName("nickName")
        private String nickName;
        @SerializedName("image")
        private Image image;

        public Client(String nickName, Image image) {
            this.nickName = nickName;
            this.image = image;
        }

        public void setNickName(String nickName) { this.nickName = nickName; }
        public void setImage(Image image) { this.image = image; }

        public String getNickName() { return nickName; }
        public Image getImage() { return image; }
    }

}

class Image{
    @SerializedName("url")
    private String url;

    public Image(String url){
        this.url = url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}

