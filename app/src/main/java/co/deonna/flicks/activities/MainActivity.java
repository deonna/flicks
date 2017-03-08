package co.deonna.flicks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.deonna.flicks.R;
import co.deonna.flicks.adapters.MoviesAdapter;
import co.deonna.flicks.models.Movie;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private String KEY_RESULTS = "results";

    private List<Movie> movies;
    private MoviesAdapter moviesAdapter;

    @BindView(R.id.lvMovies) ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        movies =  new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movies);

        lvMovies.setAdapter(moviesAdapter);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray movieJsonResults;

                try {

                    movieJsonResults = response.getJSONArray(KEY_RESULTS);
                    movies.addAll(Movie.fromJsonArray(movieJsonResults));
                    moviesAdapter.notifyDataSetChanged();
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
