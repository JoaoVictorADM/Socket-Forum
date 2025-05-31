package exception;

public class MessageException extends BaseException{

	private static final long serialVersionUID = 1L;
	
	public MessageException(String message){
		super(message);
	}
	
	public MessageException(String op, String message){
		super(op, message);
	}
	
	public MessageException(BaseException e){
		super(e);
	}
	
}
