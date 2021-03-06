package com.tmdb.ui.mvp.userreviews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tmdb.R;
import com.tmdb.model.userreviews.UserReviewsResult;
import com.tmdb.ui.mvp.webviewpage.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pranav Bhoraskar
 */

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.ViewHolder> {

    private List<UserReviewsResult> userReviewsList;
    private Context context;
    private String movieTitle;

    public UserReviewAdapter(Context context, String movieTitle,
                             ArrayList<UserReviewsResult> userReviewsResults) {
        this.context = context;
        this.movieTitle = movieTitle;
        userReviewsList = userReviewsResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(userReviewsList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return userReviewsList != null ? userReviewsList.size() : 0;
    }

    public void setUpRecyclerView(List<UserReviewsResult> userReviewsList) {
        if (this.userReviewsList != null) {
            this.userReviewsList.clear();
            this.userReviewsList.addAll(userReviewsList);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author)
        TextView reviewAuthor;
        @BindView(R.id.review_content)
        TextView reviewContent;
        @BindView(R.id.review_button_url)
        Button reviewButtonUrl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final UserReviewsResult userReviewsResult) {
            reviewAuthor.setText(userReviewsResult.getAuthor());
            reviewContent.setText(userReviewsResult.getContent());
            reviewButtonUrl.setOnClickListener(view -> {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(context.getString(R.string.url), userReviewsResult.getUrl());
                intent.putExtra(context.getString(R.string.movie_title), movieTitle);
                context.startActivity(intent);
            });
        }
    }
}