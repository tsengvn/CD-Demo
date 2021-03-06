package sg.construct.demoapp.domain.repo;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import sg.construct.demoapp.pojo.entity.AuthenticationEntity;
import sg.construct.demoapp.pojo.entity.UserEntity;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public interface AuthRepo {
    @POST("auth")
    Observable<AuthenticationEntity> getToken(@Body UserEntity entity);
}
