package sg.construct.demoapp.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import sg.construct.demoapp.pojo.entity.ProductEntity;
import sg.construct.demoapp.ui.base.BaseActivity;
import sg.construct.demoapp.ui.create.CreateProductActivity;
import sg.construct.demoapp.ui.detail.ProductDetailActivity;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class ListProductActivity extends BaseActivity implements ListProductView{

    @Inject
    DataService mDataService;

    @Inject
    ImageService mImageService;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ListProductPresenter mPresenter;
    private List<ProductEntity> mEntities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_list);
        setDefaultToolbar(false);

        mPresenter = new ListProductPresenterImpl(this, mDataService);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getData();
    }

    @Override
    public void onReceiveData(List<ProductEntity> entities) {
        if (mRecyclerView.getAdapter() == null) {
            mEntities = new ArrayList<>();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(new Adapter());
        }

        mEntities.clear();
        mEntities.addAll(entities);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @OnClick(R.id.btnAdd)
    public void openCreateProductScreen() {
        CreateProductActivity.open(this, 0);
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.adapter_product, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ProductEntity entity = mEntities.get(position);
            holder.text1.setText(entity.name);
            holder.text2.setText(entity.description);
            mImageService.loadImage(holder.image, entity.images.thumbnail);
            holder.imageNew.setVisibility(entity.isNew ? View.VISIBLE : View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductDetailActivity.open(ListProductActivity.this, entity.id);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mEntities != null ? mEntities.size() : 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView text1;
        TextView text2;
        ImageView image;
        ImageView imageNew;
        public ViewHolder(View itemView) {
            super(itemView);
            text1 = ButterKnife.findById(itemView, R.id.text1);
            text2 = ButterKnife.findById(itemView, R.id.text2);
            image = ButterKnife.findById(itemView, R.id.image);
            imageNew = ButterKnife.findById(itemView, R.id.imageNew);
        }
    }
}
