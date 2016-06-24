package sg.construct.demoapp.pojo.entity;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class ProductEntity {
    public long id;
    public String name;
    public String description;
    public boolean isNew;
    public String excerpt;
    public Images images;

    public static class Images {
        public String thumbnail;
        public String full;
    }
}
