package sg.construct.demoapp.ui.create;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import sg.construct.demoapp.Application;
import sg.construct.demoapp.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getAppComponent(this).inject(this);
        setDefaultToolbar(true);
        setContentView(R.layout.activity_create);

    }
}
