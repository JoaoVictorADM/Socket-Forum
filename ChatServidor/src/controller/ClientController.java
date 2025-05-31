package controller;

import java.net.*;
import utils.ResponseGenerator;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import application.EchoServerTCP_GUI;
import exception.AuthException;
import exception.BaseException;
import exception.UserException;
import model.ResponseMessage;
import model.Topic;
import model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import utils.MessageProcessor;
import exception.MessageException;

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
    			
                if(messageFromClient != null) {
                	this.processMessage(messageFromClient);
                } else {
                    throw new IOException("Socket encerrado pelo cliente.");
                }
                     
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
    	EchoServerTCP_GUI.removeClient(ipPort);
    	ClientController.connectedClients.remove(ipPort);
    	ClientController.clientControllers.remove(clientController);
    }
    
    
    @SuppressWarnings("unchecked")
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
        	this.sendJsonResponseToClient(ResponseGenerator.generateErrorResponse(AE));
        	return;
        } catch(BaseException e){
            if(e instanceof AuthException){
                System.out.println("AuthException");
            } 
            
            if(e instanceof UserException){
                System.out.println("UserException");
            } 
            
            if(e instanceof MessageException){
            	System.out.println("MessageException");
            }
            
            this.sendJsonResponseToClient(ResponseGenerator.generateErrorResponse(e));
            
            return;
        } catch(Exception e){
        	System.out.println("Exceção base Client Controller, Processe message");
        	
        	return;
        }
        
        switch(op){
            case "000":
            	this.user = (User)response;
            	System.out.printf("User: %s - Nick: %s - Senha: %s - Administrador: %s - Token: %s", this.user.getUser(), this.user.getNick(), this.user.getPassword(), this.user.isAdministrator(), this.user.getToken());
            	this.sendJsonResponseToClient(ResponseGenerator.generateLoginSuccessResponse(this.user));
                break;
                
            case "010":
            	this.sendJsonResponseToClient(ResponseGenerator.generateRegisterSuccessResponse());
                break;
                
            case "020":
            	this.sendJsonResponseToClient(ResponseGenerator.generateLogoutSuccessResponse());
                break;
                
            case "030":
            	this.sendJsonResponseToClient(ResponseGenerator.generateUpdateUserSuccessResponse());
            	break;
            	
            case "040":
            	this.sendJsonResponseToClient(ResponseGenerator.generateDeleteUserSuccessResponse());
            	break;
            	
            case "050":
            	this.sendJsonResponseToClient(ResponseGenerator.generateCreateTopicSuccessResponse());
            	break;
            	
            case "060":
            	this.sendJsonResponseToClient(ResponseGenerator.generateCreateResponseMessageSuccessResponse());
            	break;
            	
            case "070":
            	this.sendJsonResponseToClient(ResponseGenerator.generateGetResponseMessagesByParentIdSuccesResponse((List<ResponseMessage>)response));
            	break;
            	
            case "075":
            	this.sendJsonResponseToClient(ResponseGenerator.generateGetAllTopicsSuccesResponse((List<Topic>)response));
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
    	
    	System.out.println("Fechando tudo");
    	
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
    
    private void sendJsonResponseToClient(String jsonString) {
        try{
            if(this.socket.isConnected() && this.bufferredWriter != null){
                this.bufferredWriter.write(jsonString);
                this.bufferredWriter.newLine();
                this.bufferredWriter.flush(); 
                System.out.printf("Servidor enviou: %s%n", jsonString);
            } else{
                System.out.println("Não foi possível enviar resposta, socket desconectado ou writer nulo.");
            }
        } catch (IOException ioe) {
            System.err.println("Erro ao enviar mensagem para o cliente: " + ioe.getMessage());
        }
    }
    
    
}
