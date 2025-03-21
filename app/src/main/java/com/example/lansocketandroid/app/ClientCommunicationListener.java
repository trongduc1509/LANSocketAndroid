package com.example.lansocketandroid.app;

public interface ClientCommunicationListener {
    void onConnected();

    void onReceivedMessage(String message);

    void onDisconnected();

    void onError(String error);
}
