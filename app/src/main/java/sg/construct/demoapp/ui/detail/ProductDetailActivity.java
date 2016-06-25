package sg.construct.demoapp.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import sg.construct.demoapp.Application;
import sg.construct.demoapp.R;
import sg.construct.demoapp.domain.interactor.DataService;
import sg.construct.demoapp.domain.interactor.ImageService;
import sg.construct.demoapp.pojo.entity.ProductEntity;
import sg.construct.demoapp.ui.base.BaseActivity;
import sg.construct.demoapp.util.StringUtil;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class ProductDetailActivity extends BaseActivity implements ProductDetailView{

    public static void open(Activity activity, long id) {
        Intent intent = new Intent(activity, ProductDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    @Inject
    DataService mDataService;

    @Inject
    ImageService mImageService;

    @BindView(R.id.image)
    ImageView mImageView;

    @BindView(R.id.text1)
    TextView mTitleText;

    @BindView(R.id.text2)
    TextView mDescriptionText;

    private ProductDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setDefaultToolbar(true);

        Application.getAppComponent(this).inject(this);

        mPresenter = new ProductDetailPresenterImpl(this, mDataService);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getData(getIntent().getLongExtra("id", 0));
    }

    @Override
    public void onReceiveData(ProductEntity entity) {
        if (entity.images != null && !StringUtil.isBlank(entity.images.thumbnail)) {
            mImageService.loadImage(mImageView, entity.images.full);
        }

        mTitleText.setText(entity.name);
        mDescriptionText.setText(entity.description);
    }
}
