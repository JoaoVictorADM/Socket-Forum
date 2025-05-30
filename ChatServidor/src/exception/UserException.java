package exception;

public class UserException extends BaseException{

	private static final long serialVersionUID = 1L;
	
	public UserException(String message){
		super(message);
	}
	
	public UserException(String op, String message){
		super(op, message);
	}
	
	public UserException(BaseException e){
		super(e);
	}
	
}
