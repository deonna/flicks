package co.deonna.flicks.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    private static final String TAG = Movie.class.getSimpleName() ;

    public static final String KEY_ID = "id";
    public static final String KEY_KEY = "key";
    public static final String KEY_BACKDROP_PATH = "backdrop_path";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_ORIGINAL_TITLE = "original_title";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_VOTE_AVERAGE = "vote_average";
    public static final String KEY_POPULARITY= "popularity";
    public static final String KEY_MOVIE = "movie";

    public final String URL_POSTER = "https://image.tmdb.org/t/p/w342/%s";

    public String id;
    public String videoId;
    public String backdropPath;
    public String posterPath;
    public String originalTitle;
    public String overview;
    public double voteAverage;
    public double popularity;

    public Movie(JSONObject results) throws JSONException {

        id = results.getString(KEY_ID);
        videoId = "";

        backdropPath = String.format(URL_POSTER, results.getString(KEY_BACKDROP_PATH));
        posterPath = String.format(URL_POSTER, results.getString(KEY_POSTER_PATH));

        originalTitle = results.getString(KEY_ORIGINAL_TITLE);
        overview = results.getString(KEY_OVERVIEW);
        voteAverage = results.getDouble(KEY_VOTE_AVERAGE);
        popularity = results.getDouble(KEY_POPULARITY);
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

    public void setVideoId(JSONArray videos) {

        if (!videos.isNull(0)) {

            try {

                videoId = videos.getJSONObject(0).getString(KEY_KEY);
            } catch (JSONException e) {

                Log.e(TAG, "Couldn't parse video id.", e);
            }
        }
    }

    public int getPopularity() {

        return Long.valueOf(Math.round(popularity)).intValue();
    }


    public float getRating() {

        return Double.valueOf(voteAverage).floatValue();
    }

    protected Movie(Parcel in) {

        id = in.readString();
        videoId = in.readString();
        backdropPath = in.readString();
        posterPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        voteAverage = in.readDouble();
        popularity = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(videoId);
        dest.writeString(backdropPath);
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
    }

    @Override
    public int describeContents() {

        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel in) {

            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {

            return new Movie[size];
        }
    };

}
