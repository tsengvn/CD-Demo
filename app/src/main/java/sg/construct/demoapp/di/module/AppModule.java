package sg.construct.demoapp.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.construct.demoapp.BuildConfig;
import sg.construct.demoapp.domain.interactor.DataService;
import sg.construct.demoapp.domain.interactor.ImageService;
import sg.construct.demoapp.domain.repo.AuthRepo;
import sg.construct.demoapp.domain.repo.ProductRepo;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
@Module
public class AppModule {
    private static final String API_VERSION = "api/v1";
    private final Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Singleton @Provides
    Context provideContext() {
        return this.mApplication;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        GsonConverterFactory factory = GsonConverterFactory.create(gson);
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.URL_ENDPOINT + "/" + API_VERSION + "/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(factory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    AuthRepo provideAuthRepo(Retrofit retrofit) {
        return retrofit.create(AuthRepo.class);
    }

    @Provides
    @Singleton
    ProductRepo provideProductRepo(Retrofit retrofit) {
        return retrofit.create(ProductRepo.class);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    DataService provideDataService(AuthRepo authRepo, ProductRepo productRepo, SharedPreferences preferences, Context context) {
        return new DataService(authRepo, productRepo, preferences, context);
    }

    @Provides
    @Singleton
    ImageService provideImageService(Context context) {
        return new ImageService(context);
    }
}
