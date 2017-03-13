package co.deonna.flicks.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.deonna.flicks.R;
import co.deonna.flicks.activities.DetailsActivity;
import co.deonna.flicks.activities.MainActivity;
import co.deonna.flicks.activities.PlayTrailerActivity;
import co.deonna.flicks.models.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Movie> movies;

    private static final int DEFAULT = 0;
    private static final int HIGH_RATING = 1;

    public MoviesAdapter(@NonNull Context context, @NonNull List<Movie>
            movies) {

        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getItemViewType(int position) {

        Movie movie = movies.get(position);

        if (movie.voteAverage > 5) {
            return HIGH_RATING;
        }

        return DEFAULT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);


        switch (viewType) {

            case HIGH_RATING:
                View highRatingView = inflater.inflate(R.layout.item_movie_high_rating, parent, false);
                viewHolder = new HighRatingViewHolder(highRatingView);
                break;

            default:
                View defaultView = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new DefaultViewHolder(defaultView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Movie movie = movies.get(position);

        int orientation = context.getResources().getConfiguration().orientation;

        switch (holder.getItemViewType()) {

            case HIGH_RATING:
                HighRatingViewHolder highRatingViewHolder = (HighRatingViewHolder) holder;
                highRatingViewHolder.configure(movie);

                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    displayMovieImage(movie.backdropPathLandscape, ((HighRatingViewHolder) holder).ivImage, R.drawable.placeholder_landscape);
                } else {
                    displayMovieImage(movie.backdropPath, ((HighRatingViewHolder) holder).ivImage, R.drawable.placeholder);
                }

                break;

            default:
                DefaultViewHolder defaultViewHolder = (DefaultViewHolder) holder;
                defaultViewHolder.configure(movie);
                displayMovieImage(movie.posterPath, ((DefaultViewHolder) holder).ivImage, R.drawable.placeholder);
                break;
        }
    }

    private void displayMovieImage(String path, ImageView ivImage, int placeholderId) {

        Picasso
                .with(context)
                .load(path)
                .placeholder(placeholderId)
                .into(ivImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public static class DefaultViewHolder extends RecyclerView.ViewHolder {

        public Movie currentMovie;

        @BindView(R.id.cvMovie) CardView cvMovie;
        @Nullable @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;


        public DefaultViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void configure(Movie movie) {

            tvTitle.setTypeface(MainActivity.fontRegular);
            tvTitle.setText(movie.originalTitle);

            tvOverview.setTypeface(MainActivity.fontLight);
            tvOverview.setText(movie.overview);

            currentMovie = movie;
        }

        @OnClick(R.id.cvMovie)
        public void showDetailView() {

            Context context = cvMovie.getContext();

            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(Movie.KEY_MOVIE, currentMovie);

            context.startActivity(intent);
        }
    }

    public static class HighRatingViewHolder extends RecyclerView.ViewHolder {

        public Movie currentMovie;

        @BindView(R.id.cvMovie) CardView cvMovie;
        @Nullable @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvNumStars) TextView tvNumStars;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public HighRatingViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void configure(Movie movie) {

            tvTitle.setTypeface(MainActivity.fontRegular);
            tvTitle.setText(movie.originalTitle);

            tvNumStars.setTypeface(MainActivity.fontRegular);
            tvNumStars.setText(Float.toString(movie.getRating()));

            tvOverview.setTypeface(MainActivity.fontLight);
            tvOverview.setText(movie.overview);

            currentMovie = movie;
        }

        @OnClick(R.id.cvMovie)
        public void showTrailer() {

            Context context = cvMovie.getContext();

            Intent intent = new Intent(context, PlayTrailerActivity.class);
            intent.putExtra(PlayTrailerActivity.VIDEO_ID, currentMovie.videoId);

            context.startActivity(intent);
        }
    }
}
