package com.vik3.ui.notifications;

import static com.vik3.utils.CommonMethods.composeTextMessage;
import static com.vik3.utils.CommonMethods.getFacebook;
import static com.vik3.utils.CommonMethods.getInstagram;
import static com.vik3.utils.CommonMethods.getNumber;
import static com.vik3.utils.CommonMethods.getTwitter;
import static com.vik3.utils.CommonMethods.getWebsite;
import static com.vik3.utils.CommonMethods.getWhatsApp;
import static com.vik3.utils.CommonMethods.getYouTubeChannel;
import static com.vik3.utils.CommonMethods.showAlert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.vik3.R;
import com.vik3.databinding.FragmentMoreBinding;
import com.vik3.prefraceMaager.PreferenceManager;
import com.vik3.ui.activities.LogInActivity;
import com.vik3.ui.activities.SettingActivity;
import com.vik3.ui.activities.SoundClubActivity;
import com.vik3.ui.adapters.AdapterMoreContactList;
import com.vik3.ui.fragments.SubscribeFragment;
import com.vik3.ui.models.ModelContactUs;

import java.util.ArrayList;
import java.util.List;

public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (new PreferenceManager(getContext()).isLogIn()){
            binding.imageLogOut.setImageResource(R.drawable.ic_baseline_power_settings_new_24);
        } else {
            binding.imageLogOut.setImageResource(R.drawable.ic_baseline_person_outline_24);
        }

        binding.imageLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new PreferenceManager(getContext()).isLogIn()){
                    logOutDialog();
                } else {
                    startActivity(new Intent(getContext(), LogInActivity.class));
                }
            }
        });

        List<ModelContactUs> list = new ArrayList<>();
        list.add(new ModelContactUs(0, getString(R.string.email_us), R.drawable.img_email));
        list.add(new ModelContactUs(1, getString(R.string.prayer), R.drawable.img_prayer));
        list.add(new ModelContactUs(2, getString(R.string.question_and_feedback), R.drawable.img_question));
        list.add(new ModelContactUs(3, getString(R.string.visit_our_website), R.drawable.img_visit_our_website));
        list.add(new ModelContactUs(4, getString(R.string.text_us), R.drawable.text_us));
        list.add(new ModelContactUs(5, getString(R.string.subscribe), R.drawable.img_subscribe));
        AdapterMoreContactList adapter = new AdapterMoreContactList(requireContext(), list);
        binding.recyclerViewContactUs.setAdapter(adapter);
        adapter.setClickListener((view, position) -> {
            if (position == 0) {
                showAlert(requireContext(), getString(R.string.mail_alert));
//                    composeEmail(requireContext(), "");
            } else if (position == 1) {
                getNumber(requireContext(), "+1 (631) 359-8297");
            } else if (position == 2) {
                getWhatsApp(requireContext(), "+509 3923 3333");
            } else if (position == 3) {
                getWebsite(requireContext(), "https://vik3.media/");
            } else if (position == 4) {
                composeTextMessage(requireContext(), "+509 3923 3333", "");
            } else {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment_activity_main, new SubscribeFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        List<ModelContactUs> listSocial = new ArrayList<>();
        listSocial.add(new ModelContactUs(0, "Facebook", R.drawable.img_facebook));
        listSocial.add(new ModelContactUs(1, "Instagram", R.drawable.img_instagram));
        listSocial.add(new ModelContactUs(2, "Twitter", R.drawable.img_twitter));
        listSocial.add(new ModelContactUs(3, "YouTube", R.drawable.img_you_tube));
        listSocial.add(new ModelContactUs(4, "Soundcloud", R.drawable.img_soundcloud));
        AdapterMoreContactList adapterSocial = new AdapterMoreContactList(requireContext(), listSocial);
        binding.recyclerViewContactOnSocial.setAdapter(adapterSocial);

        adapterSocial.setClickListener((view, position) -> {
            if (position == 0) {
                getFacebook(requireContext(), "RadioVik3", "RadioVik3");
            } else if (position == 1) {//instagram page name
                getInstagram(requireContext(), "radiovik3");
            } else if (position == 2) {
                getTwitter(requireContext(), "RadioVik3");
            } else if (position == 3) {
                getYouTubeChannel(requireContext(), "UCQbgYU4oN4zLjhTjCRHyGaA/featured");
            } else if (position == 4) {
                startActivity(new Intent(getContext(), SoundClubActivity.class));
//                getWebsite(requireContext(), "https://soundcloud.com/radiovik3");
            }
        });

        binding.imageSetting.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SettingActivity.class));
            //                requireActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.nav_host_fragment_activity_main, new SettingFragment())
//                        .addToBackStack(null)
//                        .commit();
        });

        return root;
    }

    private void logOutDialog () {
        new AlertDialog.Builder(getContext())
                .setTitle("Log Out")
                .setMessage("Are you sure want log out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new PreferenceManager(getContext()).logOut();
                        requireActivity().finish();                            }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}