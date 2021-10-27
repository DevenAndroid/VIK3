package com.vik3.ui.activities;

import static com.vik3.utils.CommonMethods.isValidEmail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.vik3.MainActivity;
import com.vik3.R;
import com.vik3.apiClient.RetrofitClient;
import com.vik3.databinding.ActivityRegistrationBinding;
import com.vik3.prefraceMaager.PreferenceManager;
import com.vik3.ui.models.ModelLogIResponse;
import com.vik3.ui.models.ModelRegisterParameter;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        Glide.with(this).asGif().load(R.raw.img_logo_login).into(binding.imageLogo);

        binding.button.setOnClickListener(v -> getValidations());
    }

    public void getValidations() {
        if (Objects.requireNonNull(binding.editTextName.getText()).toString().trim().isEmpty()) {
            binding.editTextName.setError("Please Enter Name");
            binding.editTextName.requestFocus();
        } else if (Objects.requireNonNull(binding.editTextEmail.getText()).toString().isEmpty()) {
            binding.editTextEmail.setError("Please Enter Email");
            binding.editTextEmail.requestFocus();
        } else if (!isValidEmail(binding.editTextEmail.getText().toString())) {
            binding.editTextEmail.setError("Please Enter Valid Email");
            binding.editTextEmail.requestFocus();
        } else if (Objects.requireNonNull(binding.editTextPassword.getText()).toString().isEmpty()) {
            binding.editTextPassword.setError("Please Enter Password");
            binding.editTextPassword.requestFocus();
        } else if (Objects.requireNonNull(binding.editTextConfirmPassword.getText()).toString().isEmpty()) {
            binding.editTextConfirmPassword.setError("Please Enter Confirm Password");
            binding.editTextConfirmPassword.requestFocus();
        } else if (!binding.editTextPassword.getText().toString().equals(binding.editTextConfirmPassword.getText().toString())) {
            binding.editTextConfirmPassword.setError("Password not matched");
            binding.editTextConfirmPassword.requestFocus();
        } else {
            register();
        }
    }

    private void register() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        Call<ModelLogIResponse> call = RetrofitClient
                .getInstance()
                .getApiService()
                .register(new ModelRegisterParameter("Subscriber", Objects.requireNonNull(binding.editTextName.getText()).toString(),
                        "",
                        Objects.requireNonNull(binding.editTextEmail.getText()).toString(),
                        Objects.requireNonNull(binding.editTextPassword.getText()).toString()));
        call.enqueue(new Callback<ModelLogIResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModelLogIResponse> call, @NonNull Response<ModelLogIResponse> response) {
                setData(response, progressDialog);
            }

            @Override
            public void onFailure(@NonNull Call<ModelLogIResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, "Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setData(@NonNull Response<ModelLogIResponse> response, ProgressDialog progressDialog) {
        try {
            progressDialog.dismiss();
            assert response.body() != null;
            if (response.body().getStatus().equals("success")) {
                new PreferenceManager(RegistrationActivity.this).setPrefData(response.body().getData());
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
            Toast.makeText(RegistrationActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(RegistrationActivity.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}