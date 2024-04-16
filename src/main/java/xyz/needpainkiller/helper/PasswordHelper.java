package xyz.needpainkiller.helper;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class PasswordHelper {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomPassword(int len) {

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(CHARS.length());
            sb.append(CHARS.charAt(randomIndex));
        }

        return sb.toString();
    }
}
