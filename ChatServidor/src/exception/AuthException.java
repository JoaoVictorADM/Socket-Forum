package exception;

public class AuthException extends BaseException{

	private static final long serialVersionUID = 1L;
	
	public AuthException(String message){
		super(message);
	}
	
	public AuthException(String op, String message){
		super(op, message);
	}
	
	public AuthException(BaseException e){
		super(e);
	}
	
}
