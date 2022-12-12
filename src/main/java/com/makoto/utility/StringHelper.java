package com.makoto.utility;

public class StringHelper {

    public static boolean isNullOrWhitespace(String input) {
        if (input == null)
            return true;
        if (input.isEmpty())
            return true;
        if (input.isBlank())
            return true;
        return false;
    }

}
