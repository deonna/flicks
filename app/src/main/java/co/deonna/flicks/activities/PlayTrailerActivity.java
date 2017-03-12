package co.deonna.flicks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.deonna.flicks.R;

public class PlayTrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {


    public static final String VIDEO_ID = "video_id";
    private static final int RECOVERY_REQUEST = 1;
    private static final String KEY_API = "AIzaSyBDXU3bVnD4lt8avt9wOkMPUistoY0VykU";

    private String videoId;

    @BindView(R.id.ytpvTrailer) YouTubePlayerView ytpvTrailer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_trailer);

        ButterKnife.bind(this);

        videoId = getIntent().getStringExtra(VIDEO_ID);


        ytpvTrailer.initialize(KEY_API, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        if(!wasRestored) {
            youTubePlayer.cueVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult failureResult) {

        if (failureResult.isUserRecoverableError()) {
            failureResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format("Error! ", failureResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            ytpvTrailer.initialize(KEY_API, this);
        }
    }
}
