package kr.ac.jbnu.se.foodtruckowner.model;


import com.google.gson.annotations.SerializedName;

public class MenuModel {

    // Getter and Setter model for recycler view items
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String title;
    @SerializedName("price")
    private int price;
    @SerializedName("image")
    private MenuUrlModel image;

    public MenuModel(int id, String title,int price, MenuUrlModel image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    public int getPrice(){return price;}
    public String getTitle() {return title;}
    public MenuUrlModel getImage() {
        return image;
    }

    public class MenuUrlModel {
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
