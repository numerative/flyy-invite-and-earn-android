package com.theflyy.inviteearndemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.theflyy.inviteearndemo.databinding.ActivitySetupBinding;

import theflyy.com.flyy.Flyy;
import theflyy.com.flyy.helpers.FlyyReferrerDataListener;

public class SetupActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    private ActivitySetupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());

        sharedPref = this.getSharedPreferences(getString(R.string.pref), Context.MODE_PRIVATE);

        binding = ActivitySetupBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        getSupportActionBar().setTitle(R.string.sdk_setup);

        String packageName = sharedPref.getString(getString(R.string.package_name), "");
        String partnerId = sharedPref.getString(getString(R.string.partner_id), "");
        if (!packageName.isEmpty() && !partnerId.isEmpty()) {
            initialize(packageName, partnerId);
            navigateToHome();
        }


        binding.llNext.setOnClickListener(v ->
        {
            String mPackageName = binding.editTextPackageName.getText().toString();
            String mPartnerId = binding.editTextPartnerId.getText().toString();
            if (!mPackageName.isEmpty() && !mPartnerId.isEmpty()) {
                initialize(mPackageName, mPartnerId);
                navigateToHome();
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initialize(String packageName, String partnerId) {

        Flyy.setPackageName(packageName);
        Flyy.init(getApplicationContext(), partnerId, Flyy.STAGE, new FlyyReferrerDataListener() {
            @Override
            public void onReferrerDataReceived(String s) {

            }

            @Override
            public void onReferralCodeWithDataReceived(String[] strings) {
                Flyy.verifyReferralCode(SetupActivity.this, strings[0], (isValid, referralCode) ->
                {
                    if (isValid) {
                        Toast.makeText(SetupActivity.this, referralCode + " is valid.", Toast.LENGTH_SHORT).show();
                        sharedPref.edit().putString(getString(R.string.key_ref_code), referralCode).commit();
                    } else {
                        Toast.makeText(SetupActivity.this, "Referral Code is invalid.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(SetupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}