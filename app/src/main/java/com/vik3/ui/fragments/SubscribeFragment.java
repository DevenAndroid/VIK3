package com.vik3.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.vik3.MainActivity;
import com.vik3.R;
import com.vik3.apiClient.RetrofitClient;
import com.vik3.databinding.FragmentSubscribeBinding;
import com.vik3.ui.models.ModelResponse;
import com.vik3.ui.models.ModelSubscribeData;
import com.vik3.ui.notifications.MoreFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribeFragment extends Fragment {
    private FragmentSubscribeBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubscribeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.imageBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, new MoreFragment())
                .commit());

        binding.button.setOnClickListener(v -> {
            if (binding.editTextFirstName.getText().toString().isEmpty()) {
                binding.editTextFirstName.setError("Please Enter Name");
                binding.editTextFirstName.requestFocus();
            } else if (binding.editTextEmail.getText().toString().isEmpty()) {
                binding.editTextEmail.setError("Please Enter Email");
                binding.editTextEmail.requestFocus();
            } else {
                subscribeData();
            }
        });

        return view;
    }

    private void subscribeData() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ModelResponse> call = RetrofitClient
                .getInstance()
                .getApiService()
                .subscribeData(new ModelSubscribeData(binding.editTextFirstName.getText().toString(),
                        binding.editTextEmail.getText().toString(),
                        binding.editTextLastName.getText().toString()));
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(@NotNull Call<ModelResponse> call, @NotNull Response<ModelResponse> response) {
                try {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "" + Objects.requireNonNull(response.body()).getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), MainActivity.class));
                    requireActivity().finish();
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ModelResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


}