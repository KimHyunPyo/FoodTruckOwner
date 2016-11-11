package kr.ac.jbnu.se.foodtruckowner;

import android.graphics.drawable.Drawable;
import android.widget.RatingBar;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by hyunjung on 2016-11-01.
 */

public class ListViewItem {

    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private RatingBar starRat;
   // String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setStar(RatingBar star){ starRat = star ;}

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public RatingBar getStar() { return starRat; }


}
