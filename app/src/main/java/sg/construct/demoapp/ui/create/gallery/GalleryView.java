package sg.construct.demoapp.ui.create.gallery;

import android.net.Uri;

import java.util.List;

import sg.construct.demoapp.ui.base.BaseView;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/25/16
 */
interface GalleryView extends BaseView{
    void onReceiveData(List<Uri> images);
}
