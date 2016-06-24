package sg.construct.demoapp;

import android.content.Context;

import sg.construct.demoapp.di.component.AppComponent;
import sg.construct.demoapp.di.component.DaggerAppComponent;
import sg.construct.demoapp.di.module.AppModule;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class Application extends android.app.Application{
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    private void initInjector() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent(Context context) {
        return ((Application)context.getApplicationContext()).mAppComponent;
    }
}
