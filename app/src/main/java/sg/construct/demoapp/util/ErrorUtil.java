package sg.construct.demoapp.util;

import android.support.annotation.StringRes;

import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import sg.construct.demoapp.R;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 4/25/16
 */
public class ErrorUtil {
    public static @StringRes int checkError(Throwable e) {
        if (e instanceof UnknownHostException) {
            return R.string.internet_connection;
        } else if (e instanceof HttpException) {
            return R.string.backend_issue;
        } else {
            return R.string.unknown_error;
        }
    }

}
