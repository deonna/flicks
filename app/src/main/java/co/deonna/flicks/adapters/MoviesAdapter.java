package co.deonna.flicks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.deonna.flicks.R;
import co.deonna.flicks.models.Movie;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(@NonNull Context context, @NonNull List<Movie>
            movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        Movie movie = getItem(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(movie.originalTitle);
        holder.tvOverview.setText(movie.overview);

        Picasso
                .with(getContext())
                .load(movie.posterPath)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(holder.ivPoster);



        return convertView;
    }


    public class ViewHolder {

        @BindView(R.id.ivPoster) ImageView ivPoster;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public ViewHolder(View view) {

            ButterKnife.bind(this, view);
        }
    }
}
