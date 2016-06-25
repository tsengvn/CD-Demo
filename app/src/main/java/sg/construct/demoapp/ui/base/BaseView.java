package sg.construct.demoapp.ui.base;

import android.support.annotation.StringRes;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public interface BaseView {
    void showNoNetworkError();

    void showError(@StringRes int stringRes);

    void showLoading(@StringRes int stringRes);

    void showLoading();

    void hideLoading();
}
