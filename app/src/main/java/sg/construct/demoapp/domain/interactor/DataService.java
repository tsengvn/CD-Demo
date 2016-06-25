package sg.construct.demoapp.domain.interactor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import sg.construct.demoapp.domain.repo.AuthRepo;
import sg.construct.demoapp.domain.repo.ProductRepo;
import sg.construct.demoapp.pojo.entity.AuthenticationEntity;
import sg.construct.demoapp.pojo.entity.NewProductEntity;
import sg.construct.demoapp.pojo.entity.ProductEntity;
import sg.construct.demoapp.pojo.entity.UserEntity;
import sg.construct.demoapp.util.ImageUtil;
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
    private final Context mContext;

    public DataService(AuthRepo authRepo, ProductRepo productRepo, SharedPreferences preferences, Context context) {
        mAuthRepo = authRepo;
        mProductRepo = productRepo;
        mPreferences = preferences;
        mContext = context;
    }

    public Observable<List<ProductEntity>> getListProduct(boolean refresh) {
        if (refresh) {
            return refreshToken()
                    .flatMap(new Func1<String, Observable<List<ProductEntity>>>() {
                        @Override
                        public Observable<List<ProductEntity>> call(String token) {
                            return mProductRepo.getProductList(token);
                        }
                    });
        } else {
            return getToken()
                    .flatMap(new Func1<String, Observable<List<ProductEntity>>>() {
                        @Override
                        public Observable<List<ProductEntity>> call(String token) {
                            return mProductRepo.getProductList(token);
                        }
                    });
        }

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

    public Observable<List<Uri>> getImages(final Activity activity) {
        return Observable.create(new Observable.OnSubscribe<List<Uri>>() {
            @Override
            public void call(Subscriber<? super List<Uri>> subscriber) {
                List<Uri> results = new ArrayList<>();

                String[] projection = new String[] {
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DATE_TAKEN
                };

                Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                Cursor cur = activity.managedQuery(images, projection, null, null, MediaStore.Images.Media.DATE_TAKEN + " desc");

//                Log.i("ListingImages"," query count=" + cur.getCount());

                if (cur.moveToFirst()) {
                    int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);

                    do {
                        String data = cur.getString(dataColumn);
                        results.add(Uri.fromFile(new File(data)));
//                        Log.i("ListingImages", " data=" + data);
                    } while (cur.moveToNext());

                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(results);
                }
                subscriber.onCompleted();
            }
        });

    }

    public Observable<ProductEntity> createProduct(final Uri uri, final String title, final String description) {
        return getToken()
                .flatMap(new Func1<String, Observable<ProductEntity>>() {
                    @Override
                    public Observable<ProductEntity> call(String token) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
                            if (bitmap.getWidth() != bitmap.getHeight()) {
                                bitmap = ImageUtil.cropSquare(bitmap);
                            }
                            String data = ImageUtil.convertBitmapToString(bitmap);
                            bitmap.recycle();
                            NewProductEntity entity = new NewProductEntity(title, description, data);
                            return mProductRepo.createProduct(token, entity);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.error(new RuntimeException("Can not get the image data"));
                    }
                });
    }
}
