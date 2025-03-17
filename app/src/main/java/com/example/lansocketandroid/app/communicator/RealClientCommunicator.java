package com.example.lansocketandroid.app.communicator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lansocketandroid.app.ClientCommunicationListener;
import com.example.lansocketandroid.app.ClientCommunicator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class RealClientCommunicator implements ClientCommunicator {
    public RealClientCommunicator() {}
    private final Set<ClientCommunicationListener> listeners = new HashSet<>();

    private Socket client;
    private BufferedReader reader;
    private PrintWriter writer;

    @Override
    public void connect(String ipAddress, int port) {
        try {
            client = new Socket(ipAddress, port);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(client.getOutputStream(), true);
            onMessageReceived(wrapMessage("CLIENT", "Connected to server!"));
            receiveMessage();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void disconnect() {
        try {
            client.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void sendMessage(String message) {
        writer.println(message);
        onMessageReceived(wrapMessage("CLIENT", message));
    }

    public void receiveMessage() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                onMessageReceived(wrapMessage("SERVER", message));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void subscribeListener(ClientCommunicationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unsubscribeListener(ClientCommunicationListener listener) {
        listeners.remove(listener);
    }

    private String wrapMessage(String sender, String message) {
        return sender + ": " + message;
    }

    private void onMessageReceived(String message) {
        listeners.forEach(
                listener -> listener.onReceivedMessage(message)
        );
    }
}
