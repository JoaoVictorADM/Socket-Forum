package application;

import java.net.*;

import controller.AuthController;
import controller.DBConnectionController;
import java.sql.Connection;
import java.io.*;
import model.User;
import exception.AuthException;

public class EchoServerTCP_Thread_Server extends Thread {
	private Socket clientSocket;
    private static ServerSocket serverSocket;

    public EchoServerTCP_Thread_Server(int porta) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(porta);
                System.out.println("Servidor iniciado na porta " + porta);
                
                //Iniciar conexão com banco de dados
                Connection connection = DBConnectionController.getConnection();
                
                /*Teste de cadastro e login*/
                User user = null;
                
                try{
                	user = AuthController.getInstance().register(new User("teste123", "Teste", "testesenha"));
                } catch(AuthException ae){
                	System.out.println(ae.getMessage());
                }
                
                if(user != null) {
                	System.out.printf("User: %s - Nick: %s - Senha: %s - Admin: %s", user.getUser(), user.getNick(), user.getPassword(), user.isAdministrator());
                }
                
                
                while (true) {
                    Socket client = serverSocket.accept();
                    System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress());
                    new EchoServerTCP_Thread_Server(client);
                }
            } catch (IOException e) {
                System.err.println("Erro ao iniciar servidor: " + e.getMessage());
            }
        }).start();
    }

    // Construtor
    private EchoServerTCP_Thread_Server(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }

    @Override
    public void run() {
    	String clientIdentifier = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
        EchoServerTCP_GUI.addClient(clientIdentifier);  // <-- Adiciona na UI

        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Servidor recebeu: " + inputLine);
                out.println(inputLine.toUpperCase());
                if (inputLine.equalsIgnoreCase("BYE")) break;
            }
        } catch (IOException e) {
            System.err.println("Erro de comunicação com cliente: " + clientIdentifier);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
            EchoServerTCP_GUI.removeClient(clientIdentifier);  // <-- Remove da UI
        }
    }
}
