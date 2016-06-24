package sg.construct.demoapp.pojo.entity;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class UserEntity {
    String email;
    String password;

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
