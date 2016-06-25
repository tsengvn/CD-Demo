package sg.construct.demoapp.ui.create.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.OnClick;
import sg.construct.demoapp.R;
import sg.construct.demoapp.ui.base.BaseActivity;
import sg.construct.demoapp.ui.widget.squarecamera.CameraFragment;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class GalleryActivity extends BaseActivity {
    public static void open(Activity activity, int reqCode) {
        Intent intent = new Intent(activity, GalleryActivity.class);
        activity.startActivityForResult(intent, reqCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        createCameraFragment();
    }

    @OnClick(R.id.btnCapture)
    public void captureCamera() {
        CameraFragment cameraFragment = (CameraFragment) getSupportFragmentManager()
                .findFragmentByTag(CameraFragment.TAG);
        cameraFragment.takePicture();
    }

    private void createCameraFragment() {
        getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.pnlCamera, CameraFragment.newInstance(), CameraFragment.TAG)
                    .commit();
    }
}
