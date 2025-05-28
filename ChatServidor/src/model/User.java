package model;

public class User{

	private int id;
	private String user;
	private String nick;
	private String password;
	private boolean isAdministrator;
	private String token;
	
	public User(int id, String user, String nick, String password, boolean isAdministrator){
		this.id = id;
		this.user = user;
		this.nick = nick;
		this.password = password;
		this.isAdministrator = isAdministrator;
	}
	
	public User(String user, String nick, String password, boolean isAdministrator, String token){
		this.user = user;
		this.nick = nick;
		this.password = password;
		this.isAdministrator = isAdministrator;
		this.token = token;
	}
	
	
	public User(String user, String nick, String password, boolean isAdministrator){
		this.user = user;
		this.nick = nick;
		this.password = password;
		this.isAdministrator = isAdministrator;
	}
	
	public User(String user, String nick, String password){
		this.user = user;
		this.nick = nick;
		this.password = password;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getUser(){
		return this.user;
	}

	public void setUser(String user){
		this.user = user;
	}

	public String getNick(){
		return this.nick;
	}

	public void setNick(String nick){
		this.nick = nick;
	}

	public String getPassword(){
		return this.password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public boolean isAdministrator(){
		return this.isAdministrator;
	}

	public void setAdministrator(boolean isAdministrator){
		this.isAdministrator = isAdministrator;
	}
	
	public String getToken(){
		return this.token;
	}
	
	public void setToken(String token){
		this.token = token;
	}
	
}
