package co.deonna.flicks.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.deonna.flicks.R;
import co.deonna.flicks.adapters.MoviesAdapter;
import co.deonna.flicks.models.Movie;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    public static final String KEY_RESULTS = "results";
    private static final String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    public static Typeface fontRegular;
    public static Typeface fontLight;
    public static Typeface fontBold;

    private List<Movie> movies;
    private MoviesAdapter moviesAdapter;

    @BindView(R.id.rvMovies) RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fontRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        fontLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        fontBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        movies =  new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movies);

        rvMovies.setAdapter(moviesAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        makeMoviesApiRequest();
    }

    private void makeMoviesApiRequest() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                Log.e(TAG, "Error reading JSON from API " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException  {

                if(!response.isSuccessful()) {

                    throw new IOException("Unexpected status code: " + response);
                }

                try {

                    JSONObject responseJson = new JSONObject(response.body().string());
                    JSONArray movieJsonResults = responseJson.getJSONArray(KEY_RESULTS);

                    movies.addAll(Movie.fromJsonArray(movieJsonResults));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            moviesAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();
                    Log.e(TAG, "Exception reading JSON from API" + e.toString());
                }
            }
        });

    }
}
