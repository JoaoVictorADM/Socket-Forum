package utils;

import java.util.Collection;
import java.util.Random;

public abstract class TokenGenerator{
    private static final Random random = new Random();
    
    public static String generateUniqueToken(Collection<String> existingTokens){
        String token;
        int count = 0;
        int maxTokens = (int)Math.pow(10, 5);

        do{
            token = "a" + String.format("%05d", TokenGenerator.random.nextInt(100000));
            count++;
        } while(existingTokens.contains(token) && count < maxTokens);

        if(count != maxTokens)
        	return token;
        
        return "";
    }
    
    public static boolean isValidFormatToken(String token){
        return token != null && token.matches("a\\d{5}");
    }
}