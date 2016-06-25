package sg.construct.demoapp.ui.create.gallery;

import android.app.Activity;
import android.net.Uri;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.construct.demoapp.domain.interactor.DataService;
import sg.construct.demoapp.ui.base.DefaultSubscriber;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/25/16
 */
public class GalleryPresenterImpl implements GalleryPresenter {
    private final GalleryView mGalleryView;
    private final DataService mDataService;

    public GalleryPresenterImpl(GalleryView galleryView, DataService dataService) {
        mGalleryView = galleryView;
        mDataService = dataService;
    }

    @Override
    public void loadImages(Activity activity) {
        mDataService.getImages(activity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DefaultSubscriber<List<Uri>>() {
                    @Override
                    public void onNext(List<Uri> uris) {
                        mGalleryView.onReceiveData(uris);
                    }
                });
    }
}
