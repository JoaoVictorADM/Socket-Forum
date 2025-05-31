package controller;

import repository.AuthRepository;
import model.User;
import exception.AuthException;
import utils.TokenGenerator;
import utils.TextValidator;

public abstract class  AuthController{
	
	public static User login(String user, String password) throws AuthException{
		
		if((user == null || password == null))
			throw new AuthException("002", "Usuário ou Senha nulos");
		
		if(!TextValidator.isValidText(user, "[a-zA-Z0-9]+", 6, 16) || !TextValidator.isValidText(password, "[a-zA-Z0-9]+", 6, 32))
			throw new AuthException("002", "Formato de Usuário ou Senha errados");
		
		try{
			return AuthRepository.login(user, password);
		} catch(AuthException AE){
			throw new AuthException(AE);
		}
    }
	
	public static void register(User user) throws AuthException{

		if(user.getUser() == null || user.getPassword() == null || user.getNick() == null)
			throw new AuthException("012", "Usuário, Nick ou Senha nulos");
		
		if(!TextValidator.isValidText(user.getUser(), "[a-zA-Z0-9]+", 6, 16) || !TextValidator.isValidText(user.getNick(), "[a-zA-Z0-9]+", 6, 16) || !TextValidator.isValidText(user.getPassword(), "[a-zA-Z0-9]+", 6, 32))
			throw new AuthException("012", "Formato de Usuário, Nick ou Senha errados");
		
		try{
			AuthRepository.register(user);
		} catch(AuthException AE){
			throw new AuthException(AE);
		}
		
    }
	
	public static void logout(String user, String token) throws AuthException{
	
		if(user == null || token == null)
			throw new AuthException("012", "Usuário ou token nulos");
		
		if(!TextValidator.isValidText(user, "[a-zA-Z0-9]+", 6, 16) || !TokenGenerator.isValidFormatToken(token))
			throw new AuthException("002", "Formato de Usuário ou Token errados");
		
		try{
			AuthRepository.logout(user, token);
		} catch(AuthException AE){
			throw new AuthException(AE);
		}
	}
}
