package sg.construct.demoapp.ui.create;

import android.support.annotation.StringRes;

import sg.construct.demoapp.ui.base.BaseView;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/25/16
 */
interface CreateProductView extends BaseView {
    void showInputError(@StringRes int error);

    void showSubmitDoneDialog();

}
