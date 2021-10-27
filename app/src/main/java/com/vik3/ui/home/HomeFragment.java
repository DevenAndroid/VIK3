package com.vik3.ui.home;

import static com.vik3.ui.dashboard.RadioFragment.getAnimation;
import static com.vik3.ui.dashboard.RadioFragment.mediaPlayerService;
import static com.vik3.ui.dashboard.RadioFragment.serviceBound;
import static com.vik3.ui.dashboard.RadioFragmentNew.Broadcast_PLAY_NEW_AUDIO;
import static com.vik3.ui.dashboard.RadioFragmentNew.player;
import static com.vik3.utils.CommonMethods.share;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vik3.MediaPlayerService;
import com.vik3.MediaPlayerServiceNew;
import com.vik3.PlaybackStatus;
import com.vik3.R;
import com.vik3.apiClient.RetrofitClientStations;
import com.vik3.databinding.FragmentHomeBinding;
import com.vik3.prefraceMaager.PrefManagerPlayer;
import com.vik3.ui.adapters.AdapterHomeAlbumList;
import com.vik3.ui.dashboard.RadioFragment;
import com.vik3.ui.models.ModelCollections;
import com.vik3.ui.models.ModelStations;
import com.vik3.utils.StorageUtil;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    MediaPlayer mediaPlayer;
    ProgressDialog progressDialog;
    boolean initialStage = true;
    private FragmentHomeBinding binding;
    boolean serviceBound = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ImageView imageBg, button;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageBg = binding.imageBg;
        button = binding.button;

        if (mediaPlayerService == null){
            new PrefManagerPlayer(requireContext()).setPlay(false);
        }

        if (new PrefManagerPlayer(requireContext()).isPlay()) {
            getAnimation(true, binding.imageBg, binding.button);
        }
        if (player != null && player.mediaPlayer!= null && player.mediaPlayer.isPlaying()){
            getAnimation(true, binding.imageBg, binding.button);
        } else {
            getAnimation(false, binding.imageBg, binding.button);
        }
//        binding.button.setOnClickListener(v -> RadioFragment.playButtonClick(requireContext(),serviceConnection, binding.imageBg, binding.button));
        binding.button.setOnClickListener(view -> {
            try {
                if (player.mediaPlayer.isPlaying()) {
                    //create the pause action
                    player.mediaPlayer.pause();
                    player.buildNotification(PlaybackStatus.PAUSED);
                    getAnimation(false, binding.imageBg, binding.button);
                    new PrefManagerPlayer(requireContext()).setPlay(false);
                } else {
                    new PrefManagerPlayer(requireContext()).setPlay(true);
                    getAnimation(true, binding.imageBg, binding.button);
                    player.buildNotification(PlaybackStatus.PLAYING);
                    //create the play action
                    player.mediaPlayer.start();
                }
            } catch (Exception  e){}
        });
        init();
        db.collection("wpPosts")
                .get()
//                .addOnCompleteListener(task -> {
//
//                })
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        try {
                            List<ModelCollections> types = queryDocumentSnapshots.toObjects(ModelCollections.class);
                            Collections.reverse(types);
                            AdapterHomeAlbumList adapter = new AdapterHomeAlbumList(requireContext(), types);
                            binding.recyclerView.setAdapter(adapter);
                            adapter.setClickListener((view, position) -> getShareDialog(types.get(position)));
                        } catch (Exception ignored) {
                        }
                    }
                });
        getStations();
        return root;
    }

    private void playAudio(String media) {
        if (!serviceBound) {

            Intent playerIntent = new Intent(requireContext(), MediaPlayerServiceNew.class);
            playerIntent.putExtra("media", media);
            requireContext().startService(playerIntent);
            requireContext().bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Store the new audioIndex to SharedPreferences
            StorageUtil storage = new StorageUtil(player);
            storage.storeAudioIndex(player.audioIndex);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            requireActivity().sendBroadcast(broadcastIntent);
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            requireContext().unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mediaPlayerService = binder.getService();
            serviceBound = true;

            Glide.with(requireContext()).asGif().load(R.raw.loading_buffering).into(binding.button);

            mediaPlayerService.mediaPlayer.setOnPreparedListener(mp -> {
                getAnimation(true, binding.imageBg, binding.button);
                mediaPlayerService.playMedia();
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    private void getStations() {
        Call<ModelStations> call = RetrofitClientStations
                .getInstance()
                .getApiService()
                .getStations();
        call.enqueue(new Callback<ModelStations>() {
            @Override
            public void onResponse(@NotNull Call<ModelStations> call, @NotNull Response<ModelStations> response) {
                try {
                    binding.textViewName.setText(Objects.requireNonNull(response.body()).getCurrentTrack().getTitle().split("- ")[1]);
                    binding.textViewSinger.setText(response.body().getCurrentTrack().getTitle().split("- ")[0]);
                    Glide.with(requireContext())
                            .load(response.body().getCurrentTrack().getArtworkUrlLarge())
                            .into(binding.image);
                    response.body().getHistory().remove(0);
                } catch (Exception ignored) { }
            }

            @Override
            public void onFailure(@NotNull Call<ModelStations> call, @NotNull Throwable t) {
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        } catch (Exception ignored) { }
    }

    private void init() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(requireContext());
    }

    private void getShareDialog(ModelCollections model) {
        LayoutInflater factory = LayoutInflater.from(requireContext());
        final View view = factory.inflate(R.layout.dialog_share_album, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(requireContext()).create();
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.setView(view);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(model.getPost_title());
        TextView textViewSinger = view.findViewById(R.id.textViewSinger);
        textViewSinger.setText(model.getAuthor().getDisplay_name());
        TextView textViewSingerDate = view.findViewById(R.id.textViewSingerDate);

        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date newDate = null;
        try {
            newDate = spf.parse(model.getPost_date().split(" ")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String date = spf.format(Objects.requireNonNull(newDate));
        textViewSingerDate.setText(date);

        ImageView image = view.findViewById(R.id.image);
        Glide.with(requireContext())
                .load(model.getPost_content().split("src=\"")[1].split("\"")[0])
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(image);
        view.findViewById(R.id.button).setOnClickListener(v -> {
            share(requireContext(), model.getPermalink());
            deleteDialog.dismiss();
        });
        view.findViewById(R.id.imageClose).setOnClickListener(v -> deleteDialog.dismiss());

        deleteDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}