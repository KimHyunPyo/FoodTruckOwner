package kr.ac.jbnu.se.foodtruckowner.model;

/**
 * Created by hyunjung on 2016-11-25.
 */

public class CommentsModel {

    // Getter and Setter model for recycler view items
    private String titleTextView;
    private int image;

    public String getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(String titleTextView) {
        this.titleTextView = titleTextView;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}