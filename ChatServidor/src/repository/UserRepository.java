package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import controller.DBConnectionController;
import model.User;
import exception.UserException;

public abstract class UserRepository{

	public static User getUserByUsername(String user) throws UserException{
		String sql = "SELECT * FROM users WHERE user = ?";
		
		try{
        	PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
        	
            stmt.setString(1, user);

            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next())
                    throw new UserException("-1", "Usuário não existe.");

                int id = rs.getInt("iduser");    
                String nick = rs.getString("nick");
                String pass = rs.getString("pass");
                boolean isAdmin = rs.getBoolean("isAdministrator");

                User reponseUser = new User(id, user, nick, pass, isAdmin);

                return reponseUser;
            }

        } catch(SQLException e){
            e.printStackTrace();
            throw new UserException("500", "Erro ao recuperar usuario do banco de dados");
        }
		
	}
	
	public static String getNickById(int id) throws UserException{
	    String sql = "SELECT nick FROM users WHERE iduser = ?";
	    
	    try {
	        PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
	        stmt.setInt(1, id);

	        try(ResultSet rs = stmt.executeQuery()) {
	            if(rs.next())
	                return rs.getString("nick");
	            
	            throw new UserException("-1", "Não foi possivel achar nick pois o usuário não existe."); 
	            
	        }

	    } catch(SQLException e){
	        e.printStackTrace();
	        throw new UserException("500", "Erro ao recuperar nick do usuario do banco de dados"); 
	    }
	}
	
	public static void updateUserNick(String username, String newNick) throws UserException{
	    String sql = "UPDATE users SET nick = ? WHERE user = ?";

	    try {
	        PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
	        stmt.setString(1, newNick);
	        stmt.setString(2, username);

	        int rowsAffected = stmt.executeUpdate();
	        
	        if(rowsAffected == 0)
	            throw new UserException("-1", "Usuário não existe");  

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new UserException("500", "Erro ao atualizar nick do usuário.");
	    }
	}

	public static void updateUserPass(String username, String newPass) throws UserException {
	    String sql = "UPDATE users SET pass = ? WHERE user = ?";

	    try{
	        PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
	        stmt.setString(1, newPass);
	        stmt.setString(2, username);

	        int rowsAffected = stmt.executeUpdate();
	        
	        if(rowsAffected == 0)
	            throw new UserException("-1", "Usuário não encontrado para atualizar a senha.");

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new UserException("500", "Erro ao atualizar senha do usuário.");
	    }
	}
	
	public static void deleteUser(String username) throws UserException {
	    String sql = "DELETE FROM users WHERE user = ?";

	    try{
	        PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
	        stmt.setString(1, username);

	        int rowsAffected = stmt.executeUpdate();

	        if(rowsAffected == 0)
	            throw new UserException("-1", "Usuário não existe.");

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new UserException("500", "Erro ao deletar o usuário.");
	    }
	}
	
}
