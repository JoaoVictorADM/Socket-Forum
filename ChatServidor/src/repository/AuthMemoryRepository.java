package repository;

import java.util.HashMap;
import java.util.Map;
import model.User;
import exception.AuthException;

public class AuthMemoryRepository implements AuthRepository{
	
	//Map<User, String> para token
	
	private static AuthRepository instance;
	private final Map<String, User> users;
	
	private AuthMemoryRepository(){
        this.users = new HashMap<>();
        users.put("admin", new User("admin", "Admin", "admin123", true));
    }
	
	public static synchronized AuthRepository getInstance(){ // O synchronized garante que apenas uma thread por vez possa acessar esse método
        if(instance == null)
            instance = new AuthMemoryRepository();
        
        return instance;
    }
	
	public void register(User user) throws AuthException{
        if(users.containsKey(user.getUser()))
        	throw new AuthException("012", "Usuário já existe. Tente outro.");
      
        users.put(user.getUser(), new User(user.getUser(), user.getNick(), user.getPassword(), false));
    }
	
	public User login(String user, String password) throws AuthException{
        User foundUser = users.get(user);
        
        if(foundUser == null)
        	throw new AuthException("002", "Usuário não existe.");
        
        if(!foundUser.getPassword().equals(password))
        	throw new AuthException("002", "Senha errada.");
        	
        return foundUser;
    }
	
}
