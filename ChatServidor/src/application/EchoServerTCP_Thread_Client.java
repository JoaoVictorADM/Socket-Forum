package application;

import java.io.*;
import java.net.*;
import com.google.gson.JsonObject;

public class EchoServerTCP_Thread_Client {

    public static void main(String[] args) throws IOException {

        System.out.println("Qual o IP do servidor? ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String serverIP = br.readLine();

        System.out.println("Qual a Porta do servidor? ");
        int serverPort = Integer.parseInt(br.readLine());

        System.out.println("Tentando conectar com host " + serverIP + " na porta " + serverPort);

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverIP, serverPort);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host " + serverIP + " não encontrado!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Não foi possível reservar I/O para conectar com " + serverIP);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("Conectado.");
        
        JsonObject json1 = new JsonObject();
        json1.addProperty("op", "000");
        json1.addProperty("user", "testevictor");
        json1.addProperty("pass", "123456");
        
        out.println(json1.toString());

        // Lê resposta do servidor
        String response1 = in.readLine();
        System.out.println("Servidor respondeu: " + response1);
        

        while(true){
            
            System.out.print("Id pai: ");
            String id = stdIn.readLine();
            
            if(id.equals("a"))
            	break;
            
            System.out.print("Token: ");
            String token = stdIn.readLine();
            
            // Cria o JSON com Gson
            JsonObject json = new JsonObject();
            json.addProperty("op", "070");
            json.addProperty("id", id);
            json.addProperty("token", token);

            // Envia o JSON como string
            out.println(json.toString());

            // Lê resposta do servidor
            String response = in.readLine();
            System.out.println("Servidor respondeu: " + response);
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}

