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
                    throw new UserException("Usuário não existe.");

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
	
	
}
