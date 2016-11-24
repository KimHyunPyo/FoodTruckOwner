package kr.ac.jbnu.se.foodtruckowner.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.RatingBar;

import kr.ac.jbnu.se.foodtruckowner.adapter.ReviewAdapter;
import kr.ac.jbnu.se.foodtruckowner.model.ReViewItem;
import kr.ac.jbnu.se.foodtruckowner.R;

/**
 * Created by Ratan on 7/29/2015.
 */
public class ReviewFragment extends ListFragment {

    ReviewAdapter adapter ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.review_layout,null);

        adapter = new ReviewAdapter() ;
        setListAdapter(adapter) ;

        // ? ?? ??? ??.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profle),
                "HyunJung94","kingwangjjang" ) ;
        // ? ?? ??? ??.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profle),
                "kwangkwang", "chinjeolBoss") ;
        // ? ?? ??? ??.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profle),
                "Hyunjung's Mom", "My son very like this steak~^^") ;

        return super.onCreateView(inflater, container, savedInstanceState);

    }


    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        // get TextView's Text.
        ReViewItem item = (ReViewItem) l.getItemAtPosition(position) ;

        String titleStr = item.getTitle() ;
        String descStr = item.getDesc() ;
        Drawable iconDrawable = item.getIcon() ;
        //RatingBar starRat = item.getStar();

        // TODO : use item data.
    }

    public void addItem(Drawable icon, String title, String desc , RatingBar star) {
        adapter.addItem(icon, title, desc) ;
    }


}
