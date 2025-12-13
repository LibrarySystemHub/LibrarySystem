package library;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;

public class Config {

    private static Dotenv dotenv;

    static {
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

    public static String get(String key) {

        String value = System.getenv(key);
        if (value != null) {
            return value;
        }

        if (dotenv != null) {
            return dotenv.get(key);
        }

        return null;
    }
}
