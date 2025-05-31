package controller;

import model.Topic;
import model.User;
import model.ResponseMessage;

import java.util.List;

import exception.MessageException;
import utils.TokenGenerator;
import utils.TextValidator;
import repository.AuthRepository;
import repository.MessageRepository;


public abstract class MessageController{

	public static boolean messageExists(int id) throws MessageException{
		return MessageRepository.messageExists(id);
	}
	
	public static void createTopic(Topic topic, String token) throws MessageException{
		
		try{
			if(topic == null)
				throw new MessageException("052", "Objeto topic nulo - Message Controller");
			
			if(topic.getTitle() == null || topic.getSubject() == null || topic.getMessage() == null)
				throw new MessageException("052", "Titulo, Assunto ou messagem nulos");
			
			if(!TextValidator.isValidText(topic.getTitle(), "[a-zA-Z0-9 ]+", 6, 100) ||
			   !TextValidator.isValidText(topic.getSubject(), "[a-zA-Z0-9 ]+", 6, 100) ||
			   !TextValidator.isValidText(topic.getMessage(), "[a-zA-Z0-9 ]+", 6, 100) ||
			   !TokenGenerator.isValidFormatToken(token)) 
			{
				throw new MessageException("052", "Formato de Titulo, Assunto, Mensagem ou token errado");
			}
			
			User tokenUser = AuthRepository.tokens.get(token); 
			if(tokenUser == null)
	            throw new MessageException("052", "Token Não existe");
			
			topic.setUserId(tokenUser.getId());
			MessageRepository.createTopic(topic);
			
		} catch(MessageException mE){
			throw mE;
		} catch(Exception e){
	        System.out.println("Exceção em createTopic controller");
	        throw new MessageException("500", "Erro interno ao tentar criar tópico");
	    }
	}
	
	public static void createResponseMessage(ResponseMessage responseMessage, String token)  throws MessageException{
		try{
			if(responseMessage == null)
				throw new MessageException("062", "Objeto responseMessage nulo - MessageController");
			
			if(responseMessage.getMessage() == null)
				throw new MessageException("062", "Id Pai, ou messagem nulos");
			
			if(responseMessage.getParentId() < 0 || !TextValidator.isValidText(responseMessage.getMessage(), "[a-zA-Z0-9 ]+", 6, 100) | !TokenGenerator.isValidFormatToken(token))
				throw new MessageException("062", "Formato de Id pai, Mensagem ou token errado");
			
			
			if(!MessageController.messageExists(responseMessage.getParentId()))
				throw new MessageException("062", "Não existe mensagem com o id pai enviado");
			
			User tokenUser = AuthRepository.tokens.get(token); 
			if(tokenUser == null)
	            throw new MessageException("062", "Token Não existe");
			
			responseMessage.setUserId(tokenUser.getId());
			MessageRepository.createResponseMessage(responseMessage);
			
		} catch(MessageException mE){
			throw mE;
		} catch(Exception e){
	        System.out.println("Exceção em createTopic controller");
	        throw new MessageException("500", "Erro interno ao tentar criar tópico");
	    }
	}
	
	public static List<Topic> getAllTopics(String token) throws MessageException{
		try{	
			if(!TokenGenerator.isValidFormatToken(token))
				throw new MessageException("077", "Formato do Token errado");
			
			User tokenUser = AuthRepository.tokens.get(token); 
			if(tokenUser == null)
	            throw new MessageException("077", "Token Não existe");
			
			return MessageRepository.getAllTopics();
			
		} catch(MessageException mE){
			throw mE;
		} catch(Exception e){
	        System.out.println("Exceção em getAllTopics controller");
	        throw new MessageException("500", "Erro interno ao tentar recuperar tópicos");
	    }
	}
	
	public static List<ResponseMessage> getResponseMessagesByParentId(int parentId, String token) throws MessageException{
		try{
			if(parentId < 0 ||!TokenGenerator.isValidFormatToken(token))
				throw new MessageException("072", "Formato Id Pai ou Token errados");
			
			if(!MessageController.messageExists(parentId))
				throw new MessageException("072", "Não existe mensagem com o id pai enviado");
			
			User tokenUser = AuthRepository.tokens.get(token); 
			if(tokenUser == null)
	            throw new MessageException("072", "Token Não existe");
			
			return MessageRepository.getResponseMessagesByParentId(parentId);
			
		} catch(MessageException mE){
			throw mE;
		} catch(Exception e){
	        System.out.println("Exceção em getResponseMessagesByParentId controller");
	        throw new MessageException("500", "Erro interno ao tentar recuperar respostas de uma mensagem");
	    }
	}
	
}
