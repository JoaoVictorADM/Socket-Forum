package application;

import java.io.*;
import java.net.*;

public class EchoServerTCP_Thread_Client {

    public static void main(String[] args) throws IOException {

        System.out.println("Qual o IP do servidor? ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String serverIP = br.readLine();

        System.out.println("Qual a Porta do servidor? ");
        br = new BufferedReader(new InputStreamReader(System.in));
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
            System.err.println("Host " + serverIP + " nao encontrado!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Não foi possivel reservar I/O para conectar com " + serverIP);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("Conectado. Digite (\"bye\" para sair)");
        System.out.print("Digite: ");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);

            // end loop
            if (userInput.toUpperCase().equals("BYE"))
                break;

            System.out.println("Servidor retornou: " + in.readLine());
            System.out.print("Digite: ");
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
