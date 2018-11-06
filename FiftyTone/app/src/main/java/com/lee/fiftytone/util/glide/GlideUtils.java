package com.lee.fiftytone.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lee.fiftytone.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by LeeChen on 2017/7/13 0013.
 */

public class GlideUtils {
    /**
     * 加载网络图片-centerCrop
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void LoadImage(Context mContext, String path, ImageView imageview) {
        Glide.with(mContext).load(path).centerCrop()
                .placeholder(R.drawable.picture_empty)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageview);
    }

    /**
     * 加载网络图片-centerCrop-自定义预加载图片
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void LoadImage(Context mContext, String path, ImageView imageview, Drawable drawable) {
        if (drawable == null) {
            Glide.with(mContext).load(path).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
        } else {
            Glide.with(mContext).load(path).centerCrop()
                    .placeholder(R.drawable.picture_empty)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
        }
    }

    /**
     * 加载网络图片-free
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void LoadImageFree(Context mContext, String path, ImageView imageview) {
        Glide.with(mContext).load(path)
                .placeholder(R.drawable.picture_empty)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontTransform()
                .into(imageview);
    }

    /**
     * 加载网络图片-free-带圆角
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void LoadImageFree(Context mContext, String path, ImageView imageview, int cornerSize) {
        Glide.with(mContext).load(path)
                .placeholder(R.drawable.picture_empty)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new CornersTransform(mContext, cornerSize))
                .into(imageview);
    }

    public static void LoadImageFreeNoneCache(Context mContext, String path, ImageView imageview, int cornerSize) {
        Glide.with(mContext).load(path)
                .placeholder(R.drawable.picture_empty)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new CornersTransform(mContext, cornerSize))
                .into(imageview);
    }

    /**
     * 加载带尺寸的图片
     *
     * @param mContext
     * @param path
     * @param Width
     * @param Height
     * @param imageview
     */
    public static void LoadImageWithSize(Context mContext, String path, int Width, int Height, ImageView imageview) {
        Glide.with(mContext).load(path).override(Width, Height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
    }

    /**
     * 加载本地图片
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void LoadImageWithLocation(Context mContext, Integer path, ImageView imageview) {
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageview);
    }

    public static void LoadImageWithLocation(Context mContext, String path, ImageView imageview) {
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageview);
    }

    public static void LoadImageWithLocation(Context mContext, Drawable path, ImageView imageview) {
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageview);
    }

    /**
     * 加载bitmap
     *
     * @param mContext
     * @param bitmap
     * @param imageview
     */
    public static void LoadBitmapImage(Context mContext, Bitmap bitmap, ImageView imageview) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();

        Glide.with(mContext)
                .load(bytes)
                .fitCenter() //设置大小
//                .placeholder(me.iwf.photopicker.R.drawable.__picker_ic_photo_black_48dp)
//                .error(me.iwf.photopicker.R.drawable.__picker_ic_broken_image_black_48dp)
                .into(imageview);
    }

    /**
     * 圆形加载-网络
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void LoadCircleImage(Context mContext, String path, ImageView imageview) {
        Glide.with(mContext).load(path).centerCrop().placeholder(R.drawable.picture_empty)
                .transform(new CircleTransform(mContext, 2, ContextCompat.getColor(mContext, R.color.white)))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
    }
}
