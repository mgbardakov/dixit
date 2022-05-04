package site.hornsandhooves.dixit.utils;

import java.util.Random;

public class StringUtils {

    private static final int LEFT_LIMIT = 48;
    private static final int RIGHT_LIMIT = 122;

    public static String getRandomString(int length) {
            return new Random().ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(length).collect(StringBuilder::new, StringBuilder::appendCodePoint
                            , StringBuilder::append).toString();
    }
}
