package sg.construct.demoapp.ui.create;

import android.net.Uri;
import android.support.annotation.StringRes;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.construct.demoapp.R;
import sg.construct.demoapp.domain.interactor.DataService;
import sg.construct.demoapp.pojo.entity.ProductEntity;
import sg.construct.demoapp.ui.base.DefaultSubscriber;
import sg.construct.demoapp.util.StringUtil;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/25/16
 */
public class CreateProductPresenterImpl implements CreateProductPresenter {
    private final CreateProductView mView;
    private final DataService mDataService;

    public CreateProductPresenterImpl(CreateProductView createProductView, DataService dataService) {
        mView = createProductView;
        mDataService = dataService;
    }

    @Override
    public void create(Uri uri, String title, String description) {
        if (uri == null) {
            mView.showInputError(R.string.add_photo);
            return;
        }

        if (StringUtil.isBlank(title)) {
            mView.showInputError(R.string.add_title);
            return;
        }

        if (StringUtil.isBlank(description)) {
            mView.showInputError(R.string.add_description);
            return;
        }
        mView.showLoading();
        mDataService.createProduct(uri, title, description)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DefaultSubscriber<ProductEntity>() {
                    @Override
                    public void onNext(ProductEntity entity) {
                        mView.hideLoading();
                        mView.showSubmitDoneDialog();
                    }

                    @Override
                    public void onError(@StringRes int errorRes) {
                        mView.hideLoading();
                        mView.showError(errorRes);
                    }
                });
    }

}
