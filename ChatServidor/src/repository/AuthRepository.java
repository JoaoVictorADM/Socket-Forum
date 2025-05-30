package repository;

import java.util.HashMap;
import java.util.Map;
import exception.AuthException;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import controller.DBConnectionController;
import utils.TokenGenerator;
import exception.UserException;

public abstract class AuthRepository{

	private static Map<String, User> tokens;
	
	static{
        tokens = new HashMap<>();
    }
	
    public static void register(User user) throws AuthException{
        String sql = "INSERT INTO users (user, nick, pass, isAdministrator) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
        	
            stmt.setString(1, user.getUser());
            stmt.setString(2, user.getNick());
            stmt.setString(3, user.getPassword());
            stmt.setBoolean(4, false);

            stmt.executeUpdate();
            
            System.out.printf("Cadastro realizado para %s\n", user.getUser());
        } catch(SQLIntegrityConstraintViolationException e) {
            throw new AuthException("012", "Usuário já existe. Tente outro.");
        } catch(SQLException e){
            e.printStackTrace();
            throw new AuthException("500", "Erro ao registrar o usuário.");
        } catch(Exception e){
        	System.out.printf("%s\n%s", e.getMessage(), e.getStackTrace());
        }
        
    }

    public static User login(String user, String password) throws AuthException{
        String sql = "SELECT * FROM users WHERE user = ?";

        try{
        	PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
        	
            stmt.setString(1, user);

            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next())
                    throw new AuthException("002", "Usuário não existe.");

                String dbPass = rs.getString("pass");
                if(!dbPass.equals(password))
                    throw new AuthException("002", "Senha errada.");

                int id = rs.getInt("iduser");
                
                String nick = rs.getString("nick");
                boolean isAdmin = rs.getBoolean("isAdministrator");

                
                String token = TokenGenerator.generateUniqueToken(tokens.keySet());
  
                if(token.equals(""))
                	throw new AuthException("500", "Não foi possível gerar token para o usuário");               
                
                User loggedUser = new User(id, user, nick, password, isAdmin, token);
                tokens.put(token, loggedUser);
                
                System.out.printf("Login realizado para %s\n", user);
                return loggedUser;
            }

        } catch(SQLException e){
            e.printStackTrace();
            throw new AuthException("500", "Erro ao realizar login.");
        }
    }
    
    public static void logout(String username, String token) throws AuthException{
        
    	try{
            if(!tokens.containsKey(token)) {
                throw new AuthException("022", "Token não existe");
            }
            
            User user = UserRepository.getUserByUsername(username);
            User tokenOwner = tokens.get(token);

            if(!tokenOwner.getUser().equals(username)) {
                throw new AuthException("023", "Este token não pertence ao usuário informado");
            }

            tokens.remove(token);

            System.out.println("Logout realizado com sucesso.");
            
        } catch (UserException e) {
            throw new AuthException(e);
        }
    }
    	
}
