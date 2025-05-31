package model;

public class ResponseMessage extends Message{

	private int parentId;
	
	public ResponseMessage(int id, String message, String nick){
		super(id, message, nick);
	}
	
	public ResponseMessage(String message, int parentId){
		super(message);
		this.parentId = parentId;
	}
	
	public int getParentId(){
		return this.parentId;
	}
	
	public void setParentId(int parentId){
		this.parentId = parentId;
	}
	
}
