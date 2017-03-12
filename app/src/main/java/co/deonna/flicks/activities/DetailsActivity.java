package co.deonna.flicks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.deonna.flicks.R;
import co.deonna.flicks.models.Movie;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    public static final String VIDEO_ID = "video_id";
    private static final String VIDEOS_URL  = "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

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
        makeVideosRequest();
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
        intent.putExtra(VIDEO_ID, movie.videoId);

        startActivity(intent);
    }

    private void makeVideosRequest() {

        AsyncHttpClient client = new AsyncHttpClient();

        String url = String.format(VIDEOS_URL, movie.id);

        client.get(String.format(url), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray movieJsonResults;

                try {

                    movieJsonResults = response.getJSONArray(MainActivity.KEY_RESULTS);
                    movie.setVideoId(movieJsonResults);
                } catch (JSONException e) {

                    e.printStackTrace();
                    Log.e(TAG, "Exception reading JSON from API" + e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
