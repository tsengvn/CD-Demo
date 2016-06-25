package sg.construct.demoapp.ui.create.gallery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.construct.demoapp.Application;
import sg.construct.demoapp.R;
import sg.construct.demoapp.domain.interactor.DataService;
import sg.construct.demoapp.domain.interactor.ImageService;
import sg.construct.demoapp.ui.base.BaseActivity;
import sg.construct.demoapp.ui.widget.squarecamera.CameraFragment;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class GalleryActivity extends BaseActivity implements GalleryView{
    public static void open(Activity activity, int reqCode) {
        Intent intent = new Intent(activity, GalleryActivity.class);
        activity.startActivityForResult(intent, reqCode);
    }

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    ImageService mImageService;

    @Inject
    DataService mDataService;

    private GalleryPresenter mPresenter;
    private List<Uri> mImages = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_gallery);
        setDefaultToolbar(true);

        createCameraFragment();
        mPresenter = new GalleryPresenterImpl(this, mDataService);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadImages(this);
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

    @Override
    public void onReceiveData(List<Uri> images) {
        if (mRecyclerView.getAdapter() == null) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            mRecyclerView.setAdapter(new PhotoAdapter());
        }
        mImages.clear();
        mImages.addAll(images);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
        private int mSelectedPos = -1;

        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.adapter_photo, parent, false);
            return new PhotoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final PhotoViewHolder holder, int position) {
            mImageService.loadImage(holder.photoImage, mImages.get(position));
            holder.selectedImage.setVisibility(mSelectedPos == position ? View.VISIBLE : View.INVISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedPos = holder.getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mImages.size();
        }
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImage;
        ImageView selectedImage;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            photoImage = ButterKnife.findById(itemView, R.id.ivPhoto);
            selectedImage = ButterKnife.findById(itemView, R.id.ivSelected);
        }
    }
}
