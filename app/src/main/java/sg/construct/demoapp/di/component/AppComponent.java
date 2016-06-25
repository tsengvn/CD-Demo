package sg.construct.demoapp.di.component;

import javax.inject.Singleton;

import dagger.Component;
import sg.construct.demoapp.di.module.AppModule;
import sg.construct.demoapp.ui.create.CreateProductActivity;
import sg.construct.demoapp.ui.detail.ProductDetailActivity;
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

    void inject(ProductDetailActivity productDetailActivity);

    void inject(CreateProductActivity createProductActivity);
}
