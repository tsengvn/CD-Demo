package sg.construct.demoapp.ui.create;

import android.app.Activity;
import android.content.Intent;

import sg.construct.demoapp.ui.base.BaseActivity;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class CreateProductActivity extends BaseActivity {
    public static void open(Activity activity, int reqCode) {
        Intent intent = new Intent(activity, CreateProductActivity.class);
        activity.startActivityForResult(intent, reqCode);
    }
}
