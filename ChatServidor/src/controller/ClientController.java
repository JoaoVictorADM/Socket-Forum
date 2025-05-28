package controller;

import java.net.*;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import application.EchoServerTCP_GUI;
import exception.AuthException;
import model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import utils.MessageProcessor;

public class ClientController extends Thread{
    private static final Set<String> connectedClients = Collections.synchronizedSet(new HashSet<>());
    private static final List<ClientController> clientControllers = new ArrayList<>();
    
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferredWriter;
    private String ipPort;
    private User user;
    
    public ClientController(Socket socket){
    	
    	try{
    		this.socket = socket;
    		this.bufferredWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    		this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    		this.ipPort = this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort();
    		this.user = null;
    		ClientController.addClient(this, ipPort);
    	} catch(IOException e){
    		this.closeEverything(this.socket, this.bufferedReader, this.bufferredWriter);
    	}
    	
    }
    
    @Override
    public void run(){
    	
    	String messageFromClient;
    	
    	while(this.socket.isConnected()) {
    		try{
    			messageFromClient = bufferedReader.readLine();
    			
                if(messageFromClient != null)
                    this.processMessage(messageFromClient);
                
        	} catch(IOException e){
        		this.closeEverything(this.socket, this.bufferedReader, this.bufferredWriter);
        		break;
        	}
    	}
    }
    
    public void broadcastMessage(){
    	
    }
   
    private static void addClient(ClientController clientController, String ipPort){
    	ClientController.connectedClients.add(ipPort);
    	ClientController.clientControllers.add(clientController);
        EchoServerTCP_GUI.addClient(ipPort);
    }

    private static void removeClient(ClientController clientController , String ipPort){
    	ClientController.connectedClients.remove(ipPort);
    	ClientController.clientControllers.remove(clientController);
        EchoServerTCP_GUI.removeClient(ipPort);
    }
    
    
    private void processMessage(String json){

    	System.out.printf("Servidor recebeu: %s", json);
    	
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        String op = jsonObject.has("op") ? jsonObject.get("op").getAsString() : null;

        if(op == null){
            System.out.println("JSON não possui atributo 'op'");
            return;
        }

        Object response = null;
        
        try{
        	response = MessageProcessor.processMessage(op, jsonObject);
        } catch(AuthException AE){
        	System.out.println("Exceção de auth");
        } catch(Exception e){
        	e.printStackTrace();
        }
        
        switch(op){
            case "000":
            	this.user = (User)response;
            	System.out.printf("User: %s - Nick: %s - Senha: %s - Administrador: %s - Token: %s", this.user.getUser(), this.user.getNick(), this.user.getPassword(), this.user.isAdministrator(), this.user.getToken());
                break;
            case "sendMessage":
                break;
            case "logout":
                break;
            default:
                System.out.println("Operação desconhecida: " + op);
                break;
        }
    }
    

    public static Set<String> getConnectedClients(){
        return new HashSet<>(ClientController.connectedClients);
    }
    
    public static List<ClientController> getClientControllers(){
    	return new ArrayList<>(ClientController.clientControllers);
    }
    
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
    	
    	ClientController.removeClient(this, this.ipPort);
    	
    	try{
    		if(bufferedReader != null)
    			bufferedReader.close();
    		
    		if(bufferedWriter != null)
    			bufferedWriter.close();
    			
    		if(socket != null)
    			socket.close();
    		
    	} catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    
    
    
}
