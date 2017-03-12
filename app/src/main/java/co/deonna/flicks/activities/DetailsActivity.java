package co.deonna.flicks.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.deonna.flicks.R;
import co.deonna.flicks.models.Movie;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.rbStars) RatingBar rbStars;
    @BindView(R.id.tvPopularityRating) TextView tvPopularityRating;
    @BindView(R.id.tvSummary) TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        initializeComponents();
    }

    private void initializeComponents() {

        Movie movie = getIntent().getParcelableExtra("movie");

        tvTitle.setText(movie.originalTitle);

        Picasso
                .with(this)
                .load(movie.backdropPath)
                .placeholder(R.drawable.placeholder)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivImage);

        tvPopularityRating.setText(String.format(Locale.US, "%d", movie.getPopularity()));
        tvSummary.setText(movie.overview);
    }
}
