package repository;

import exception.AuthException;
import model.User;

public interface AuthRepository{

	public abstract void register(User user) throws AuthException;
	public abstract User login(String user, String password) throws AuthException;
	public abstract void logout();
	
}
