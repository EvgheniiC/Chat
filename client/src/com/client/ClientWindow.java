package com.client;

//import javafx.scene.layout.BorderPane;

import com.network.TCPConnection;
import com.network.TCPConnectionLister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionLister {

    private static final String IP_ADRESSE = "89.222.249131";
    private static final int PORT = 8189;
    private static final int WIGHT = 600;
    private static final int HEIGHT = 400;


    public static void main(String[] args)  {


        try {
            SwingUtilities.invokeAndWait((new Runnable() {
                @Override
                public void run() {
                    new ClientWindow();
                }
            }));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fielNickName = new JPasswordField("Evghenii ");
    private final JTextField fielInput = new JPasswordField();

    private TCPConnection connection;

    private ClientWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        log.setEditable(false);
        log.setLineWrap(true);
        fielInput.addActionListener(this);

        add(log, BorderLayout.CENTER);
        add(fielInput, BorderLayout.SOUTH);
        add(fielNickName, BorderLayout.NORTH);

        setVisible(true);

        try {
            connection = new TCPConnection(this,IP_ADRESSE,PORT);
        } catch (IOException e) {
            printMassege("Connection extention: " + e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String mess = fielInput.getText();
        if (mess.equals("")) return;
        fielInput.setText(null);
        connection.sendMessage(fielNickName.getText() + " " + mess);

    }

    @Override
    public void onConnectionsReade(TCPConnection tcpConnection) {
        printMassege("Connection ready ");
    }

    @Override
    public void onReciveString(TCPConnection tcpConnection, String value) {
        printMassege(value);

    }

    @Override
    public void onDissconnect(TCPConnection tcpConnection) {
        printMassege("Connection close ");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMassege("Connection extention: " + e);

    }


    private synchronized void printMassege(String mess){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(mess + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}

