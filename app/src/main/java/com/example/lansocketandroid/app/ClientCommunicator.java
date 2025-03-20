package com.example.lansocketandroid.app;

import androidx.lifecycle.LiveData;

public interface ClientCommunicator {
    Boolean isConnected();

    void connect(String ipAddress, int port);

    void disconnect();

    void sendMessage(String message);

    void subscribeListener(ClientCommunicationListener listener);

    void unsubscribeListener(ClientCommunicationListener listener);
}
