package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.AuthController;
import exception.AuthException;
import model.User;

public abstract class MessageProcessor{

	public static Object processMessage(String op, JsonObject jsonObject) throws Exception{
        

        switch(op){
            case "000":
                try{
                	return MessageProcessor.handleLogin(jsonObject);
                } catch(AuthException AE){
                	throw new AuthException(AE);
                }
           
            case "010":
            	try{
                	return MessageProcessor.handleRegister(jsonObject);
                } catch(AuthException AE){
                	throw new AuthException(AE);
                }
            
            case "020":
            	try{
            		return MessageProcessor.handleLogout(jsonObject);
            	} catch(AuthException AE){
                	throw new AuthException(AE);
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
	        throw new AuthException(AE);
	    } catch(Exception e) {
	    	System.out.println("Exceção handle login Message Processor");
	    	return null;
	    }
	}
	
	private static Object handleRegister(JsonObject jsonObject) throws AuthException{
		try{
	        String user = jsonObject.get("user").getAsString();
	        String nick = jsonObject.get("nick").getAsString();
	        String pass = jsonObject.get("pass").getAsString();
	        	
	        AuthController.register(new User(user, nick, pass));
	        
	        return null;
	        		
	    } catch(AuthException AE){
	        throw new AuthException(AE);
	    } catch(Exception e) {
	    	System.out.println("Exceção handle register Message Processor");
	    	return null;
	    }
	}
	
	public static Object handleLogout(JsonObject jsonObject) throws AuthException{
		try{
			
			String user = jsonObject.get("user").getAsString();
	        String token = jsonObject.get("token").getAsString();
	        
	        AuthController.logout(user, token);
	        
	        return null;
			
		} catch(AuthException AE){
	        throw new AuthException(AE);
	    } catch(Exception e) {
	    	System.out.println("Exceção handle logout Message Processor");
	    	return null;
	    }
	}
	
}
