package controller;

import repository.AuthRepository;
import model.User;
import exception.AuthException;
import repository.AuthMySQLRepository;

public class AuthController{
	
	private static AuthController instance;
	private final AuthRepository authRepository;
	
	private AuthController(){
        this.authRepository = AuthMySQLRepository.getInstance();
    }
	
	public static synchronized AuthController getInstance(){ // O synchronized garante que apenas uma thread por vez possa acessar esse método
        if(instance == null)
            instance = new AuthController();
        
        return instance;
    }
	
	public User login(String user, String password) throws AuthException{
		
		if((user == null || password == null))
			throw new AuthException("002", "Usuário ou Senha nulos");
		
		if(!this.isValidText(user, "[a-zA-Z0-9]+", 6, 16) || !this.isValidText(password, "[a-zA-Z0-9]+", 6, 32))
			throw new AuthException("002", "Formato de Usuário ou Senha errados;");
		
		try{
			return this.authRepository.login(user, password);
		} catch(AuthException AE){
			throw new AuthException(AE);
		}
    }
	
	public User register(User user) throws AuthException{

		try{
			this.authRepository.register(new User(user.getUser(), user.getNick(), user.getPassword()));
			return this.login(user.getUser(), user.getPassword()); 
		} catch(AuthException AE){
			throw new AuthException(AE);
		}
		
    }
	
	private boolean isValidText(String text, String regex, int min, int max){
		
	    if(text.length() < min || text.length() > max) 
	    	return false;
	    
	    return text.matches(regex);
	}
}
