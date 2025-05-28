package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.AuthController;
import exception.AuthException;

public abstract class MessageProcessor{

	public static Object processMessage(String op, JsonObject jsonObject) throws Exception{
        

        switch(op){
            case "000":
                try{
                	return MessageProcessor.handleLogin(jsonObject);
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

	        return AuthController.getInstance().login(user, pass);
	    } catch(AuthException AE){
	        throw new AuthException(AE);
	    }
	}
	
}
