package sg.construct.demoapp.util;

import android.text.TextUtils;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 4/11/16
 */
public class StringUtil {
    public static boolean isEmpty(CharSequence value) {
        return TextUtils.isEmpty(value);
    }

    public static boolean isBlank(CharSequence value) {
        if (value == null)
            return true;

        value = trim(value);

        return TextUtils.isEmpty(value);
    }

    public static String trim(CharSequence value) {
        if (value == null)
            return null;
        return value.toString().trim();
    }

    public static String join(String delimiter, String... value) {
        String result = "";
        for (String s : value) {
            if (!isBlank(s)) {
                result += trim(s) + delimiter + " ";
            }
        }

        if (isBlank(result)) {
            return "";
        } else {
            return result.substring(0, result.length()-(delimiter.length() + 1));
        }

    }
}
