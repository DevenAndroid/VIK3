package com.vik3.ui.dashboard;

import static com.vik3.utils.CommonMethods.getWebsite;
import static com.vik3.utils.CommonMethods.share;
import static com.vik3.utils.CommonMethods.showAlert;
import static com.vik3.utils.Constants.AUDIO_STREAMING_URL;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vik3.BuildConfig;
import com.vik3.MediaPlayerService;
import com.vik3.R;
import com.vik3.apiClient.RetrofitClient;
import com.vik3.apiClient.RetrofitClientStations;
import com.vik3.databinding.FragmentRadioBinding;
import com.vik3.prefraceMaager.PrefManagerPlayer;
import com.vik3.ui.adapters.AdapterHomeArtistDetailList;
import com.vik3.ui.adapters.AdapterSongList;
import com.vik3.ui.fragments.ProfileFragment;
import com.vik3.ui.models.ModelArtist;
import com.vik3.ui.models.ModelImage;
import com.vik3.ui.models.ModelStations;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RadioFragment extends Fragment {

    private FragmentRadioBinding binding;

    ProgressDialog progressDialog;
    public static ModelStations modelStations;

    public static MediaPlayerService mediaPlayerService;
    public static boolean serviceBound = false;

    public static String sourceUrl;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRadioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        clickListeners();

        return root;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mediaPlayerService = binder.getService();
            serviceBound = true;
            Glide.with(requireContext()).asGif().load(R.raw.loading_buffering).into(binding.button);

            if (mediaPlayerService.mediaPlayer != null) {
                mediaPlayerService.mediaPlayer.setOnPreparedListener(mp -> {
                    getAnimation(true, binding.imageBg, binding.button);
                    mediaPlayerService.playMedia();
                });
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    public static void playAudio(String media, Context context, ServiceConnection serviceConnection) {
        if (!serviceBound) {
            Intent playerIntent = new Intent(context, MediaPlayerService.class);
            playerIntent.putExtra("media", media);
            context.startService(playerIntent);
            context.bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public static void stopAudio(Context context, ServiceConnection serviceConnection) {
        if (mediaPlayerService != null) {
            mediaPlayerService.pauseMedia();
            mediaPlayerService.stopForeground(true);
        } else {
            Intent playerIntent = new Intent(context, MediaPlayerService.class);
            context.stopService(playerIntent);
            context.startService(playerIntent);
            context.bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void clickListeners() {
        progressDialog = new ProgressDialog(getContext());
        if (mediaPlayerService == null) {
            new PrefManagerPlayer(requireContext()).setPlay(false);
        }
        new Player(requireContext(), binding.button, binding.imageBg).execute(AUDIO_STREAMING_URL);

        if (new PrefManagerPlayer(requireContext()).isPlay()) {
            getAnimation(true, binding.imageBg, binding.button);
        }

        binding.button.setOnClickListener(v -> playButtonClick(requireContext(), serviceConnection, binding.imageBg, binding.button));

        binding.buttonSleep.setOnClickListener(v -> getSleepMenus());
        getArtist();

    }

    public static void playButtonClick(Context context, ServiceConnection serviceConnection, ImageView imageViewBg, ImageView button) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        AudioManager.OnAudioFocusChangeListener afChangeListener = focusChange -> {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Lower the volume, keep playing
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Your app has been granted audio focus again
                // Raise volume to normal, restart playback if necessary
            }
        };

        int result = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);

        if (new PrefManagerPlayer(context).isPlay()) {
            new PrefManagerPlayer(context).setPlay(false);
            stopAudio(context, serviceConnection);
            getAnimation(false, imageViewBg, button);
            MediaPlayerService.PlaybackStatus playbackStatus = MediaPlayerService.PlaybackStatus.PAUSED;
        } else {
            try {
                if (mediaPlayerService == null) {
                    playAudio(sourceUrl, context, serviceConnection);
                    getAnimation(true, imageViewBg, button);
                } else if (mediaPlayerService.mediaPlayer == null) {
                    if (mediaPlayerService.mediaPlayer == null) {
                        mediaPlayerService.initMediaPlayer();
                    }
                    playAudio(sourceUrl, context, serviceConnection);
                    getAnimation(true, imageViewBg, button);

                    Glide.with(context).asGif().load(R.raw.loading_buffering).into(button);

                    mediaPlayerService.mediaPlayer.setOnPreparedListener(mp -> {
                        getAnimation(true, imageViewBg, button);
                        mediaPlayerService.playMedia();
                    });

                } else if (!mediaPlayerService.mediaPlayer.isPlaying()) {
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        getAnimation(true, imageViewBg, button);
//                        mediaPlayerService.resumeMedia();
                        mediaPlayerService.playMedia();
                    }
                    /*MediaPlayerService.PlaybackStatus playbackStatus = MediaPlayerService.PlaybackStatus.PLAYING;
                    mediaPlayerService.buildNotification(playbackStatus);*/
                } else {

                    /*MediaPlayerService.PlaybackStatus playbackStatus = MediaPlayerService.PlaybackStatus.PAUSED;
                    mediaPlayerService.buildNotification(playbackStatus);*/
                    getAnimation(false, imageViewBg, button);
                    mediaPlayerService.pauseMedia();
                    /*if (mediaPlayerService.mediaPlayer.isPlaying()) {
                        mediaPlayerService.mediaPlayer.pause();
                    }*/
                }
            } catch (IllegalStateException e) {
                getAnimation(true, imageViewBg, button);
                playAudio(sourceUrl, context, serviceConnection);
                Toast.makeText(context, "ExceptionIlliNulll : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static void getAnimation(boolean isAnimate, ImageView imageViewBg, ImageView button) {
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        if (isAnimate) {
            rotate.setDuration(5000);
            rotate.setInterpolator(new LinearInterpolator());
            rotate.setRepeatCount(Animation.INFINITE);
            imageViewBg.startAnimation(rotate);

            button.setImageResource(R.drawable.img_pause);
        } else {
            rotate.setDuration(0);
            rotate.setInterpolator(new LinearInterpolator());
//                    rotate.setRepeatCount(Animation.INFINITE);
            imageViewBg.startAnimation(rotate);

            button.setImageResource(R.drawable.img_play);
        }
    }

    private void getSleepMenus() {
        PopupMenu menu = new PopupMenu(requireContext(), binding.buttonSleep);
        menu.getMenu().add(getString(R.string.minute_15));
        menu.getMenu().add(getString(R.string.minute_30));
        menu.getMenu().add(getString(R.string.minute_45));
        menu.getMenu().add(getString(R.string.minute_60));
        menu.getMenu().add(getString(R.string.timer_off));
        menu.show();
        menu.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("15 Minute")) {
                getSleep(getString(R.string.min_15), 15 * 60 * 1000);
            } else if (item.getTitle().equals("30 Minute")) {
                getSleep(getString(R.string.min_30), 30 * 60 * 1000);
            } else if (item.getTitle().equals("45 Minute")) {
                getSleep(getString(R.string.min_45), 45 * 60 * 1000);
            } else if (item.getTitle().equals("60 Minute")) {
                getSleep(getString(R.string.min_60), 60 * 60 * 1000);
            } else {
                binding.buttonSleep.setText("Sleep");
            }
            return false;
        });
    }

    private void getSleep(String time, long sleeperTime) {
        playButtonClick(getContext(), serviceConnection, binding.imageBg, binding.button);
//        if (mediaPlayerService != null && mediaPlayerService.mediaPlayer != null && !mediaPlayerService.mediaPlayer.isPlaying()) {
////            playMusic(requireContext(), binding.button, binding.imageBg);
//        }
        binding.buttonSleep.setText(time);
        new Handler().postDelayed(() -> {
            binding.buttonSleep.setText("Sleep");
            getAnimation(false, binding.imageBg, binding.button);

            new PrefManagerPlayer(getContext()).setPlay(true);
            stopAudio(getContext(), serviceConnection);
        }, sleeperTime);
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            if (mediaPlayerService != null && mediaPlayerService.mediaPlayer.isPlaying()) {
                new PrefManagerPlayer(requireContext()).setPlay(true);
            }
        } catch (Exception e) {
            new PrefManagerPlayer(requireContext()).setPlay(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mediaPlayerService != null && mediaPlayerService.mediaPlayer.isPlaying()) {
                new PrefManagerPlayer(requireContext()).setPlay(true);
            }
        } catch (Exception e) {
            new PrefManagerPlayer(requireContext()).setPlay(false);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    class Player extends AsyncTask<String, Void, Boolean> {
        Context context;
        ImageView button;
        ImageView imageViewBg;

        public Player(Context context, ImageView button, ImageView imageViewBg) {
            this.context = context;
            this.button = button;
            this.imageViewBg = imageViewBg;

            progressDialog.setMessage("Buffering...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean prepared;
            sourceUrl = strings[0];
            prepared = true;
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Buffering...");
            progressDialog.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getStations(List<ModelArtist> artists) {
        Call<ModelStations> call = RetrofitClientStations
                .getInstance()
                .getApiService()
                .getStations();
        call.enqueue(new Callback<ModelStations>() {
            @Override
            public void onResponse(@NotNull Call<ModelStations> call, @NotNull Response<ModelStations> response) {
                try {
                    modelStations = response.body();
                    for (int i = 0; i < (response.body() != null ? response.body().getHistory().size() : 0); i++) {
                        response.body().getHistory().get(i).setUrl(response.body().getLogoUrl());
                    }
                    binding.textViewName.setText(Objects.requireNonNull(response.body()).getCurrentTrack().getTitle().split("- ")[1]);
                    binding.textViewSinger.setText(response.body().getCurrentTrack().getTitle().split("- ")[0]);
                    Glide.with(requireContext())
                            .load(response.body().getCurrentTrack().getArtworkUrlLarge())
                            .into(binding.image);
                    response.body().getHistory().remove(0);

                    AdapterSongList adapter = new AdapterSongList(requireContext(), response.body().getHistory().subList(0, 10));
                    binding.recyclerView.setAdapter(adapter);
                    adapter.setClickListener((view, position) -> {
                        if (getArtist(response, position, artists) != null) {
                            showBottomSheetDialog(getArtist(response, position, artists));
                        } else {
                            showAlert(requireContext(), "Details on this song are not available yet.");
                        }
                    });

                    binding.buttonShare.setOnClickListener(v -> share(requireContext(),
                            "Now Playing \"" + response.body().getCurrentTrack().getTitle().split("- ")[1] + "\" by \"" +
                                    response.body().getCurrentTrack().getTitle().split("- ")[0] + "\" on Radio VIK3. Download Radio VIK3 via https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "  to the best christian songs. Follow us on Twitter - https://twitter.com/RadioVik3"));
                } catch (Exception ignored) {

                }
            }

            @Override
            public void onFailure(@NotNull Call<ModelStations> call, @NotNull Throwable t) {

            }
        });
    }

    private ModelArtist getArtist(@NotNull Response<ModelStations> response, int position, List<ModelArtist> artists) {
        ModelArtist modelArtist = new ModelArtist();
        for (int i = 0; i < artists.size(); i++) {
            if (response.body().getHistory().get(position).getTitle().split("- ")[0].trim().contains(artists.get(i).getTitle().trim())) {
                modelArtist = artists.get(i);
                break;
            } else {
                modelArtist = null;
            }
        }
        return modelArtist;
    }

    private void getArtist() {
        Call<List<ModelArtist>> call = RetrofitClient
                .getInstance()
                .getApiService()
                .getArtist();
        call.enqueue(new Callback<List<ModelArtist>>() {
            @Override
            public void onResponse(@NotNull Call<List<ModelArtist>> call, @NotNull Response<List<ModelArtist>> response) {
                try {
                    List<ModelArtist> artists = response.body();
                    getStations(artists);

                    final Handler handler = new Handler();
                    int delay = 5000;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getStations(artists);
                            handler.postDelayed(this, delay);
                        }
                    }, delay);
                } catch (Exception ignored) {
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<ModelArtist>> call, @NotNull Throwable t) {
            }
        });
    }

    private void showBottomSheetDialog(ModelArtist body) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_singer_detail);

        List<ModelImage> images = new ArrayList<>();
        images.add(new ModelImage(0, getString(R.string.share), R.drawable.img_share));
        images.add(new ModelImage(1, getString(R.string.profile), R.drawable.img_profile));
        if (body.getSocialLinks() != null) {
            if (body.getSocialLinks().getFacebook() != null) {
                images.add(new ModelImage(2, getString(R.string.facebook), R.drawable.img_facebook));
            }
            if (body.getSocialLinks().getInstagram() != null) {
                images.add(new ModelImage(3, getString(R.string.instagram), R.drawable.img_instagram));
            }
            if (body.getSocialLinks().getTwitter() != null) {
                images.add(new ModelImage(4, getString(R.string.twitter), R.drawable.img_twitter));
            }
            if (body.getSocialLinks().getYouTube() != null) {
                images.add(new ModelImage(5, "YouTube", R.drawable.img_you_tube));
            }
        }
        AdapterHomeArtistDetailList adapterHomeArtistDetailList = new AdapterHomeArtistDetailList(requireContext(), images);
        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recyclerView);
        Objects.requireNonNull(recyclerView).setAdapter(adapterHomeArtistDetailList);
        adapterHomeArtistDetailList.setClickListener((view, position) -> {
            if (position == 0) {
                share(requireContext(),
                        "Now Playing \"" + body.getTitle().split("- ")[1] + "\" by \"" +
                                body.getTitle().split("- ")[0] + "\" on Radio VIK3. Download Radio VIK3 via https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "  to the best christian songs. Follow us on Twitter - https://twitter.com/RadioVik3");
            } else if (position == 1) {
                Fragment fragment = new ProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("model", body);
                fragment.setArguments(bundle);
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment_activity_main, fragment)
                        .addToBackStack(null)
                        .commit();
                bottomSheetDialog.dismiss();
            } else if (position == 2) {
                getWebsite(requireContext(), body.getSocialLinks().getFacebook());
            } else if (position == 3) {
                getWebsite(requireContext(), body.getSocialLinks().getInstagram());
            } else if (position == 4) {
                getWebsite(requireContext(), body.getSocialLinks().getTwitter());
            }
        });
        TextView textView = bottomSheetDialog.findViewById(R.id.textView);
        TextView textViewSinger = bottomSheetDialog.findViewById(R.id.textViewSinger);
        Objects.requireNonNull(textView).setText(body.getTitle());
        Objects.requireNonNull(textViewSinger).setText(body.getBio());
        Button button = bottomSheetDialog.findViewById(R.id.button);
        ImageView imageView = bottomSheetDialog.findViewById(R.id.image);

        Glide.with(requireContext())
                .load(body.getImage())
                .into(Objects.requireNonNull(imageView));

        Objects.requireNonNull(button).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }
}