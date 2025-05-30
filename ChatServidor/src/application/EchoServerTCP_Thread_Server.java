package application;

import java.net.*;
import java.io.*;
import controller.ClientController;
import controller.DBConnectionController;

public class EchoServerTCP_Thread_Server extends Thread {
    private static ServerSocket serverSocket;
    private int port; 

    public EchoServerTCP_Thread_Server(int port) {
        this.port = port; 
    }

    @Override
    public void run(){ 
        try{
            serverSocket = new ServerSocket(this.port);
            System.out.println("Servidor iniciado na porta " + this.port);
            DBConnectionController.getConnection();
            
            while (!EchoServerTCP_Thread_Server.serverSocket.isClosed()){
                Socket client = serverSocket.accept();
                System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress() + ":" + client.getPort());
                new ClientController(client).start();
            }
        } catch (IOException e){
            System.err.println("Erro ao iniciar ou rodar o servidor: " + e.getMessage());
        } finally{
            if(serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e){
                    System.err.println("Erro ao fechar server socket: " + e.getMessage());
                }
            }
        }
    }
}