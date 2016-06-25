package sg.construct.demoapp.ui.detail;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.construct.demoapp.domain.interactor.DataService;
import sg.construct.demoapp.pojo.entity.ProductEntity;
import sg.construct.demoapp.ui.base.DefaultSubscriber;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/25/16
 */
public class ProductDetailPresenterImpl implements ProductDetailPresenter {
    private final ProductDetailView mView;
    private final DataService mService;

    public ProductDetailPresenterImpl(ProductDetailView view, DataService service) {
        mView = view;
        mService = service;
    }

    @Override
    public void getData(long id) {
        mService.getProduct(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<ProductEntity>() {
                    @Override
                    public void onNext(ProductEntity entity) {
                        mView.onReceiveData(entity);
                    }
                });
    }
}
