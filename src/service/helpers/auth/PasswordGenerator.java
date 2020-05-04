package service.helpers.auth;

import java.util.Random;

public class PasswordGenerator {

    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int ran = random.nextInt(26);
            char c = (char) ('A' + ran);
            password.append(c);
        }

        return password.toString();
    }
}
