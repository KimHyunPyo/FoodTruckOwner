package kr.ac.jbnu.se.foodtruckowner.model;


public class MenuModel {

    // Getter and Setter model for recycler view items
    private String title;
    private int image;
    private int price;

    public MenuModel(String title,int price, int image) {

        this.title = title;
        this.price = price;
        this.image = image;
    }

    public int getPrice(){return price;}
    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }
}
