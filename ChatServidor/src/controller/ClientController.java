package controller;

import java.net.*;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import application.EchoServerTCP_GUI;
import model.User;

public class ClientController {
    private static final Set<String> connectedClients = Collections.synchronizedSet(new HashSet<>());
    private static final List<ClientController> clientControllers = new ArrayList();
    
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferredWriter;
    
    private User user;
    
    public ClientController(Socket socket){
    	
    	try {
    		this.socket = socket;
    		this.bufferredWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    		this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    		this.user = null;
    	} catch(IOExcption e){
    		closeEverything(this.socket, this.bufferedReader, this.bufferredWriter);
    	}
    	
    }
    
    public static void addClient(String ipPort) {
        connectedClients.add(ipPort);
        EchoServerTCP_GUI.addClient(ipPort);
    }

    public static void removeClient(String ipPort) {
        connectedClients.remove(ipPort);
        EchoServerTCP_GUI.removeClient(ipPort);
    }

    public static Set<String> getClients() {
        return new HashSet<>(connectedClients);
    }
}
