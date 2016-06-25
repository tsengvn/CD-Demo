package sg.construct.demoapp.ui.create;

import android.net.Uri;

/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/25/16
 */
interface CreateProductPresenter {
    void create(Uri uri, String title, String description);
}
