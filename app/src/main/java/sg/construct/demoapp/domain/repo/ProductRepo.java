package sg.construct.demoapp.domain.repo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import sg.construct.demoapp.pojo.entity.NewProductEntity;
import sg.construct.demoapp.pojo.entity.ProductEntity;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public interface ProductRepo {
    @GET("product")
    Observable<List<ProductEntity>> getProductList(@Header("Authorization") String token);

    @POST("product")
    Observable<ProductEntity> createProduct(@Header("Authorization") String token, @Body NewProductEntity entity);

    @GET("product/{id}")
    Observable<ProductEntity> getProduct(@Header("Authorization") String token, @Path("id") long id);
}
