package library;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
/**
 * Configuration utility class that loads environment variables
 * from a .env file using java-dotenv library.
 * Provides a static method to access environment variables by key.
 * 
 * @author Alaa
 * @version 1.0
 */
public class Config {

    try {
        File file = new File("./.env");

        dotenv = Dotenv.configure()
                       .directory(file.getParent())
                       .filename(file.getName())
                       .load();
    } catch (Exception e) {
        dotenv = null; 
    }
    }
    /**
     * Retrieves the value of an environment variable from the .env file.
     * 
     * @param key the name of the environment variable
     * @return the value of the environment variable, or null if not found
     */
    public static String get(String key) {
       String value = System.getenv(key);
    if (value != null) {
        return value;
    }

    
    if (dotenv != null) {
        return dotenv.get(key);
    }
    }
}
