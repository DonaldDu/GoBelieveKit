package com;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.beetle.bauhinia.PeerMessageActivity;
import com.beetle.bauhinia.api.IMHttpAPI;
import com.beetle.bauhinia.db.CustomerMessageDB;
import com.beetle.bauhinia.db.GroupMessageDB;
import com.beetle.bauhinia.db.GroupMessageHandler;
import com.beetle.bauhinia.db.PeerMessageDB;
import com.beetle.bauhinia.db.PeerMessageHandler;
import com.beetle.bauhinia.db.SyncKeyHandler;
import com.beetle.im.IMService;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class IMKIT {
    public static void init(Context context) {
        IMHttpAPI.setAPIURL("http://api.gobelieve.io");

        IMKIT.initDB(context);
        IMKIT.initDeviceID(context);

        IMService.setHost("imnode2.gobelieve.io");
        IMService.getInstance().registerConnectivityChangeReceiver(context);
    }

    private static void initDB(Context context) {
        PeerMessageDB db = PeerMessageDB.getInstance();
        db.setDir(context.getDir("peer", MODE_PRIVATE));
        GroupMessageDB groupDB = GroupMessageDB.getInstance();
        groupDB.setDir(context.getDir("group", MODE_PRIVATE));
        CustomerMessageDB csDB = CustomerMessageDB.getInstance();
        csDB.setDir(context.getDir("customer_service", MODE_PRIVATE));
    }

    private static void initDeviceID(Context context) {
        @SuppressLint("HardwareIds")
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        IMService.getInstance().setDeviceID(androidID);
    }

    public static void showChat(Context context, String token, long sender, String peer_name, long receiver) {
        IMService.getInstance().stop();
        PeerMessageHandler.getInstance().setUID(sender);
        GroupMessageHandler.getInstance().setUID(sender);
        IMHttpAPI.setToken(token);
        IMService.getInstance().setToken(token);

        SyncKeyHandler handler = new SyncKeyHandler(context.getApplicationContext(), "sync_key");
        handler.load();

        HashMap<Long, Long> groupSyncKeys = handler.getSuperGroupSyncKeys();
        IMService.getInstance().clearSuperGroupSyncKeys();
        for (Map.Entry<Long, Long> e : groupSyncKeys.entrySet()) {
            IMService.getInstance().addSuperGroupSyncKey(e.getKey(), e.getValue());
        }
        IMService.getInstance().setSyncKey(handler.getSyncKey());
        IMService.getInstance().setSyncKeyHandler(handler);
        IMService.getInstance().start();

        Intent intent = new Intent(context, PeerMessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("peer_uid", receiver);
        intent.putExtra("peer_name", peer_name);
        intent.putExtra("current_uid", sender);
        context.startActivity(intent);
    }
}
