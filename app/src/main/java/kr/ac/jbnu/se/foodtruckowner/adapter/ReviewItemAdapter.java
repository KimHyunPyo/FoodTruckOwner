package kr.ac.jbnu.se.foodtruckowner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.ReViewItem;
import kr.ac.jbnu.se.foodtruckowner.ui.CommentsActivity;

/**
 * Created by hyunjung on 2016-11-26.
 */

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ReviewViewHolder>  {

public static ArrayList<ReViewItem> reviewitems;
private Context mContext = null;
public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";


public ReviewItemAdapter(Context c, ArrayList<ReViewItem> listitems) {
        this.mContext = c;
        this.reviewitems = listitems;
        }

@Override
public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(v);
        return reviewViewHolder;

        }

@Override
public void onBindViewHolder(final ReviewViewHolder holder, final int position) {

        holder.ivFeedCenter.setImageResource(reviewitems.get(position).getCenterimage());
        holder.ivFeedBottom.setText(reviewitems.get(position).getReviewText());
        holder.userImageView.setImageResource(reviewitems.get(position).getUserImage());
        holder.userImageView.setTag(reviewitems.get(position).getUserImage());
        holder.userTextView.setText(reviewitems.get(position).getUserText());
        holder.tsLikesCounter.setCurrentText(holder.vImageRoot.getResources().getQuantityString(
        R.plurals.likes_count, reviewitems.get(position).getLikesCount(), reviewitems.get(position).getLikesCount()
        ));

        holder.btnComments.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        }
        });
        holder.ivFeedCenter.setOnClickListener(new View.OnClickListener() {
/*@Override
public void onClick(View v) {
        reviewitems.get(position).setLikesCount(reviewitems.get(position).isLiked() ?
        reviewitems.get(position).getLikesCount()-1 : reviewitems.get(position).getLikesCount()+1);
        reviewitems.get(position).setLiked(reviewitems.get(position).isLiked() ? false : true);
        notifyItemChanged(position, ACTION_LIKE_BUTTON_CLICKED);
        if(!reviewitems.get(position).isLiked()){
        if (mContext instanceof AcitivityTruckDetail) {
        ((AcitivityTruckDetail) mContext).showLikedSnackbar();
        }
        else if(mContext instanceof AcitivityTruckReview){
        ((AcitivityTruckReview) mContext).showLikedSnackbar();
        }
        }
        }
        });
        holder.likebutton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        reviewitems.get(position).setLikesCount(reviewitems.get(position).isLiked() ?
        reviewitems.get(position).getLikesCount()-1 : reviewitems.get(position).getLikesCount()+1);
        reviewitems.get(position).setLiked(reviewitems.get(position).isLiked() ? false : true);
        //notifyItemChanged(position, ACTION_LIKE_BUTTON_CLICKED);
        if(!reviewitems.get(position).isLiked()){
        if (mContext instanceof AcitivityTruckDetail) {
        ((AcitivityTruckDetail) mContext).showLikedSnackbar();
        }
        else if(mContext instanceof AcitivityTruckReview){
        ((AcitivityTruckReview) mContext).showLikedSnackbar();
        }
        }

        }
        });
        holder.btnComments.setOnClickListener(new View.OnClickListener() {*/
@Override
public void onClick(View view) {
final Intent intent = new Intent(mContext, CommentsActivity.class);
        int[] startingLocation = new int[2];
        view.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        mContext.startActivity(intent);

        }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        }
        });
        }

@Override
public int getItemCount() {
        return reviewitems.size();
        }

public static class ReviewViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView userImageView;
    private TextView userTextView;
    private ImageView ivFeedCenter;
    private TextView ivFeedBottom;
    private ImageButton btnComments;
    public ImageButton btnMore;
    public View vBgLike;
    public ImageView ivLike;
    public TextSwitcher tsLikesCounter;
    public FrameLayout vImageRoot;
    public ShineButton likebutton;

    public ReviewViewHolder(View v) {
        super(v);
        userImageView = (CircleImageView)v.findViewById(R.id.userImageView);
        userTextView = (TextView)v.findViewById(R.id.userTextView);
        ivFeedCenter = (ImageView)v.findViewById(R.id.ivFeedCenter);
        ivFeedBottom = (TextView)v.findViewById(R.id.ivFeedBottom);
        btnComments = (ImageButton)v.findViewById(R.id.btnComments);
        btnMore = (ImageButton)v.findViewById(R.id.btnMore);
        vBgLike = (View)v.findViewById(R.id.vBgLike);
        ivLike = (ImageView)v.findViewById(R.id.ivLike);
        tsLikesCounter = (TextSwitcher)v.findViewById(R.id.tsLikesCounter);
        vImageRoot = (FrameLayout)v.findViewById(R.id.vImageRoot);
        likebutton = (ShineButton)v.findViewById(R.id.po_image);

    }
}


}