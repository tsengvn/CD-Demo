package sg.construct.demoapp.ui.list;

import android.support.annotation.StringRes;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.construct.demoapp.domain.interactor.DataService;
import sg.construct.demoapp.pojo.entity.ProductEntity;
import sg.construct.demoapp.ui.base.DefaultSubscriber;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class ListProductPresenterImpl implements ListProductPresenter {
    private final ListProductView mView;
    private final DataService mService;

    private boolean mFirstRun = true;

    public ListProductPresenterImpl(ListProductView view, DataService service) {
        mView = view;
        mService = service;
    }

    @Override
    public void getData() {
        mService.getListProduct(mFirstRun)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DefaultSubscriber<List<ProductEntity>>() {
                    @Override
                    public void onNext(List<ProductEntity> entities) {
                        mView.onReceiveData(entities);
                    }

                    @Override
                    public void onError(@StringRes int errorRes) {
                        mView.showError(errorRes);
                    }
                });
        mFirstRun = false;
    }
}
