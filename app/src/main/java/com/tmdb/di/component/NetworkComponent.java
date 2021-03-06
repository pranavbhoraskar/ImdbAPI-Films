package com.tmdb.di.component;

import com.tmdb.di.module.AppModule;
import com.tmdb.di.module.NetworkModule;
import com.tmdb.session.SessionManager;
import com.tmdb.ui.mvp.login.LoginActivity;
import com.tmdb.ui.mvp.moviedetail.MovieDetailActivity;
import com.tmdb.ui.mvp.moviedetail.MovieDetailFragment;
import com.tmdb.ui.mvp.moviespage.MoviesFragment;
import com.tmdb.ui.mvp.userreviews.UserReviewActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Pranav Bhoraskar
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {

    SessionManager sessionManager();

    void inject(MoviesFragment moviesFragment);

    void injectMovieDetails(MovieDetailFragment movieDetailFragment);

    void injectLauncher(LoginActivity loginActivity);

    void injectMovieDetailsActivity(MovieDetailActivity movieDetailActivity);

    void injectUserReviewsActivity(UserReviewActivity userReviewActivity);
}