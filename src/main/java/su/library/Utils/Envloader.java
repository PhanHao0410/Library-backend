package su.library.Utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Envloader {
    private static final Dotenv dotenv = Dotenv.configure().load();

    public static String get(String key) {
        return dotenv.get(key);
    }
}
