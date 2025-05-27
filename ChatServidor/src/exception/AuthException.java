package exception;

public class AuthException extends Exception{

	private static final long serialVersionUID = 1L;
	private final String op;
	
	public AuthException(String op, String message) {
		super(message);
		this.op = op;
	}
	
	public AuthException(AuthException AE) {
		super(AE.getMessage());
		this.op = AE.getOp();
	}
	
	public String getOp(){
        return op;
    }
	
}
