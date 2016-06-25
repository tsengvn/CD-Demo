package sg.construct.demoapp.ui.create;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.construct.demoapp.Application;
import sg.construct.demoapp.R;
import sg.construct.demoapp.domain.interactor.DataService;
import sg.construct.demoapp.domain.interactor.ImageService;
import sg.construct.demoapp.ui.base.BaseActivity;
import sg.construct.demoapp.ui.create.gallery.GalleryActivity;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class CreateProductActivity extends BaseActivity implements CreateProductView{
    public static void open(Activity activity, int reqCode) {
        Intent intent = new Intent(activity, CreateProductActivity.class);
        activity.startActivityForResult(intent, reqCode);
    }

    @BindView(R.id.image)
    ImageView mImageView;

    @BindView(R.id.edtTitle)
    EditText mTitleInput;

    @BindView(R.id.edtDescription)
    EditText mDescriptionInput;

    @Inject
    ImageService mImageService;

    @Inject
    DataService mDataService;

    private CreateProductPresenter mPresenter;
    private Uri mUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_create);
        setDefaultToolbar(true);

        mPresenter = new CreateProductPresenterImpl(this, mDataService);
    }

    @OnClick(R.id.image)
    void onImageClicked() {
        GalleryActivity.open(this, 0);
    }

    @OnClick(R.id.btnSubmit)
    void onSubmit() {
        mPresenter.create(mUri, mTitleInput.getText().toString(), mDescriptionInput.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            mUri = data.getData();
            mImageService.loadImage(mImageView, mUri);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showInputError(int error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error));
        builder.setMessage(error);
        builder.show();
    }

    @Override
    public void showSubmitDoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your product submitted successfully!");
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void showLoading() {
//        super.showLoading();
        View view = ButterKnife.findById(this, R.id.ivSpinner);
        view.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.ROTATION, 0, 360);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

        mDescriptionInput.setEnabled(false);
        mTitleInput.setEnabled(false);
        mImageView.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        mDescriptionInput.setEnabled(true);
        mTitleInput.setEnabled(true);
        mImageView.setEnabled(true);
        View view = ButterKnife.findById(this, R.id.ivSpinner);
        view.clearAnimation();
        view.setVisibility(View.INVISIBLE);
    }
}
