package com.client;

//import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class ClientWindow extends JFrame implements ActionListener {

    private static final String IP_ADRESSE = "89.222.249131";
    private static final int PORT = 8189;
    private static final int WIGHT = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {



        SwingUtilities.invokeAndWait((new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        }));
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fielNickName = new JPasswordField("Evghenii ");
    private final JTextField fielInput = new JPasswordField();

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

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

