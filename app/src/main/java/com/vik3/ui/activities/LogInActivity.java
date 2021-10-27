package com.vik3.ui.activities;

import static com.vik3.utils.CommonMethods.isValidEmail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vik3.MainActivity;
import com.vik3.R;
import com.vik3.apiClient.RetrofitClient;
import com.vik3.databinding.ActivityLogInActivityBinding;
import com.vik3.prefraceMaager.PreferenceManager;
import com.vik3.ui.models.ModelLogIResponse;
import com.vik3.ui.models.ModelLogInParameter;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        getAnimation();

        binding.textViewRegister.setOnClickListener(v -> {
            startActivity(new Intent(LogInActivity.this, RegistrationActivity.class));
        });

        binding.textViewForgotPassword.setOnClickListener(v -> startActivity(new Intent(LogInActivity.this, ForgotPasswordActivity.class)));

        binding.button.setOnClickListener(v -> {
            if (Objects.requireNonNull(binding.editTextEmail.getText()).toString().isEmpty()) {
                binding.editTextEmail.setError("Please Enter Email.");
                binding.editTextEmail.requestFocus();
            } else if (!isValidEmail(binding.editTextEmail.getText().toString())) {
                binding.editTextEmail.setError("Please Enter Valid Email");
                binding.editTextEmail.requestFocus();
            } else if (Objects.requireNonNull(binding.editTextPassword.getText()).toString().isEmpty()) {
                binding.editTextPassword.setError("Please Enter Password.");
                binding.editTextPassword.requestFocus();
            } else {
                getLogIn();
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
                        /*resource.setLoopCount(1);
                        resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                            @Override
                            public void onAnimationEnd(Drawable drawable) {
                                Glide.with(LogInActivity.this).load(R.drawable.img_logo_login_final).into(binding.imageLogo);
                            }
                        });*/
                        return false;
                    }})
                .load(R.raw.img_logo_login).into(binding.imageLogo);
    }

    private void getLogIn() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        Call<ModelLogIResponse> call = RetrofitClient
                .getInstance()
                .getApiService()
                .login(new ModelLogInParameter(Objects.requireNonNull(binding.editTextEmail.getText()).toString(),
                        Objects.requireNonNull(binding.editTextPassword.getText()).toString()));
        call.enqueue(new Callback<ModelLogIResponse>() {
            @Override
            public void onResponse(@NonNull Call<ModelLogIResponse> call, @NonNull Response<ModelLogIResponse> response) {
                try {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    if (response.body().getStatus().equals("success")) {
                        new PreferenceManager(LogInActivity.this).setPrefData(response.body().getData());
                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                        finish();
                    }
                    Toast.makeText(LogInActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LogInActivity.this, "Exception : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelLogIResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LogInActivity.this, "Failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}