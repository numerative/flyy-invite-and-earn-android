package com.theflyy.inviteearndemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theflyy.inviteearndemo.databinding.ActivityLoginBinding;

import theflyy.com.flyy.Flyy;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Sign Up");

        binding.llSignup.setOnClickListener(v -> {
            String userId = binding.etUserId.getText().toString();
            String userName = binding.etUserName.getText().toString();
            if (!userId.isEmpty()) {
                Flyy.setNewUser(userId);

                if (!userName.isEmpty()) {
                    Flyy.setUsername(userName);
                }
            } else {
                Toast.makeText(this, "User Id cannot be Empty", Toast.LENGTH_SHORT).show();
            }

            navigateToHome();
        });

        binding.resetButton.setOnClickListener(v ->
        {
            SharedPreferences preferences = this.getSharedPreferences(getString(R.string.pref), Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            navigateToSetup();
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToSetup() {
        Intent intent = new Intent(LoginActivity.this, SetupActivity.class);
        startActivity(intent);
        finish();
    }

    public void setReferral(View view) {
        Flyy.verifyReferralCode(this, binding.editTextReferral.getText().toString(),
                (isValid, referralCode) -> {
                    if (isValid) {
                        Flyy.setReferrerCode(referralCode);
                        Toast.makeText(this, "Referral Code Applied", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "Invalid Referral Code", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}