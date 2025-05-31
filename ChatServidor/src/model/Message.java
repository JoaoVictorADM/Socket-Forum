package model;

public abstract class Message{

	protected int id;
	protected String message;
	protected int userId;
	protected String nick;
	
	public Message(int id, String message, String nick){
		this.id = id;
		this.message = message;
		this.nick = nick;
	}
	
	public Message(String message){
		this.message = message;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public int getUserId(){
		return this.userId;
	}
	
	public void setUserId(int userId){
		this.userId = userId;
	}
	
	public String getNick(){
		return this.nick;
	}
	
	public void setNick(String nick){
		this.nick = nick;
	}
	
}
