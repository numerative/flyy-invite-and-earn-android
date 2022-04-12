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
            String mPackageName = sharedPref.getString(getString(R.string.package_name), "");
            String mPartnerId = sharedPref.getString(getString(R.string.partner_id), "");
            Flyy.setPackageName(mPackageName);
            Flyy.init(getApplicationContext(), mPartnerId, Flyy.STAGE, new FlyyReferrerDataListener() {
                @Override
                public void onReferrerDataReceived(String s) {
                    System.out.print(s);
                }

                @Override
                public void onReferralCodeWithDataReceived(String[] strings) {
                    for (String string : strings) {
                        System.out.print(string);
                    }
                }
            });
            navigateToHome();
        }


        binding.llNext.setOnClickListener(v ->
        {
            String mPackageName = binding.editTextPackageName.getText().toString();
            String mPartnerId = binding.editTextPartnerId.getText().toString();
            if (!mPackageName.isEmpty() && !mPartnerId.isEmpty()) {
                sharedPref.edit().putString(getString(R.string.package_name), mPackageName).apply();
                sharedPref.edit().putString(getString(R.string.partner_id), mPartnerId).apply();

                Flyy.setPackageName(mPackageName);
                Flyy.init(getApplicationContext(), mPartnerId, Flyy.STAGE, new FlyyReferrerDataListener() {
                    @Override
                    public void onReferrerDataReceived(String s) {
                        System.out.print(s);
                    }

                    @Override
                    public void onReferralCodeWithDataReceived(String[] strings) {
                        for (String string : strings) {
                            System.out.print(string);
                        }
                    }
                });

                navigateToHome();
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void navigateToHome() {
        Intent intent = new Intent(SetupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}