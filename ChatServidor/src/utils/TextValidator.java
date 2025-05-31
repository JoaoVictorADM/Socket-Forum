package utils;

public abstract class TextValidator{

	public static boolean isValidText(String text, String regex, int min, int max){
		
	    if(text.length() < min || text.length() > max) 
	    	return false;
	    
	    return text.matches(regex);
	}
	
}
