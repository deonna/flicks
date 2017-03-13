package co.deonna.flicks.network;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MovieDbClient {

    private static final String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    ;
    private OkHttpClient client;

    public MovieDbClient() {
        client = new OkHttpClient();
    }

    public void getTopMovies(Callback callback) {

        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void getVideoForMovie(String movieId, Callback callback) {

        String url = String.format(VIDEOS_URL, movieId);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
