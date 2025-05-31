package model;

public class Topic extends Message{
	
	private String title;
	private String subject;

	public Topic(int id, String title, String subject, String message, String nick){
		super(id, message, nick);
		this.title = title;
		this.subject = subject;
	}
	
	public Topic(String title, String subject, String message){
		super(message);
		this.title = title;
		this.subject = subject;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getSubject(){
		return this.subject;
	}
	
	public void setSubject(String subject){
		this.subject = subject;
	}
}
