package sg.construct.demoapp.ui.list;

import java.util.List;

import sg.construct.demoapp.pojo.entity.ProductEntity;
import sg.construct.demoapp.ui.base.BaseView;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
interface ListProductView extends BaseView{
    void onReceiveData(List<ProductEntity> entities);
}
