package edu.masanz.da.en;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class TxatClientHandler extends Thread {

    private Socket socket;
    private PrintWriter out;
    private Map<String,PrintWriter> mapClientsWriters;
    private String clientName;

    public TxatClientHandler(Socket socket, Map<String,PrintWriter> mapClientsWriters) {
        this.socket = socket;
        this.mapClientsWriters = mapClientsWriters;
    }

    public void run() {
        System.out.println("Nuevo cliente conectado: " + socket.getInetAddress());
        try (Scanner in = new Scanner(socket.getInputStream())) {
            out = new PrintWriter(socket.getOutputStream(), true);

            registerClient(in);

            while (in.hasNextLine()) {
                String message = in.nextLine();
                System.out.printf("Mensaje recibido de %s> %s \n", clientName, message);

                if (message.startsWith("/")) {
                    processCommand(message);
                    continue;
                }

                broadcast(clientName + ": " + message);
            }
        } catch (IOException e) {
            System.out.println("Error en la conexión con un cliente.");
        } finally {
            closeConnection();
        }
    }

    private void processCommand(String message) {
        String cmd = message.split("\\s+")[0].substring(1);
        switch (cmd) {
            case "MAP":
                sendMap();
                break;
            case "PWD":
                sendWhereAmI();
                break;
            case "KILL":
                break;
            case "MOVE":
                if (message.split("\\s+").length != 2) {
                    break;
                }
                String par1 = message.split("\\s+")[1];
                moveSala(par1);
                break;
        }
    }

    private void moveSala(String nombreSala) {
        boolean b = GameManager.getInstance().move(clientName, nombreSala);
        if (b) {
            out.println("Ya estás en " + nombreSala);
        }else{
            out.println("No puedes ir a " + nombreSala);
        }
    }

    private void sendWhereAmI() {
        out.println(GameManager.getInstance().whereIsJugador(clientName));
    }

    private void sendMap() {
        out.println(GameManager.getInstance().getMapaTextual());
    }

    private void registerClient(Scanner in) {
        while (true) {
            if (in.hasNextLine()) {
                String proposedName = in.nextLine().trim();
                synchronized (mapClientsWriters) {
                    if (!proposedName.isEmpty() && !mapClientsWriters.containsKey(proposedName)) {
                        clientName = proposedName;
                        mapClientsWriters.put(clientName, out);
                        System.out.println("Cliente " + clientName + " conectado.");
                        out.println("OK");

                        GameManager.getInstance().addJugador(clientName);

                        break;
                    }else {
                        out.println("NOK");
                    }
                }
            }
        }
    }

    private void broadcast(String message) {
        synchronized (mapClientsWriters) {
            for (PrintWriter writer : mapClientsWriters.values()) {
                writer.println(message);
            }
        }
    }

    private void listarConectados() {
        StringBuilder sb = new StringBuilder();
        sb.append("Clientes conectados: ");
        synchronized (mapClientsWriters) {
//            for (String name : mapClientsWriters.keySet()) {
//                sb.append(name).append(", ");
//            }
            sb.append(String.join(", ", mapClientsWriters.keySet()));
        }
        out.print(sb.toString());
    }

    private void closeConnection() {
        if (clientName != null) {
            synchronized (mapClientsWriters) {
                mapClientsWriters.remove(clientName);
            }
            System.out.println("Cliente " + clientName + " desconectado.");
        }
        try { socket.close(); } catch (IOException e) { }
    }


}