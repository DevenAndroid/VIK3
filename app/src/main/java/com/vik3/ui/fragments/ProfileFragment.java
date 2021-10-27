package com.vik3.ui.fragments;

import static com.vik3.utils.CommonMethods.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.vik3.BuildConfig;
import com.vik3.R;
import com.vik3.databinding.FragmentProfileBinding;
import com.vik3.ui.dashboard.RadioFragment;
import com.vik3.ui.models.ModelArtist;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        ModelArtist modelArtist = (ModelArtist) requireArguments().getSerializable("model");

        binding.textViewName.setText(modelArtist.getTitle());
        binding.textView.setText(modelArtist.getBio());
        Glide.with(requireContext())
                .load(modelArtist.getImage())
                .into(binding.image);

        binding.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(requireContext(),
                        "Now Playing \"" + modelArtist.getTitle().split("- ")[1] + "\" by \"" +
                                modelArtist.getTitle().split("- ")[0] + "\" on Radio VIK3. Download Radio VIK3 via https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "  to the best christian songs. Follow us on Twitter - https://twitter.com/RadioVik3");
            }
        });
        binding.imageBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.nav_host_fragment_activity_main, new RadioFragment())
                .commit());

        return binding.getRoot();
    }
}