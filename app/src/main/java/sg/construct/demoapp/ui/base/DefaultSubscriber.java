package sg.construct.demoapp.ui.base;

import android.support.annotation.StringRes;

import rx.Subscriber;
import sg.construct.demoapp.util.ErrorUtil;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public abstract class DefaultSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onError(ErrorUtil.checkError(e));
    }

    public void onError(@StringRes int errorRes) {
    }

}
