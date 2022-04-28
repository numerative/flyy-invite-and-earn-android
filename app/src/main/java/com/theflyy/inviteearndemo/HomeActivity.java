package com.theflyy.inviteearndemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theflyy.inviteearndemo.databinding.ActivityHomeBinding;

import theflyy.com.flyy.Flyy;
import theflyy.com.flyy.helpers.FlyyReferralCountFetchedListener;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences preferences;
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Home");

        //Setting Preference Key
        preferences = this.getSharedPreferences(getString(R.string.pref), Context.MODE_PRIVATE);
        String eventKey = preferences.getString(getString(R.string.key_event_key), "kyc_done");

        binding.etEventKey.setText(eventKey);


    }

    public void navigateToOffers(View view) {
        Flyy.navigateToOffersActivity(view.getContext());
    }

    public void navigateToHistory(View view) {
        Flyy.navigateToReferralHistoryActivity(view.getContext());
    }

    public void showRererralCount(View view) {
        Flyy.getReferralCount(this, new FlyyReferralCountFetchedListener() {
            @Override
            public void onReferralCountFetched(int referralsCount) {
                Toast.makeText(view.getContext(), "" + referralsCount, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String failureMessage) {

            }
        });
    }

    public void triggerEvent(View view) {
        //Note: Only use the sendEvent method in Staging. For production, make
        //an API call from your backend.
        //See: https://docs.theflyy.com/docs/create-and-track-your-first-offer#send-event-on-checkout
        String eventKey = binding.etEventKey.getText().toString();
        if (eventKey.isEmpty()) {
            Toast.makeText(this, "Enter Event Key", Toast.LENGTH_SHORT).show();
        } else {
            //Send Event
            Flyy.sendEvent(binding.etEventKey.getText().toString(), "true");

            //Store Event Key for Future.
            preferences.edit().putString(getString(R.string.key_event_key), eventKey).apply();
        }
    }
}