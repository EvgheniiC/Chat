package com.network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection {

    private final Socket socket;
    private  final Thread rxThread;//Thread hört bericht
    private final BufferedReader in;
    private final BufferedWriter out;
    private final TCPConnectionLister eventListner;

    public TCPConnection (TCPConnectionLister eventListner, String ipAdresse, int port ) throws IOException{
        this(eventListner, new Socket(ipAdresse,port) );

    }

    public TCPConnection(TCPConnectionLister eventListner,Socket socket) throws IOException {
        this.eventListner = eventListner;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),Charset.forName("UTF-8")));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {

                    try {
                        eventListner.onConnectionsReade(TCPConnection.this);
                        while (!rxThread.isInterrupted()){
                            String messege = in.readLine();
                            eventListner.onReciveString(TCPConnection.this,messege);
                        }

                    } catch (IOException e) {
                        eventListner.onException(TCPConnection.this,e);
                    }
                    finally {
                        eventListner.onDissconnect(TCPConnection.this);
                    }

            }
        });
        rxThread.start();
    }

    public synchronized void sendMessage(String value){
        try {
            out.write(value + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListner.onException(TCPConnection.this,e);
            dissconnect();
        }
    }

    private synchronized  void dissconnect(){
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListner.onException(TCPConnection.this,e);
        }

    }

    @Override
    public String toString() {
        return "TCPConnection{" +
                "socket=" + socket.getInetAddress() + " " + socket.getPort() +
                '}';
    }
}
