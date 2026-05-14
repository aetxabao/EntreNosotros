package edu.masanz.da.en;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TxatServer {

    private static final int PORT = 12345;

    // Lista para llevar registro de todos los clientes conectados
    private static Map<String,PrintWriter> mapClientsWriters;

    public TxatServer() {
        mapClientsWriters = new HashMap<>();
    }

    // --- LÓGICA DEL SERVIDOR ---
    private void start() {
        System.out.println("Servidor iniciado en el puerto " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // El servidor se queda esperando una nueva conexión
                Socket socket = serverSocket.accept();
                new TxatClientHandler(socket, mapClientsWriters).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TxatServer().start();
    }

}
