package library;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;

public class Config {
	private static Dotenv dotenv;

    static {
       
        File file = new File("./.envv");
        dotenv = Dotenv.configure()
                       .directory(file.getParent())
                       .filename(file.getName())
                       .load();
    }

    public static String get(String key) {
        return dotenv.get(key);
    }

}
