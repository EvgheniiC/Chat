package com.server;

import com.network.TCPConnection;
import com.network.TCPConnectionLister;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerChat implements TCPConnectionLister {
    public static void main(String[] args) {

        new ServerChat();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();


    private  ServerChat(){
        System.out.println("Server running ");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true){
                try {
                    new TCPConnection(this,serverSocket.accept());

                } catch (IOException e){
                    System.out.println("TCPConnection Exception " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void  onConnectionsReade(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnection("Client connected " + tcpConnection);
    }

    @Override
    public synchronized void onReciveString(TCPConnection tcpConnection, String value) {
        sendToAllConnection(value);

    }

    @Override
    public synchronized void onDissconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnection("Client connected " + tcpConnection);

    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception " + e);

    }

    private void sendToAllConnection(String value){
        System.out.println(value);
        for (int i = 0; i <connections.size() ; i++) {
            connections.get(i).sendMessage(value);
            
        }
    }
}
