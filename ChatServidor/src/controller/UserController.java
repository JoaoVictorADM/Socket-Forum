package controller;

import exception.UserException;
import model.User;
import repository.AuthRepository;
import repository.UserRepository;
import utils.TextValidator;
import utils.TokenGenerator;

public abstract class UserController{

	public static User getUserByUsername(String username) throws UserException{
		
		try{
			if(username == null)
				throw new UserException("-1", "Usuário nulo"); 
			
			return UserRepository.getUserByUsername(username);
		} catch(UserException e){
			throw new UserException(e);
		}
		
	}
	
	public static String getNickById(int id) throws UserException{
		try{
			if(id < 0)
				throw new UserException("-1", "Formato de id inválido - getNick Controller"); 
			
			return UserRepository.getNickById(id);
		} catch(UserException e){
			throw new UserException(e);
		}
	}
	
	public static void updateUser(String username, String pass, String newNick, String newPass) throws UserException{
		
        try{
        	if (username == null || pass == null)
                throw new UserException("032", "Usuário ou senha nulos");
            
            if(!TextValidator.isValidText(username, "[a-zA-Z0-9]+", 6, 16) || !TextValidator.isValidText(pass, "[a-zA-Z0-9]+", 6, 32))
                throw new UserException("032", "Formato de usuário ou senha inválido");
            
            if(!newNick.isEmpty() && !TextValidator.isValidText(newNick, "[a-zA-Z0-9]+", 6, 16))
                throw new UserException("032", "Formato de novo nick inválido");
            
            if(!newPass.isEmpty() && !TextValidator.isValidText(newPass, "[a-zA-Z0-9]+", 6, 16))
                throw new UserException("032", "Formato de nova senha inválido");
            
            User user = UserController.getUserByUsername(username);
            
            if(!user.getPassword().equals(pass))
                throw new UserException("032", "Senha errada");
            
            if(!newNick.equals(""))
            	UserRepository.updateUserNick(username, newNick);

            if(!newPass.equals(""))
            	UserRepository.updateUserPass(username, newPass);
            
        } catch(UserException e){
        	throw new UserException("032", e.getMessage());
        } catch(Exception e){
        	System.out.println("Exceção update user controller");
        }
       
    }
	
	public static void deleteUser(String username, String password, String token) throws UserException{
	    try{
	        if(username == null || password == null || token == null)
	            throw new UserException("042", "Usuário, Token ou Senha nulos");

	        if(!TextValidator.isValidText(username, "[a-zA-Z0-9]+", 6, 16) ||
	           !TextValidator.isValidText(password, "[a-zA-Z0-9 ]+", 6, 32) ||
	           !TokenGenerator.isValidFormatToken(token)
	           )
	            throw new UserException("042", "Formato de Usuário, Token ou Senha errados");

	        User user = getUserByUsername(username); 

	        if(!user.getPassword().equals(password))
	            throw new UserException("042", "Senha errada");

	        User tokenUser = AuthRepository.tokens.get(token);
	        if(tokenUser == null || !tokenUser.getUser().equals(user.getUser()))
	            throw new UserException("042", "Token Errado/Não existe");

	        UserRepository.deleteUser(username);
	        AuthRepository.tokens.remove(token);

	    } catch(UserException e){
	        throw new UserException("042", e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Exceção em deleteUser controller");
	        throw new UserException("500", "Erro interno ao tentar deletar usuário");
	    }
	}

	
}
