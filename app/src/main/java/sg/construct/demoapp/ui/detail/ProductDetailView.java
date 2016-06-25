package sg.construct.demoapp.ui.detail;

import sg.construct.demoapp.pojo.entity.ProductEntity;
import sg.construct.demoapp.ui.base.BaseView;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/25/16
 */
interface ProductDetailView extends BaseView{
    void onReceiveData(ProductEntity entity);
}
