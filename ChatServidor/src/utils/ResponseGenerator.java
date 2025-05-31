package utils;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import exception.BaseException;
import model.ResponseMessage;
import model.Topic;
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
	
	public static String generateLoginSuccessResponse(User user){
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
	
	public static String generateRegisterSuccessResponse(){
		try{
			JsonObject response = new JsonObject();
			
	        response.addProperty("op", "011");
	        
	        return response.toString();
		} catch(Exception e) {
			System.out.println("Exceção na hora de gerar resposta de sucesso para registro");
			return "";
		}
	}
	
	public static String generateLogoutSuccessResponse(){
		try{
			JsonObject response = new JsonObject();
			
	        response.addProperty("op", "021");
	        
	        return response.toString();
		} catch(Exception e) {
			System.out.println("Exceção na hora de gerar resposta de sucesso para logout");
			return "";
		}
	}
	
	public static String generateUpdateUserSuccessResponse(){
		try{
			JsonObject response = new JsonObject();
			
	        response.addProperty("op", "031");
	        
	        return response.toString();
		} catch(Exception e) {
			System.out.println("Exceção na hora de gerar resposta de sucesso atualização de usuário");
			return "";
		}
	}
	
	public static String generateDeleteUserSuccessResponse(){
		try{
			JsonObject response = new JsonObject();
			
	        response.addProperty("op", "041");
	        
	        return response.toString();
		} catch(Exception e) {
			System.out.println("Exceção na hora de gerar resposta de sucesso para exclusão de usuário");
			return "";
		}
	}
	
	
	public static String generateCreateTopicSuccessResponse(){
		try{
			JsonObject response = new JsonObject();
			
	        response.addProperty("op", "051");
	        
	        return response.toString();
		} catch(Exception e) {
			System.out.println("Exceção na hora de gerar resposta de sucesso para criação de tópico");
			return "";
		}
	}
	
	public static String generateCreateResponseMessageSuccessResponse(){
		try{
			JsonObject response = new JsonObject();
			
	        response.addProperty("op", "061");
	        
	        return response.toString();
		} catch(Exception e) {
			System.out.println("Exceção na hora de gerar resposta de sucesso para criação de mensagem de resposta");
			return "";
		}
	}
	
	public static String generateGetResponseMessagesByParentIdSuccesResponse(List<ResponseMessage> responseMessages){
		try{
	        JsonObject response = new JsonObject();
	        response.addProperty("op", "071");

	        JsonArray responseMessagesArray = new JsonArray();

	        for(ResponseMessage responseMessage : responseMessages){
	            JsonObject topicJson = new JsonObject();
	            topicJson.addProperty("id", responseMessage.getId());
	            topicJson.addProperty("msg", responseMessage.getMessage());
	            topicJson.addProperty("nick", responseMessage.getNick());

	            responseMessagesArray.add(topicJson);
	        }

	        response.add("msg_list", responseMessagesArray);

	        return response.toString();
	    } catch(Exception e){
	        System.out.println("Exceção na hora de gerar resposta de sucesso para requisição para pegar todos os tópicos");
	        return "";
	    }
	}
	
	public static String generateGetAllTopicsSuccesResponse(List<Topic> topics){
		try{
	        JsonObject response = new JsonObject();
	        response.addProperty("op", "076");

	        JsonArray topicsArray = new JsonArray();

	        for(Topic topic : topics) {
	            JsonObject topicJson = new JsonObject();
	            topicJson.addProperty("id", topic.getId());
	            topicJson.addProperty("title", topic.getTitle());
	            topicJson.addProperty("subject", topic.getSubject());
	            topicJson.addProperty("msg", topic.getMessage());
	            topicJson.addProperty("nick", topic.getNick());

	            topicsArray.add(topicJson);
	        }

	        response.add("msg_list", topicsArray);

	        return response.toString();
	    } catch(Exception e){
	        System.out.println("Exceção na hora de gerar resposta de sucesso para requisição para pegar todos os tópicos");
	        return "";
	    }
	}
}
