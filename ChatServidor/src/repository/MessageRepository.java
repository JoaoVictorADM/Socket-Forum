package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import controller.DBConnectionController;
import controller.UserController;
import exception.MessageException;
import exception.UserException;
import model.Topic;
import model.ResponseMessage;

public abstract class MessageRepository{
	
	public static void createTopic(Topic topic) throws MessageException{
		String sql = "INSERT INTO messages (title, subject, message, iduser) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
        	
            stmt.setString(1, topic.getTitle());
            stmt.setString(2, topic.getSubject());
            stmt.setString(3, topic.getMessage());
            stmt.setInt(4, topic.getUserId());

            stmt.executeUpdate();
            
            System.out.print("Tópico cadastrado");
        } catch(SQLIntegrityConstraintViolationException e) {
            throw new MessageException("052", "Erro ao cadastrar novo tópico");
        } catch(SQLException e){
            e.printStackTrace();
            throw new MessageException("500", "Erro ao registrar tópico.");
        } catch(Exception e){
        	System.out.printf("%s\n%s", e.getMessage(), e.getStackTrace());
        }
	}
	
	public static void createResponseMessage(ResponseMessage responseMessage) throws MessageException{
		String sql = "INSERT INTO messages (idparent, message, iduser) VALUES (?, ?, ?)";

        try{
            PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
        	
            stmt.setInt(1, responseMessage.getParentId());
            stmt.setString(2, responseMessage.getMessage());
            stmt.setInt(3, responseMessage.getUserId());

            stmt.executeUpdate();
            
            System.out.print("Resposta cadastrada");
        } catch(SQLIntegrityConstraintViolationException e) {
            throw new MessageException("062", "Erro ao cadastrar nova resposta");
        } catch(SQLException e){
            e.printStackTrace();
            throw new MessageException("500", "Erro ao registrar reposta.");
        } catch(Exception e){
        	System.out.printf("%s\n%s\n", e.getMessage(), e.getStackTrace());
        }
	}
	
	public static boolean messageExists(int id) throws MessageException{
	    String sql = "SELECT 1 FROM messages WHERE idmessage = ? LIMIT 1";
	    
	    try{
	    	PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
	    	
	        stmt.setInt(1, id);

	        try(ResultSet rs = stmt.executeQuery()){
	            return rs.next();
	        }

	    } catch(SQLException e){
	        e.printStackTrace();
	        return false;
	    } catch (Exception e) {
	        System.out.printf("%s\n%s\n", e.getMessage(), e.getStackTrace());
	        return false;
	    }
	}
	
	public static List<Topic> getAllTopics() throws MessageException{
	    List<Topic> topics = new ArrayList<>();
	    String sql = "SELECT * FROM messages WHERE idparent IS NULL";

	    try{

	    	PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
	    	
	        ResultSet rs = stmt.executeQuery();
	    	
	        while(rs.next()){
	            int id = rs.getInt("idmessage");
	            String title = rs.getString("title");
	            String subject = rs.getString("subject");
	            String message = rs.getString("message");
	            int userId = rs.getInt("iduser");
	            
	            String nick = UserController.getNickById(userId);
	            
	            Topic topic = new Topic(id, title, subject, message, nick);
	            topics.add(topic);
	        }

	    } catch(UserException UE){
	    	throw new MessageException("077", UE.getMessage());
	    } catch(SQLException e){
	        e.printStackTrace();
	    }
	    
	    return topics;

	}
	
	public static List<ResponseMessage> getResponseMessagesByParentId(int parentId) throws MessageException{
		List<ResponseMessage> responseMessages = new ArrayList<>();
	    String sql = "SELECT * FROM messages WHERE idparent = ?";

	    try{

	    	PreparedStatement stmt = DBConnectionController.getConnection().prepareStatement(sql);
	    	
	    	stmt.setInt(1, parentId);
	    	
	        ResultSet rs = stmt.executeQuery();
	    	
	        while(rs.next()){
	            int id = rs.getInt("idmessage");
	            String message = rs.getString("message");
	            int userId = rs.getInt("iduser");
	            
	            String nick = UserController.getNickById(userId);
	            
	            ResponseMessage responseMessage = new ResponseMessage(id, message, nick);
	            responseMessages.add(responseMessage);
	        }

	    } catch(UserException UE){
	    	throw new MessageException("072", UE.getMessage());
	    } catch(SQLException e){
	        e.printStackTrace();
	    }
	    
	    return responseMessages;
	}
	
}
