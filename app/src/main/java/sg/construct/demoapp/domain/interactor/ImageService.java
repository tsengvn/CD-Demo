package sg.construct.demoapp.domain.interactor;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Copyright (c) 2016, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 6/24/16
 */
public class ImageService {
    private final Context mContext;

    public ImageService(Context context) {
        mContext = context;
        Picasso.with(mContext).setLoggingEnabled(true);
    }

    public void loadImage(ImageView imageView, String url) {
        Picasso.with(mContext).load(url).fit().centerCrop().into(imageView);
    }

    public void loadImage(ImageView imageView, Uri uri) {
        Picasso.with(mContext).load(uri).fit().centerCrop().into(imageView);
    }

    public void loadThumbnail(ImageView imageView, Uri uri) {
        Picasso.with(mContext).load(uri).resize(300, 300).centerCrop().into(imageView);
    }
}
