package co.deonna.flicks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import co.deonna.flicks.R;
import co.deonna.flicks.models.Movie;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movies;

    public MoviesAdapter(@NonNull Context context, @NonNull List<Movie>
            movies) {

        this.context = context;
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = movies.get(position);

        holder.tvTitle.setText(movie.originalTitle);
        holder.tvOverview.setText(movie.overview);

        if (holder.ivPoster != null) {
            Picasso
                    .with(context)
                    .load(movie.posterPath)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(holder.ivPoster);
        } else if (holder.ivBackdrop != null) {
            Picasso
                    .with(context)
                    .load(movie.backdropPath)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(holder.ivBackdrop);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable @BindView(R.id.ivBackdrop) ImageView ivBackdrop;
        @Nullable @BindView(R.id.ivPoster) ImageView ivPoster;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
