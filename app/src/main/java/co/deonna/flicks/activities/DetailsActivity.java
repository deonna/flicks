package co.deonna.flicks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.deonna.flicks.R;
import co.deonna.flicks.models.Movie;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    private static final int MAX_STARS = 10;
    private static final float STEP_SIZE = 0.5f;
    private static final int ROUNDED_CORNER_RADIUS = 10;

    private Movie movie;

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

        movie = getIntent().getParcelableExtra(Movie.KEY_MOVIE);

        tvTitle.setText(movie.originalTitle);

        Picasso
                .with(this)
                .load(movie.backdropPath)
                .placeholder(R.drawable.placeholder)
                .transform(new RoundedCornersTransformation(ROUNDED_CORNER_RADIUS, ROUNDED_CORNER_RADIUS))
                .into(ivImage);

        rbStars.setNumStars(MAX_STARS);
        rbStars.setStepSize(STEP_SIZE);
        rbStars.setRating(movie.getRating());

        tvPopularityRating.setText(String.format(Locale.US, "%d", movie.getPopularity()));
        tvSummary.setText(movie.overview);
    }

    @OnClick(R.id.ivImage)
    public void playVideo() {

        Intent intent = new Intent(this, PlayTrailerActivity.class);
        intent.putExtra(PlayTrailerActivity.VIDEO_ID, movie.videoId);

        startActivity(intent);
    }


}
