package com.theflyy.inviteearndemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import theflyy.com.flyy.Flyy;
import theflyy.com.flyy.helpers.FlyyReferralCountFetchedListener;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Home");
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
        Flyy.sendEvent("invite_demo", "true");
    }
}