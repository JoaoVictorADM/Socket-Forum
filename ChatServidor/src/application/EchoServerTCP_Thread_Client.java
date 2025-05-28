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

        // Envia o JSON com op, user e pass usando GSON
        System.out.println("Conectado. Enviando dados de login...");

        System.out.print("Usuário: ");
        String user = stdIn.readLine();

        System.out.print("Senha: ");
        String pass = stdIn.readLine();

        // Cria o JSON com Gson
        JsonObject json = new JsonObject();
        json.addProperty("op", "000");
        json.addProperty("user", user);
        json.addProperty("pass", pass);

        // Envia o JSON como string
        out.println(json.toString());

        // Lê resposta do servidor
        String response = in.readLine();
        System.out.println("Servidor respondeu: " + response);

        // Loop normal
        System.out.println("Digite (\"bye\" para sair)");
        System.out.print("Digite: ");
        while ((userInput = stdIn.readLine()) != null) {
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }

            out.println(userInput);
            String serverResponse = in.readLine();
            System.out.println("Servidor respondeu: " + serverResponse);
            System.out.print("Digite: ");
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}

