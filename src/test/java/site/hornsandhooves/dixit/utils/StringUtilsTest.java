package site.hornsandhooves.dixit.utils;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

    @Test
    void whenGetRandomStringThenLengthIs10() {
        assertEquals(StringUtils.getRandomString(10).length(), 10);
    }

}
