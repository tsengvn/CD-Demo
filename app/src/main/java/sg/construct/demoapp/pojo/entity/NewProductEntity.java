package sg.construct.demoapp.pojo.entity;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/25/16
 */
public class NewProductEntity {
    public String name;
    public String description;
    public String image;

    public NewProductEntity(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
