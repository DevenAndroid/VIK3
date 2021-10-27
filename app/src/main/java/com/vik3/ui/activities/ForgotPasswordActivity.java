package com.vik3.ui.activities;

import static com.vik3.utils.CommonMethods.isValidEmail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vik3.R;
import com.vik3.apiClient.RetrofitClient;
import com.vik3.databinding.ActivityForgotPasswordBinding;
import com.vik3.ui.models.ModelForgotPassword;
import com.vik3.ui.models.ModelForgotPasswordResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        getAnimation();

        binding.button.setOnClickListener(v -> {
            if (Objects.requireNonNull(binding.editTextEmail.getText()).toString().isEmpty()) {
                binding.editTextEmail.setError("Please Enter Email");
                binding.editTextEmail.requestFocus();
            } else if (!isValidEmail(binding.editTextEmail.getText().toString())) {
                binding.editTextEmail.setError("Please Enter Valid Email");
                binding.editTextEmail.requestFocus();
            } else {
                forgotPassword();
            }
        });
    }

    private void getAnimation() {
        Glide.with(this).asGif()
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        resource.setLoopCount(1);
                        resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                            @Override
                            public void onAnimationEnd(Drawable drawable) {
                            }
                        });
                        return false;
                    }
                })
                .load(R.raw.img_logo_login).into(binding.imageLogo);
    }

    private void forgotPassword() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        Call<ModelForgotPasswordResponse> call = RetrofitClient
                .getInstance()
                .getApiService()
                .forgotPassword(new ModelForgotPassword(Objects.requireNonNull(binding.editTextEmail.getText()).toString()));
        call.enqueue(new Callback<ModelForgotPasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModelForgotPasswordResponse> call, @NonNull Response<ModelForgotPasswordResponse> response) {
                try {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    if (response.body().getStatus().equals("success")) {
                        new AlertDialog.Builder(ForgotPasswordActivity.this)
                                .setTitle("Forgot Password")
                                .setMessage(response.body().getMessage())
                                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                    startActivity(new Intent(ForgotPasswordActivity.this, LogInActivity.class));
                                    finish();
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    } else {
                        new AlertDialog.Builder(ForgotPasswordActivity.this)
                                .setTitle("Forgot Password")
                                .setMessage(response.body().getMessage())
                                .setNegativeButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }

//                    Toast.makeText(ForgotPasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ForgotPasswordActivity.this, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelForgotPasswordResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}