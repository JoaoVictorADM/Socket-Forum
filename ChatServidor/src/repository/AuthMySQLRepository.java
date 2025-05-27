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

public class AuthMySQLRepository implements AuthRepository{

	private static AuthRepository instance;
	private final Map<String, User> tokens;
	
	private AuthMySQLRepository(){
        this.tokens = new HashMap<>();
    }
	
	public static synchronized AuthRepository getInstance(){ // O synchronized garante que apenas uma thread por vez possa acessar esse método
        if(instance == null)
            instance = new AuthMySQLRepository();
        
        return instance;
    }
	
	@Override
    public void register(User user) throws AuthException {
        String sql = "INSERT INTO users (user, nick, pass, isAdministrator) VALUES (?, ?, ?, ?)";

        try{

            PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
        	
            stmt.setString(1, user.getUser());
            stmt.setString(2, user.getNick());
            stmt.setString(3, user.getPassword());
            stmt.setBoolean(4, false);

            stmt.executeUpdate();
            
            System.out.printf("Cadastro realizado para %s\n", user.getUser());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AuthException("012", "Usuário já existe. Tente outro.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthException("500", "Erro ao registrar o usuário.");
        } catch(Exception e){
        	System.out.printf("%s\n%s", e.getMessage(), e.getStackTrace());
        }
        
    }

    @Override
    public User login(String user, String password) throws AuthException {
        String sql = "SELECT * FROM users WHERE user = ?";

        try{
        	
        	PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
        	
            stmt.setString(1, user);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next())
                    throw new AuthException("002", "Usuário não existe.");

                String dbPass = rs.getString("pass");
                if (!dbPass.equals(password))
                    throw new AuthException("002", "Senha errada.");

                String nick = rs.getString("nick");
                boolean isAdmin = rs.getBoolean("isAdministrator");
                
                System.out.printf("Login realizado para %s\n", user);
                return new User(user, nick, password, isAdmin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthException("500", "Erro ao realizar login.");
        }
    }
	
}
