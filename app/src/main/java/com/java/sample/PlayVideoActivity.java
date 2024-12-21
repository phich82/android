package com.java.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.java.sample.adapter.YouTubePlaylistAdapter;
import com.java.sample.dto.YouTubeItem;
import com.java.sample.dto.YouTubeResponseData;
import com.java.sample.http.Http;
import com.java.sample.util.Callback;
import com.java.sample.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PlayVideoActivity extends AppCompatActivity {

    private static final String API_KEY_YOUTUBE = "AIzaSyADUrHRg9M4juHL6HU_SGeyZlGhrP_RdEY";
    private static final String PLAYLIST_ID_YOUTUBE = "PL4wCiLP-N9g2-o6FXP29hA8UQWr_6tSRU";
    private static final int PER_PAGE = 10;
    private final String URL_YOUTUBE_PLAYLIST = String.format("https://www.googleapis.com/youtube/v3/playlistItems?playlistId=%s&key=%s&part=snippet&maxResults=%d", PLAYLIST_ID_YOUTUBE, API_KEY_YOUTUBE, PER_PAGE);
    private Http http;

    private ListView listViewPlaylistYoutube;
    private List<YouTubeItem> youTubePlaylistVideoList;
    private YouTubePlaylistAdapter youTubePlaylistAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_playlist_youtube);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewPlaylistYoutube = findViewById(R.id.listViewPlaylistYoutube);
        youTubePlaylistVideoList = new ArrayList<>();
        youTubePlaylistAdapter = new YouTubePlaylistAdapter(this, R.layout.youtube_playlist_item, youTubePlaylistVideoList);
        listViewPlaylistYoutube.setAdapter(youTubePlaylistAdapter);

        // Click on each video, play this video
        listViewPlaylistYoutube.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlayVideoActivity.this, PlayVideoYouTubeActivity.class);
                intent.putExtra("idVideoYouTube", youTubePlaylistVideoList.get(position).getSnippet().getResourceId().getVideoId());
                startActivity(intent);
            }
        });

        // Load playlist from youtube
        http = new Http();
        http.request(this, URL_YOUTUBE_PLAYLIST, new Callback<Boolean, JSONObject>() {
            @Override
            public Void call() {
                System.out.println("youtube ===> success (callback): " + this.success);
                if (this.success) {
                    YouTubeResponseData responseData = YouTubeResponseData.from(this.data);
                    youTubePlaylistVideoList.clear();
                    youTubePlaylistVideoList.addAll(responseData.getItems());
                    youTubePlaylistAdapter.notifyDataSetChanged();
                }
                return null;
            }
        });

    }

}

//package com.java.sample;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.java.sample.dto.YouTubeResponseData;
//import com.java.sample.http.Http;
//import com.java.sample.util.Callback;
//import com.java.sample.util.Util;
//import com.java.sample.youtube.CustomPlayerUiController;
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
//import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
//
//import org.json.JSONObject;
//
//import java.util.Random;
//public class PlayVideoActivity extends AppCompatActivity {
////public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
////public class PlayVideoActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
//
//    private static final int REQUEST_VIDEO = 123;
//    private static final String API_KEY_YOUTUBE = "AIzaSyADUrHRg9M4juHL6HU_SGeyZlGhrP_RdEY";
//    private static final String PLAYLIST_ID_YOUTUBE = "PL4wCiLP-N9g2-o6FXP29hA8UQWr_6tSRU";
//    private static final int PER_PAGE = 10;
//    private final String URL_YOUTUBE_PLAYLIST = String.format("https://www.googleapis.com/youtube/v3/playlistItems?playlistId=%s&key=%s&part=snippet&maxResults=%d", PLAYLIST_ID_YOUTUBE, API_KEY_YOUTUBE, PER_PAGE);
//    private Http http;
//
//
//
//    //YouTubePlayerView youTubePlayerView;
//    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView youTubePlayerView2;
//    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView youTubePlayerView3;
//    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView youTubePlayerView4;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_play_video);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        //youTubePlayerView = findViewById(R.id.youTubePlayerView0);
//        //youTubePlayerView.initialize(API_KEY_YOUTUBE, this);
//
//        youTubePlayerView2 = findViewById(R.id.youtubeLayerView2);
//        youTubePlayerView3 = findViewById(R.id.youtubeLayerView3);
//        youTubePlayerView4 = findViewById(R.id.youtubeLayerView4);
//
//        getLifecycle().addObserver(youTubePlayerView2);
//        youTubePlayerView2.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
//                youTubePlayer.loadVideo("u9tzLI4SJ2w", 0);
//            }
//        });
////        youTubePlayerView2.getYouTubePlayerWhenReady(youTubePlayer -> {
////            youTubePlayer.loadVideo("u9tzLI4SJ2w", 0); // autoplay
////            //youTubePlayer.cueVideo("u9tzLI4SJ2w", 0);
////        });
//        String PLAYLIST_ID = "RDEMpNVMa83HZXJAVK1UbMoTfA";
//        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
//                .controls(1)
//                .fullscreen(0) // 1: fullscreen
//                .listType("playlist")
//                .list(PLAYLIST_ID)
//                .build();
//        youTubePlayerView3.initialize(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
//                youTubePlayer.play();
//            }
//        }, iFramePlayerOptions);
//
//        // Create your own custom UI
//        getLifecycle().addObserver(youTubePlayerView4);
//        View customPlayerUi = youTubePlayerView4.inflateCustomPlayerUi(R.layout.custom_player_ui);
//        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
//                CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(PlayVideoActivity.this, customPlayerUi, youTubePlayer, youTubePlayerView4);
//                youTubePlayer.addListener(customPlayerUiController);
//
//                YouTubePlayerUtils.loadOrCueVideo(
//                        youTubePlayer,
//                        getLifecycle(),
//                        VideoIdsProvider.getNextVideoId(),
//                        0f
//                );
//            }
//        };
//        // disable iframe ui
//        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
//        youTubePlayerView4.initialize(listener, options);
//
//        http = new Http();
//        http.request(this, URL_YOUTUBE_PLAYLIST, new Callback<Boolean, JSONObject>() {
//            @Override
//            public Void call() {
//                System.out.println("youtube ===> URL_YOUTUBE_PLAYLIST: " + URL_YOUTUBE_PLAYLIST);
//                System.out.println("youtube ===> success (callback): " + this.success);
//                System.out.println("youtube ===> data (callback): " + this.data);
//                YouTubeResponseData responseData = YouTubeResponseData.from(this.data);
//                System.out.println("youtube ===> responseData (callback): " + responseData);
//                System.out.println("youtube ===> responseData (getKind): " + responseData.getKind());
//                System.out.println("youtube ===> responseData (getEtag): " + responseData.getEtag());
//                System.out.println("youtube ===> responseData (getPageInfo): " + responseData.getPageInfo().getTotalResults());
//                System.out.println("youtube ===> responseData (getPageInfo): " + responseData.getPageInfo().getResultsPerPage());
//                System.out.println("youtube ===> responseData (getItems): " + Util.toJson(responseData.getItems().get(0).getSnippet().getThumbnails()));
//
//                return null;
//            }
//        });
//
//    }
//
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        System.out.println("requestCode ===> " + requestCode);
////        if (requestCode == REQUEST_VIDEO) {
////            youTubePlayerView.initialize(API_KEY_YOUTUBE, PlayVideoActivity.this);
////        }
////        super.onActivityResult(requestCode, resultCode, data);
////    }
//
////    @Override
////    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
////        youTubePlayer.cueVideo("kVG9XjbX1Ig");
////    }
////
////    @Override
////    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
////        System.out.println("error ===> " + youTubeInitializationResult.toString());
////        if (youTubeInitializationResult.isUserRecoverableError()) {
////            youTubeInitializationResult.getErrorDialog(PlayVideoActivity.this, REQUEST_VIDEO);
////        } else {
////            Toast.makeText(PlayVideoActivity.this, "Unknown error!", Toast.LENGTH_SHORT).show();
////        }
////    }
//
//    public static class VideoIdsProvider {
//        private static final String[] videoIds = {"6JYIGclVQdw", "LvetJ9U_tVY", "S0Q4gqBUs7c", "zOa-rSM4nms"};
//        private static final String[] liveVideoIds = {"hHW1oY26kxQ"};
//        private static final Random random = new Random();
//
//        public static String getNextVideoId() {
//            return videoIds[random.nextInt(videoIds.length)];
//        }
//
//        public static String getNextLiveVideoId() {
//            return liveVideoIds[random.nextInt(liveVideoIds.length)];
//        }
//    }
//}