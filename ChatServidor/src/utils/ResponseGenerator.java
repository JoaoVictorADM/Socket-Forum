package utils;

import com.google.gson.JsonObject;
import exception.BaseException;
import model.User;

public abstract class ResponseGenerator{

	public static String generateErrorResponse(BaseException e){
	
		try{
			JsonObject errorResponse = new JsonObject();
			
	        errorResponse.addProperty("op", e.getOp());
	        errorResponse.addProperty("msg", e.getMessage()); 
	        
	        return errorResponse.toString();
		} catch(Exception e1) {
			System.out.println("Exceção na hora de gerar resposta de erro");
			return "";
		}
		
	}
	
	public static String genereLoginSucessResponse(User user){
		try{
			JsonObject response = new JsonObject();
			
	        response.addProperty("op", "001");
	        response.addProperty("token", user.getToken()); 
	        
	        return response.toString();
		} catch(Exception e) {
			System.out.println("Exceção na hora de gerar resposta de sucesso para login");
			return "";
		}
	}
	
	public static String genereRegisterSucessResponse(){
		try{
			JsonObject response = new JsonObject();
			
	        response.addProperty("op", "011");
	        
	        return response.toString();
		} catch(Exception e) {
			System.out.println("Exceção na hora de gerar resposta de sucesso para registro");
			return "";
		}
	}
	
	public static String genereLogoutSucessResponse(){
		try{
			JsonObject response = new JsonObject();
			
	        response.addProperty("op", "021");
	        
	        return response.toString();
		} catch(Exception e) {
			System.out.println("Exceção na hora de gerar resposta de sucesso para registro");
			return "";
		}
	}
}
