package com.theflyy.inviteearndemo.services;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import theflyy.com.flyy.helpers.FlyyNotificationHandler;

public class FCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //Add the following lines
        Map<String, String> data = remoteMessage.getData();
        if (data.containsKey("notification_source")
                && data.get("notification_source").equalsIgnoreCase("flyy_sdk")) {
            FlyyNotificationHandler.handleNotification(getApplicationContext(), remoteMessage, null, null);
        }
    }


    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        System.out.print(token);
    }
}