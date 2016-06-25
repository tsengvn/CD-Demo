package sg.construct.demoapp.domain.interactor;

import android.content.SharedPreferences;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import sg.construct.demoapp.domain.repo.AuthRepo;
import sg.construct.demoapp.domain.repo.ProductRepo;
import sg.construct.demoapp.pojo.entity.AuthenticationEntity;
import sg.construct.demoapp.pojo.entity.ProductEntity;
import sg.construct.demoapp.pojo.entity.UserEntity;
import sg.construct.demoapp.util.StringUtil;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class DataService {
    private final AuthRepo mAuthRepo;
    private final ProductRepo mProductRepo;
    private final SharedPreferences mPreferences;

    public DataService(AuthRepo authRepo, ProductRepo productRepo, SharedPreferences preferences) {
        mAuthRepo = authRepo;
        mProductRepo = productRepo;
        mPreferences = preferences;
    }

    public Observable<List<ProductEntity>> getListProduct() {
        return getToken()
                .flatMap(new Func1<String, Observable<List<ProductEntity>>>() {
                    @Override
                    public Observable<List<ProductEntity>> call(String token) {
                        return mProductRepo.getProductList(token);
                    }
                });
    }

    private Observable<String> getToken() {
        return Observable.just(mPreferences.getString("token", null))
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String token) {
                        return StringUtil.isBlank(token) ? refreshToken() : Observable.just(token);
                    }
                });
    }

    private Observable<String> refreshToken() {
        UserEntity userEntity = new UserEntity("nmhien88@gmail.com", "BCtT5dRpVDEd");
        return mAuthRepo.getToken(userEntity)
                .map(new Func1<AuthenticationEntity, String>() {
                    @Override
                    public String call(AuthenticationEntity authenticationEntity) {
                        String token = "Bearer " + authenticationEntity.apiToken;
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.putString("token", token);
                        editor.apply();
                        return token;
                    }
                });
    }

    public Observable<ProductEntity> getProduct(final long id) {
        return getToken().flatMap(new Func1<String, Observable<ProductEntity>>() {
            @Override
            public Observable<ProductEntity> call(String token) {
                return mProductRepo.getProduct(token, id);
            }
        });
    }
}
