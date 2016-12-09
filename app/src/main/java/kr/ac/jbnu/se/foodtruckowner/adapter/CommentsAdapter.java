package kr.ac.jbnu.se.foodtruckowner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.CommentsModel;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;
    private ArrayList<CommentsModel> commentsList;

    public CommentsAdapter(Context context, ArrayList<CommentsModel> commentsList) {
        this.context = context;
        this.commentsList = commentsList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.ivUserAvatar.setImageResource(commentsList.get(position).getImage());
        holder.tvComment.setText(commentsList.get(position).getTitleTextView());
    }
    @Override
    public int getItemCount() {
        return commentsList.size();
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivUserAvatar;
        private TextView tvComment;

        public CommentViewHolder(View view) {
            super(view);
            tvComment = (TextView) view.findViewById(R.id.tvComment);
            ivUserAvatar = (ImageView) view.findViewById(R.id.ivUserAvatar);

        }
    }
}