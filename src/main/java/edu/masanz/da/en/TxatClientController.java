package edu.masanz.da.en;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TxatClientController {

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnSend;

    @FXML
    private TextArea txaTxat;

    @FXML
    private TextField txtMessage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtServer;


    private boolean isConnected = false;

    private int port;
    private String server;
    private String userName;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread listenerThread;


    @FXML
    private void initialize(){
        txaTxat.setEditable(false);
        setConnected(isConnected);
    }

    private void setConnected(boolean b) {
        isConnected = b;
        btnConnect.setText(b ? "Desconectarse" : "Conectarse");
        txtServer.setDisable(b);
        txtPort.setDisable(b);
        txtName.setDisable(b);
        txtMessage.setDisable(!b);
        btnSend.setDisable(!b);
    }

    @FXML
    void handleConnectBtn(ActionEvent event) {
        if (isConnected) {
            disconnect();
        }else {
            connect();
        }
    }

    private void connect() {
        server = txtServer.getText();
        if (server.trim().isEmpty()) {
            txtServer.requestFocus();
            return;
        }
        try {
            port = Integer.parseInt(txtPort.getText());
        } catch (NumberFormatException e) {
            txtPort.requestFocus();
            return;
        }
        userName = txtName.getText();
        if (userName.trim().isEmpty()) {
            txtName.requestFocus();
            return;
        }
        //setConnected(true);
        new Thread(()-> connectInBackground()).start();
    }

    private void connectInBackground() {
        System.out.println("Conectando al servidor " + server + ":" + port);
        try {
            socket = new Socket(server, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(userName);

            String respuesta = in.readLine();

            if (!respuesta.equals("OK")) {
                closeResources();
                Platform.runLater(() -> {
                    setConnected(false);
                    appendMessage("Nombre " + userName + " no disponible.");
                });
                return;
            }

            Platform.runLater(() -> {
                setConnected(true);
                appendMessage("Conectado al servidor.");
            });

            startListening();

        } catch (IOException e) {
            System.out.println("No se pudo conectar al servidor. ¿Está encendido?");
        }
    }

    private void startListening() {
        listenerThread = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    String finalLine = line;
                    Platform.runLater(() -> appendMessage(finalLine));
                }
            } catch (IOException e){
                if (isConnected) {
                    Platform.runLater(()->appendMessage("Conexión cerrada por el servidor."));
                }
            } finally {
                Platform.runLater(()->setConnected(false));
                closeResources();
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    private void appendMessage(String s) {
        txaTxat.appendText(s + "\n");
    }

    private void closeResources() {
        safeClose(in);
        safeClose(out);
        safeClose(socket);
    }

    private void safeClose(AutoCloseable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (Exception e) {}
    }

    private void disconnect() {
        System.out.println("desconectado :(");
        if (out != null) {
            out.println("BYE");
        }
        closeResources();
        setConnected(false);
    }

    @FXML
    void handleSendBtn(ActionEvent event) {
        if (!isConnected || out == null) {
            return;
        }
        String message = txtMessage.getText();
        if (message.trim().isEmpty()) {
            return;
        }

        out.println(message);
        txtMessage.clear();

        if (message.equals("BYE")) {
            disconnect();
        }
    }

}
