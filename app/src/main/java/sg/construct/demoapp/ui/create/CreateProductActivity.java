package sg.construct.demoapp.ui.create;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sg.construct.demoapp.Application;
import sg.construct.demoapp.R;
import sg.construct.demoapp.domain.interactor.ImageService;
import sg.construct.demoapp.ui.base.BaseActivity;
import sg.construct.demoapp.ui.create.gallery.GalleryActivity;

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

    @BindView(R.id.image)
    ImageView mImageView;

    @Inject
    ImageService mImageService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getAppComponent(this).inject(this);
        setDefaultToolbar(true);
        setContentView(R.layout.activity_create);
    }

    @OnClick(R.id.image)
    void onImageClicked() {
        GalleryActivity.open(this, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            mImageService.loadImage(mImageView, data.getData());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
