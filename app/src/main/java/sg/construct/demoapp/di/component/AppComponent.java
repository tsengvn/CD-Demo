package sg.construct.demoapp.di.component;

import javax.inject.Singleton;

import dagger.Component;
import sg.construct.demoapp.di.module.AppModule;
import sg.construct.demoapp.ui.list.ListProductActivity;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(ListProductActivity listProductActivity);
}
