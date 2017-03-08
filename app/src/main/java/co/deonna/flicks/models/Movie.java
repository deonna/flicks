package co.deonna.flicks.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private static final String TAG = Movie.class.getSimpleName() ;

    public final String KEY_BACKDROP_PATH = "backdrop_path";
    public final String KEY_POSTER_PATH = "poster_path";
    public final String KEY_ORIGINAL_TITLE = "original_title";
    public final String KEY_OVERVIEW = "overview";

    public final String URL_POSTER = "https://image.tmdb.org/t/p/w342/%s";

    public String backdropPath;
    public String posterPath;
    public String originalTitle;
    public String overview;

    public Movie(JSONObject results) throws JSONException {

        backdropPath = String.format(URL_POSTER, results.getString(KEY_BACKDROP_PATH));
        posterPath = String.format(URL_POSTER, results.getString(KEY_POSTER_PATH));
        originalTitle = results.getString(KEY_ORIGINAL_TITLE);
        overview = results.getString(KEY_OVERVIEW);
    }

    public static List<Movie> fromJsonArray(JSONArray results) {
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {

            try {
                movies.add(new Movie(results.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e(TAG, "Couldn't create new movie list.", e);
            }
        }

        return movies;
    }

}
