package application;

import java.net.*;
import java.io.*;
import controller.ClientController;

public class EchoServerTCP_Thread_Server extends Thread{
    private static ServerSocket serverSocket;

    public EchoServerTCP_Thread_Server(int porta) {
        new Thread(() -> {
            try{
                serverSocket = new ServerSocket(porta);
                System.out.println("Servidor iniciado na porta " + porta);
                
                while(!EchoServerTCP_Thread_Server.serverSocket.isClosed()){
                    Socket client = serverSocket.accept();
                    
                    System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress() + ":" + client.getPort());
                    
                    new ClientController(client).start();
                }
            } catch(IOException e) {
                System.err.println("Erro ao iniciar servidor: " + e.getMessage());
            }
        }).start();
    }
}
