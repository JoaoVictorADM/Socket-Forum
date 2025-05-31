package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.AuthController;
import exception.AuthException;
import model.Topic;
import model.User;
import exception.UserException;
import controller.UserController;
import exception.MessageException;
import controller.MessageController;
import model.ResponseMessage;

public abstract class MessageProcessor{

	public static Object processMessage(String op, JsonObject jsonObject) throws Exception{
        

        switch(op){
            case "000":
                try{
                	return MessageProcessor.handleLogin(jsonObject);
                } catch(Exception e){
            		throw e;
            	} 
           
            case "010":
            	try{
                	return MessageProcessor.handleRegister(jsonObject);
                } catch(Exception e){
            		throw e;
            	} 
            
            case "020":
            	try{
            		return MessageProcessor.handleLogout(jsonObject);
            	} catch(NullPointerException e){
            		throw e;
            	} catch(Exception e){
            		throw e;
            	} 
            	
            case "030":
            	try{
            		return MessageProcessor.handleUpdateUser(jsonObject);
            	} catch(Exception e){
            		throw e;
            	} 
            	
            case "040":
            	try{
            		return MessageProcessor.handleDeleteUser(jsonObject);
            	} catch(Exception e){
            		throw e;
            	} 
            	
            case "050":
            	try{
            		return MessageProcessor.handleCreateTopic(jsonObject);
            	} catch(Exception e){
            		throw e;
            	} 
            	
            case "060":
            	try{
            		return MessageProcessor.handleCreateResponseMessage(jsonObject);
            	} catch(Exception e){
            		throw e;
            	}
            	
            case "070":
            	try{
            		return MessageProcessor.handleGetResponseMessagesByParentId(jsonObject);
            	} catch(Exception e){
            		throw e;
            	}
            	
            case "075":
            	try{
            		return MessageProcessor.handleGetAllTopics(jsonObject);
            	} catch(Exception e){
            		throw e;
            	}
            	
            default:
                System.out.println("Operação nao reconhecida - Classe MessageProcessor");
                return null;
        }
    }
	
	private static Object handleLogin(JsonObject jsonObject) throws AuthException{
	    try{
	        String user = jsonObject.get("user").getAsString();
	        String pass = jsonObject.get("pass").getAsString();

	        return AuthController.login(user, pass);
	    } catch(AuthException AE){
	    	throw AE;
	    } catch(NullPointerException e){
	        throw new AuthException("002", "Json enviado no formato errado");   
	    } catch(Exception e) {
	    	System.out.println("Exceção handle login - Message Processor");
	    	throw e;
	    }
	}
	
	private static Object handleRegister(JsonObject jsonObject) throws AuthException{
		try{
	        String user = jsonObject.get("user").getAsString();
	        String nick = jsonObject.get("nick").getAsString();
	        String pass = jsonObject.get("pass").getAsString();
	        	
	        AuthController.register(new User(user, nick, pass));
	        
	        return null;
	        		
	    } catch(NullPointerException e){
	        throw new AuthException("012", "Json enviado no formato errado");   
	    } catch(AuthException AE){
	    	throw AE;
	    } catch(Exception e){
	    	System.out.println("Exceção handle register - Message Processor");
	    	throw e;
	    }
	}
	
	private static Object handleLogout(JsonObject jsonObject) throws AuthException{
		try{
			String user = jsonObject.get("user").getAsString();
	        String token = jsonObject.get("token").getAsString();
	        
	        AuthController.logout(user, token);
	        
	        return null;
			
		} catch(NullPointerException e){
	        throw new AuthException("022", "Json enviado no formato errado");   
	    } catch(AuthException AE){
	    	throw AE;
	    } catch(Exception e) {
	    	System.out.println("Exceção handle logout - Message Processor");
	    	throw e;
	    }
	}
	
	private static Object handleUpdateUser(JsonObject jsonObject) throws UserException{
		try{
			String user = jsonObject.get("user").getAsString();
	        String pass = jsonObject.get("pass").getAsString();
	        String newNick = jsonObject.get("new_nick").getAsString();
	        String newPass = jsonObject.get("new_pass").getAsString();
	        
	        UserController.updateUser(user, pass, newNick, newPass);
	        
	        return null;
			
		} catch(NullPointerException e){
	        throw new UserException("032", "Json enviado no formato errado");   
	    } catch(UserException UE){
	    	throw UE;
	    } catch(Exception e){
	    	System.out.println("Exceção handle update user - Message Processor");
	    	throw e;
	    }
	}
	
	public static Object handleDeleteUser(JsonObject jsonObject) throws UserException{
		try{
			String user = jsonObject.get("user").getAsString();
	        String pass = jsonObject.get("pass").getAsString();
	        String token = jsonObject.get("token").getAsString();
	        
	        UserController.deleteUser(user, pass, token);
	        
	        return null;
			
		} catch(NullPointerException e){
	        throw new UserException("042", "Json enviado no formato errado");   
	    } catch(UserException UE){
	        throw UE;
	    } catch(Exception e) {
	    	System.out.println("Exceção handle delete user - Message Processor");
	    	throw e;
	    }
	}
	
	public static Object handleCreateTopic(JsonObject jsonObject) throws MessageException{
		try{
			String title = jsonObject.get("title").getAsString();
	        String subject = jsonObject.get("subject").getAsString();
	        String message = jsonObject.get("msg").getAsString();
	        String token = jsonObject.get("token").getAsString();
	        
	        MessageController.createTopic(new Topic(title, subject, message), token);
	        
	        return null;
			
		} catch(NullPointerException e){
	        throw new MessageException("052", "Json enviado no formato errado");   
	    } catch(MessageException ME){
	        throw ME;
	    } catch(Exception e) {
	    	System.out.println("Exceção handle create topic - Message Processor");
	    	throw e;
	    }
	}
	
	public static Object handleCreateResponseMessage(JsonObject jsonObject) throws MessageException{
		try{
			int parentId = jsonObject.get("id").getAsInt();
	        String message = jsonObject.get("msg").getAsString();
	        String token = jsonObject.get("token").getAsString();
	        
	        MessageController.createResponseMessage(new ResponseMessage(message, parentId), token);
	        
	        return null;
			
		} catch(NullPointerException e){
	        throw new MessageException("062", "Json enviado no formato errado");   
	    } catch(MessageException ME){
	        throw ME;
	    } catch(Exception e) {
	    	System.out.println("Exceção handle create topic - Message Processor");
	    	throw e;
	    }
	}
	
	public static Object handleGetResponseMessagesByParentId(JsonObject jsonObject) throws MessageException{
		try{
	        String token = jsonObject.get("token").getAsString();
	        int parentId = jsonObject.get("id").getAsInt();
	        
	        return MessageController.getResponseMessagesByParentId(parentId, token);
			
		} catch(NullPointerException e){
	        throw new MessageException("072", "Json enviado no formato errado");   
	    } catch(MessageException ME){
	        throw ME;
	    } catch(Exception e) {
	    	System.out.println("Exceção handle Get Response Messages By Parent Id - Message Processor");
	    	throw e;
	    }
	}
	
	public static Object handleGetAllTopics(JsonObject jsonObject) throws MessageException{
		try{
	        String token = jsonObject.get("token").getAsString();
	        
	        return MessageController.getAllTopics(token);
			
		} catch(NullPointerException e){
	        throw new MessageException("077", "Json enviado no formato errado");   
	    } catch(MessageException ME){
	        throw ME;
	    } catch(Exception e) {
	    	System.out.println("Exceção handle Get All Topics - Message Processor");
	    	throw e;
	    }
	}
	
}
