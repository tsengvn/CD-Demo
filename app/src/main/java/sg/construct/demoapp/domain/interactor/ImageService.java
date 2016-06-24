package sg.construct.demoapp.domain.interactor;

import android.content.Context;
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
    }

    public void loadImage(ImageView imageView, String url) {
        Picasso.with(mContext).load(url).fit().centerCrop().into(imageView);
    }
}
