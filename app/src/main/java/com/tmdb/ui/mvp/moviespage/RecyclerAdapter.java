package com.tmdb.ui.mvp.moviespage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tmdb.R;
import com.tmdb.model.Result;
import com.tmdb.ui.mvp.moviedetail.MovieDetailActivity;
import com.tmdb.ui.mvp.moviedetail.MovieDetailFragment;
import com.tmdb.utils.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pranav Bhoraskar
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_PLOT = "movie_plot";
    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_RATED = "movie_rated";
    public static final String MOVIE_POSTER_URL = "movie_poster";
    public static final String MOVIE_RELEASE_DATE = "movie_release_date";

    private Context mContext;
    private List<Result> mMovieList;
    private final String navigationTag;

    public RecyclerAdapter(Context context, List<Result> movieList, String tag) {
        mContext = context;
        mMovieList = movieList;
        this.navigationTag = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bindData(mMovieList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ViewUtils.isInternetAvailable(mContext)) {

                    openMovieDetailsActivity(holder);

//                    MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
//                    FragmentManager fragmentManager = ((MoviesListActivity) mContext).
//                            getSupportFragmentManager();
//
//                    Bundle bundle = new Bundle();
//                    setBundleDetails(bundle, holder);
//                    movieDetailFragment.setArguments(bundle);
//
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.moviesFrameLayout, movieDetailFragment,
//                            MoviesListActivity.MOVIE_DETAIL_FRAGMENT_TAG);
////               fragmentTransaction.addToBackStack(MoviesListActivity.MOVIE_DETAIL_FRAGMENT_TAG);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                }
                else {
                    ViewUtils.createNoInternetDialog(mContext, R.string.network_error);
                }
            }
        });
    }

    private void openMovieDetailsActivity(ViewHolder holder) {
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        setBundleDetails(bundle, holder);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void setBundleDetails(Bundle bundle, ViewHolder holder) {
        bundle.putString(MOVIE_TITLE, holder.movieName.getText().toString());
        bundle.putString(MOVIE_RATED, holder.movieRating.getText().toString());
        bundle.putString(MOVIE_POSTER_URL, holder.urlPoster);
        bundle.putString(MOVIE_PLOT, holder.plotOfMovie);
        bundle.putString(MOVIE_RELEASE_DATE, holder.releaseDate);
        bundle.putString(MOVIE_ID, holder.movieId);
    }

    @Override
    public int getItemCount() {
        return mMovieList != null ? mMovieList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_title_poster)
        ImageView moviePoster;
        @BindView(R.id.movie_name)
        TextView movieName;
        @BindView(R.id.movie_rating)
        TextView movieRating;

        String movieId, urlPoster, plotOfMovie, releaseDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            movieName.setSelected(true);
        }

        /**
         * Function to bind data
         *
         * @param movieResult
         */
        @SuppressLint("DefaultLocale")
        public void bindData(Result movieResult) {

            movieId = String.valueOf(movieResult.getId());
            movieName.setText(movieResult.getTitle());

            movieRating.setText(String.format("Rating : %s",
                    String.valueOf(movieResult.getVoteAverage())));

            String moviePosterUrl = mContext.getString(R.string.image_url)
                    + movieResult.getPosterPath();
            Picasso.with(mContext).load(moviePosterUrl)
                    .placeholder(R.mipmap.no_image)
                    .fit()
                    .into(moviePoster);

            plotOfMovie = movieResult.getOverview();
            urlPoster = movieResult.getBackdropPath();
            releaseDate = movieResult.getReleaseDate();
        }
    }
}