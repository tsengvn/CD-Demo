package sg.construct.demoapp.ui.base;

import android.app.ProgressDialog;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import sg.construct.demoapp.R;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class BaseActivity extends AppCompatActivity implements BaseView{
    private ProgressDialog mProgressDialog;
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = ProgressDialog.show(this, "", "Loading");
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void showNoNetworkError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error)
                .setMessage(R.string.internet_connection)
                .show();
    }

    @Override
    public void showError(@StringRes int stringRes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error)
                .setMessage(stringRes)
                .show();
    }

    protected void setDefaultToolbar(boolean allowBack) {
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        if (toolbar == null) return;

        setSupportActionBar(toolbar);
        setTitle("");
        if (allowBack) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
