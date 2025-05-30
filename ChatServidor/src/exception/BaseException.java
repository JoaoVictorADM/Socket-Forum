package exception;

public class BaseException extends Exception{

	private static final long serialVersionUID = 1L;
	protected String op;
	
	public BaseException(String message){
		super(message);
	}
	
	public BaseException(String op, String message){
		super(message);
		this.op = op;
	}
	
	public BaseException(BaseException e){
		super(e.getMessage());
		this.op = e.getOp();
	}
	
	public String getOp(){
        return op;
    }
	
}
