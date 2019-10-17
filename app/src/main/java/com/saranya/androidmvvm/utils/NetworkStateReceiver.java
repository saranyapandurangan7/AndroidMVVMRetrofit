package com.saranya.androidmvvm.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashSet;
import java.util.Set;

public class NetworkStateReceiver extends BroadcastReceiver {

    protected Set<Listener> listeners;
    protected Boolean connected;

    public NetworkStateReceiver() {
        listeners = new HashSet<Listener>();
        connected = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null || intent.getExtras() == null)
            return;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        if(ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,Boolean.FALSE)) {
            connected = false;
        }

        notifyStateToAll();
    }

    private void notifyStateToAll() {
        for(Listener listener : listeners)
            notifyState(listener);
    }

    private void notifyState(Listener listener) {
        if(connected == null || listener == null)
            return;

        if(connected)
            listener.notifyNetworkAvailable();
        else
            listener.notifyNetworkUnAvailable();
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
        notifyState(listener);
    }

    public void unregisterListener(Listener l) {
        listeners.remove(l);
    }

    public interface Listener {
        void notifyNetworkAvailable();
        void notifyNetworkUnAvailable();
    }
}
